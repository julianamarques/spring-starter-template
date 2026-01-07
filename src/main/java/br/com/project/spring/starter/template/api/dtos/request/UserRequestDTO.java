package br.com.project.spring.starter.template.api.dtos.request;

import br.com.project.spring.starter.template.api.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    @NotNull(message = "${message.api.field.validator.required_name}")
    private String name;
    @Email(message = "${message.api.field.validator.invalid_email}")
    @NotNull(message = "${message.api.field.validator.required_email}")
    private String email;
    @NotNull(message = "${message.api.field.validator.required_password}")
    private String password;
    @NotNull(message = "${message.api.field.validator.required_password_confirm}")
    private String confirmPassword;

    public User convertToEntity(String encriptedPassword) {
        return convertToEntity(new User(), encriptedPassword);
    }

    public User convertToEntity(User usuario, String encriptedPassword) {
        usuario.setName(this.password);
        usuario.setEmail(this.email);
        usuario.setActive(true);
        usuario.setPassword(encriptedPassword);

        return usuario;
    }
}
