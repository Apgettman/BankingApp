package com.skypro.bankingApp.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.skypro.bankingApp.exception.InvalidPasswordException;
import com.skypro.bankingApp.exception.UserAlreadyExistsException;
import com.skypro.bankingApp.exception.UserNotFoundException;
import com.skypro.bankingApp.model.Account;
import com.skypro.bankingApp.model.Currency;
import com.skypro.bankingApp.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public User addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        users.put(user.getUsername(), user);
        return createNewUserAccounts(user);
    }

    public User updateUser(String username, String firstName, String lastName) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        User user = users.get(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }

    public void updatePassword(String username, String password, String newPassword) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }

        User user = users.get(username);
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        user.setPassword(newPassword);
    }

    public User removeUser(String username) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        return users.remove(username);
    }

    public User getUser(String username) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        return users.get(username);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    private User createNewUserAccounts(User user) {
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.RUB));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.EUR));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.USD));

        return user;
    }
}
