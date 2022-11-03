package com.example.getinline.dto;

public record AdminDTO(
        String email,
        String nickname,
        String password,
        String phoneNumber,
        String memo
) {
    public static AdminDTO of(
            String email,
            String nickname,
            String password,
            String phoneNumber,
            String memo
    ) {
        return new AdminDTO(email, nickname, password, phoneNumber, memo);
    }
}
