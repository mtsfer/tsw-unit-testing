package com.tads.tsw;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUser(User updatedUser) {
        return userRepository
                .updateUser(updatedUser)
                .orElseThrow(() -> new RuntimeException("User with CPF [" + updatedUser.getCpf() + "] not found!"));
    }
}
