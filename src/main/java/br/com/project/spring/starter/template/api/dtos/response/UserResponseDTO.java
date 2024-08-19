package br.com.project.spring.starter.template.api.dtos.response;

import br.com.project.spring.starter.template.api.entities.User;
import br.com.project.spring.starter.template.api.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
    private String accessToken;
    private LocalDateTime expirationDateToken;
    private String name;
    private String email;
    private List<RoleResponseDTO> roles;

    public UserResponseDTO(String token, Date expirationDateToken, User user) {
        this.accessToken = token;
        this.expirationDateToken = DateUtils.convertDateToLocalDateTime(expirationDateToken);
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = RoleResponseDTO.converterParaListaDTO(user.getRoles());
    }
}
