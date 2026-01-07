package br.org.creapi.sigec.api.services;

import br.org.creapi.sigec.api.entities.Role;
import br.org.creapi.sigec.api.enums.ApiMessageEnum;
import br.org.creapi.sigec.api.enums.RoleEnum;
import br.org.creapi.sigec.api.exceptions.ApiException;
import br.org.creapi.sigec.api.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Role buscarPorNome(RoleEnum role) throws ApiException {
        return repository.findByName(role).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiMessageEnum.ROLE_NOT_FOUND));
    }
}
