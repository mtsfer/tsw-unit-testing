package com.tads.tsw;


public class User {
    private String username;
    private String fullName;
    private String cpf;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String birthDate;


    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        if (fullName == null) {
            throw new IllegalArgumentException("Name must not be null!");
        }
        this.fullName = fullName;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";

        if (email != null && !email.matches(emailRegex)) {
            throw new IllegalArgumentException("Por favor, informe um email válido.");
        }

        this.email = email;
    }

    public void setPassword(String password) {
        final String passwordRegex = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{8,32})\\S$";

        if (password == null) {
            throw new IllegalArgumentException("A senha não pode ser nula.");
        } else if (!password.matches(passwordRegex)) {
            throw new IllegalArgumentException("Sua senha deve conter ao menos 1 caractere maiúsculo, 1 minúsculo e 1 número.");
        }

        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            throw new IllegalArgumentException("O telefone deve conter 11 dígitos.");
        }

        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("O endereço não pode ser nulo.");
        }

        this.address = address;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
