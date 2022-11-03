package com.example.getinline.dto;

public record LoginDTO(
        String email,
        String password
) {
    public static LoginDTO of(String email, String password) {
        return new LoginDTO(email, password);
    }
}
