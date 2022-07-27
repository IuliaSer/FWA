package edu.school21.cinema.services;

import edu.school21.cinema.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    long signUp(User user);

    Optional<User> getUserByEmail(String email, String password);

    void addAuth(long id, String remoteAddr);

    List getAuthList();
}
