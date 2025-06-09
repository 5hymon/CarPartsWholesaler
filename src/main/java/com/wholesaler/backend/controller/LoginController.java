package com.wholesaler.backend.controller;

import com.wholesaler.backend.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "Logowanie", description = "Logowanie na konto użytkownika")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // GET - get Integer (1 or 2) if login credentials are correct or Integer (0) if login credentials are incorrect
    @GetMapping
    @Operation(summary = "Spradź poprawniość danych logowania")
    public Integer isLoginSuccessful(
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("password") String password
    ) {
        return loginService.areLogCredentialsCorrect(emailAddress, password);
    }
}
