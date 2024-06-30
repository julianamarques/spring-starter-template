package br.com.project.spring.starter.template.api.controllers;

import br.com.project.spring.starter.template.api.dtos.response.Response;
import br.com.project.spring.starter.template.api.dtos.request.AuthRequestDTO;
import br.com.project.spring.starter.template.api.dtos.request.UsuarioRequestDTO;
import br.com.project.spring.starter.template.api.dtos.response.UsuarioResponseDTO;
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
    public ResponseEntity<UsuarioResponseDTO> autenticar(@Valid @RequestBody AuthRequestDTO request) throws ApiException {
        UsuarioResponseDTO body = authService.autenticar(request);

        return new Response<>(body);
    }

    @GetMapping("/user")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioLogado(@RequestHeader("Authorization") String token) throws ApiException {
        UsuarioResponseDTO body = authService.getUsuarioLogadoDTO(token);

        return new Response<>(body);
    }

    @PostMapping("/criar-usuario")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO request) throws ApiException {
        UsuarioResponseDTO body = authService.criarUsuario(request);

        return new Response<>(body);
    }

    @PutMapping("/editar-usuario")
    public ResponseEntity<UsuarioResponseDTO> editarUsuario(@RequestHeader("Authorization") String token,
                                                          @Valid @RequestBody UsuarioRequestDTO request) throws ApiException {
        UsuarioResponseDTO body = authService.editarUsuario(token, request);

        return new Response<>(body);
    }
}
