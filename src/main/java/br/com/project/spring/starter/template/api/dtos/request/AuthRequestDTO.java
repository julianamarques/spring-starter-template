package br.com.project.spring.starter.template.api.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    @Email(message = "{message.api.field.validator.email_invalido}")
    @NotNull(message = "{message.api.field.validator.email_obrigatorio}")
    private String email;
    @NotNull(message = "{message.api.field.validator.senha_obrigatoria}")
    private String senha;
}
