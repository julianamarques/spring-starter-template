package br.com.project.spring.starter.template.api.repositories;

import br.com.project.spring.starter.template.api.entities.User;

import java.util.Optional;

public interface UsuarioRepository extends GenericRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
