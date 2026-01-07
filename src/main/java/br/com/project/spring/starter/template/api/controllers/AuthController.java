package br.com.project.spring.starter.template.api.controllers;

import br.com.project.spring.starter.template.api.dtos.response.Response;
import br.com.project.spring.starter.template.api.dtos.request.AuthRequestDTO;
import br.com.project.spring.starter.template.api.dtos.request.UserRequestDTO;
import br.com.project.spring.starter.template.api.dtos.response.UserResponseDTO;
import br.com.project.spring.starter.template.api.exceptions.ApiException;
import br.com.project.spring.starter.template.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> auth(@Valid @RequestBody AuthRequestDTO request) throws ApiException {
        UserResponseDTO body = authService.auth(request);

        return new Response<>(body);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getAuthUser(@RequestHeader("Authorization") String token) throws ApiException {
        UserResponseDTO body = authService.getAuthUserDTO(token);

        return new Response<>(body);
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) throws ApiException {
        UserResponseDTO body = authService.save(request);

        return new Response<>(body);
    }

    @PutMapping("/edit-user")
    public ResponseEntity<UserResponseDTO> editUser(@RequestHeader("Authorization") String token,
                                                    @Valid @RequestBody UserRequestDTO request) throws ApiException {
        UserResponseDTO body = authService.edit(token, request);

        return new Response<>(body);
    }
}
