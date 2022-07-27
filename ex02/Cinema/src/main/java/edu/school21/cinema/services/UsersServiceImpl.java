package edu.school21.cinema.services;

import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

public class UsersServiceImpl implements UsersService{

    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public long signUp(User user) {
        return (usersRepository.signUp(user));
    }

    @Override
    public Optional<User> getUserByEmail(String email, String password) {
        Optional<User> user = usersRepository.getUserByEmail(email, password);
        return user;
    }

    @Override
    public void addAuth(long id, String remoteAddr) {
        usersRepository.addAuth(id, remoteAddr);
    }

    @Override
    public List getAuthList() {
        return usersRepository.getAuthList();
    }
}
