package com.example.getinline.controller.api;

import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.AdminDTO;
import com.example.getinline.dto.LoginDTO;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/api")
//@RestController
public class APIAuthController {

    @PostMapping("sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminDTO adminDTO) {
        return APIDataResponse.empty();
    }

    @PostMapping("login")
    public APIDataResponse<String> login(@RequestBody LoginDTO loginDTO) {
        return APIDataResponse.empty();
    }

}
