package com.tads.tsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private final List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public Optional<User> updateUser(User updatedUser) {
        return this.users
                .stream()
                .filter(user -> user.getCpf().equals(updatedUser.getCpf()))
                .findFirst()
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setFullName(updatedUser.getFullName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    user.setPhoneNumber(updatedUser.getPhoneNumber());
                    user.setAddress(updatedUser.getAddress());
                    user.setBirthDate(updatedUser.getBirthDate());
                    return user;
                });
    }

}
