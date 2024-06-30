package br.com.project.spring.starter.template.api.entities;

import br.com.project.spring.starter.template.api.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class Role implements Serializable, GrantedAuthority {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private UUID id;
    @Column(name = "nome", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum nome;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "roleusuario",
            joinColumns = @JoinColumn(name = "rolefk"),
            inverseJoinColumns = @JoinColumn(name = "usuariofk")
    )
    private List<Usuario> usuarios;

    @Override
    public String getAuthority() {
        return this.nome.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
