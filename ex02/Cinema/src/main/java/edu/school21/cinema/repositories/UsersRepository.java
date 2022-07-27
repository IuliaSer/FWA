package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    long signUp(User entity);

    Optional<User> getUserByEmail(String email, String password);
    List getAuthList();
    void addAuth(long id, String ip);
}