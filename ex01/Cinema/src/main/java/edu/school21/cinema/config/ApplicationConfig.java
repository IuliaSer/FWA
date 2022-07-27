package edu.school21.cinema.config;

import edu.school21.cinema.repositories.UsersRepository;
import edu.school21.cinema.repositories.UsersRepositoryJdbcImpl;
import edu.school21.cinema.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@PropertySource("../application.properties")
public class ApplicationConfig {

    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.user}")
    private String DB_USER;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${db.driver.name}")
    private String DB_DRIVER_NAME;

    @Value("${storage.path}")
    private String IMAGE_PATH;

    @Bean
    public UsersServiceImpl usersService(UsersRepository usersRepository) {
        return new UsersServiceImpl(usersRepository);
    }

    @Bean
    public UsersRepository usersRepository(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new UsersRepositoryJdbcImpl(dataSource, bCryptPasswordEncoder);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setUrl(DB_URL);
        driver.setUsername(DB_USER);
        driver.setPassword(DB_PASSWORD);
        driver.setDriverClassName(DB_DRIVER_NAME);
        return driver;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public String imagePath() {
        return IMAGE_PATH;
    }
}
