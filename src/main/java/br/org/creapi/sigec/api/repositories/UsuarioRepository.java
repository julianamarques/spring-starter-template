package br.org.creapi.sigec.api.repositories;

import br.org.creapi.sigec.api.entities.User;

import java.util.Optional;

public interface UsuarioRepository extends GenericRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
