package br.com.project.spring.starter.template.api.services;

import br.com.project.spring.starter.template.api.entities.Usuario;
import br.com.project.spring.starter.template.api.enums.MensagemApiEnum;
import br.com.project.spring.starter.template.api.exceptions.ApiException;
import br.com.project.spring.starter.template.api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) throws ApiException {
        return repository.findByEmail(email).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, MensagemApiEnum.USUARIO_NAO_ENCONTRADO));
    }
}
