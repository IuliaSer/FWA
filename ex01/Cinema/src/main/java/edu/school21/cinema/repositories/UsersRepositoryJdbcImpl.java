package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Auth;
import edu.school21.cinema.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersRepositoryJdbcImpl(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException, SQLException {
            User user = new User();

            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

    public class AuthRowMapper implements RowMapper<Auth> {
        @Override
        public Auth mapRow(ResultSet rs, int rowNum) throws SQLException, SQLException {
            String ip = rs.getString("ip");
            String user_id = rs.getString("user_id");
            Auth auth = new Auth(rs.getString("date"), ip, user_id);
            return auth;
        }
    }
    @Override
    public long signUp(User entity) {
        String password = bCryptPasswordEncoder.encode(entity.getPassword());

        String query = "INSERT INTO users VALUES (default, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
                    ps.setString(1, entity.getFirstName());
                    ps.setString(2, entity.getLastName());
                    ps.setString(3, entity.getPhoneNumber());
                    ps.setString(4, password);
                    ps.setString(5, entity.getEmail());
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<User> getUserByEmail(String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String selectQuery = "SELECT * FROM users WHERE email = " + '\'' + email + '\'';// + " AND password = " + '\'' + password + '\'';
        List<User> users = jdbcTemplate.query(selectQuery, new UserRowMapper());
        if (users.isEmpty() || !bCryptPasswordEncoder.matches(password, users.get(0).getPassword()))
            return Optional.empty();
        return Optional.of(users.get(0));
    }

    @Override
    public void addAuth(long id, String addr) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String ip = null;
        if(Objects.equals(addr, "0:0:0:0:0:0:0:1"))
            ip = "127.0.0.1";

        String query = "INSERT INTO auth(user_id, date, ip) VALUES(?, ?, ?)";
        String finalIp = ip;
        jdbcTemplate.execute(query, (PreparedStatementCallback<Object>) ps -> {
            ps.setLong(1, id);
            ps.setString(2, dateFormat.format(new Date()));
            ps.setString(3, finalIp);
            return ps.execute();
        });
    }

    @Override
    public List getAuthList() {
        String selectQuery = "SELECT * FROM auth";
        return(jdbcTemplate.query(selectQuery, new AuthRowMapper()));
    }
}