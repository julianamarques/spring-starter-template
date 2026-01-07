package br.org.creapi.sigec.api.repositories;

import br.org.creapi.sigec.api.entities.Role;
import br.org.creapi.sigec.api.enums.RoleEnum;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends GenericRepository<Role, UUID> {
    Optional<Role> findByName(RoleEnum name);
}
