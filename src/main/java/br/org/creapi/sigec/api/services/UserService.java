package br.org.creapi.sigec.api.services;

import br.org.creapi.sigec.api.entities.User;
import br.org.creapi.sigec.api.enums.ApiMessageEnum;
import br.org.creapi.sigec.api.exceptions.ApiException;
import br.org.creapi.sigec.api.repositories.UsuarioRepository;
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
