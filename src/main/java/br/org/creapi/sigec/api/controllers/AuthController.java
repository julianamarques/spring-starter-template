package br.org.creapi.sigec.api.controllers;

import br.org.creapi.sigec.api.dtos.response.Response;
import br.org.creapi.sigec.api.dtos.request.AuthRequestDTO;
import br.org.creapi.sigec.api.dtos.request.UserRequestDTO;
import br.org.creapi.sigec.api.dtos.response.UserResponseDTO;
import br.org.creapi.sigec.api.exceptions.ApiException;
import br.org.creapi.sigec.api.services.AuthService;
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
