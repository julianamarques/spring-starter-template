package br.com.project.spring.starter.template.api.dtos.response;

import br.com.project.spring.starter.template.api.entities.Usuario;
import br.com.project.spring.starter.template.api.utils.DataUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UsuarioResponseDTO {
    private String accessToken;
    private LocalDateTime expirationDateToken;
    private String nome;
    private String email;
    private List<RoleResponseDTO> roles;

    public UsuarioResponseDTO(String token, Date expirationDateToken, Usuario usuario) {
        this.accessToken = token;
        this.expirationDateToken = DataUtils.convertDateToLocalDateTime(expirationDateToken);
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.roles = RoleResponseDTO.converterParaListaDTO(usuario.getRoles());
    }
}
