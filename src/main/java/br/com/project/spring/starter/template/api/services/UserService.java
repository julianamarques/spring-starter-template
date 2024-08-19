package br.com.project.spring.starter.template.api.services;

import br.com.project.spring.starter.template.api.entities.User;
import br.com.project.spring.starter.template.api.enums.ApiMessageEnum;
import br.com.project.spring.starter.template.api.exceptions.ApiException;
import br.com.project.spring.starter.template.api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsuarioRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
        return repository.save(user);
    }

    public User findByEmail(String email) throws ApiException {
        return repository.findByEmail(email).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiMessageEnum.USER_NOT_FOUND));
    }
}
