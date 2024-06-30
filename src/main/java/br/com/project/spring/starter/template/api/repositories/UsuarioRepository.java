package br.com.project.spring.starter.template.api.repositories;

import br.com.project.spring.starter.template.api.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends GenericRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
