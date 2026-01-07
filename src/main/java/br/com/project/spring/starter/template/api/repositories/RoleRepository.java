package br.com.project.spring.starter.template.api.repositories;

import br.com.project.spring.starter.template.api.entities.Role;
import br.com.project.spring.starter.template.api.enums.RoleEnum;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends GenericRepository<Role, UUID> {
    Optional<Role> findByName(RoleEnum name);
}
