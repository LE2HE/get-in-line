package com.example.getinline.controller.api;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.AdminDTO;
import com.example.getinline.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(APIAuthController.class)
class APIAuthControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    public APIAuthControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[API][POST] 관리자 가입 - 정상 입력하면 회원 정보를 추가하고 안내 메시지 리턴")
    @Test
    void givenAdminDetails_whenSigningUp_thenCreatesAdminAndReturns() throws Exception {
        // given
        AdminDTO adminDTO = AdminDTO.of(
                "test@test.com",
                "testNick",
                "passwd",
                "010-1234-5678",
                "test memo"
        );

        // when & then
        mvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(adminDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());
    }

    @DisplayName("[API][POST] 로그인 - 존재하는 유저 정보로 인증 요청하면 인증 통과")
    @Test
    void givenUsernameAndPassword_whenLoggingIn_thenCreatesAdminAndReturns() throws Exception {
        // given
        LoginDTO loginDTO = LoginDTO.of(
                "test@test.com",
                "passwd"
        );

        // when & then
        mvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());
    }

}
