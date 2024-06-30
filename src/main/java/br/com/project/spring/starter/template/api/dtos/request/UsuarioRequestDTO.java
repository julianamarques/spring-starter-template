package br.com.project.spring.starter.template.api.dtos.request;

import br.com.project.spring.starter.template.api.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    @NotNull(message = "{message.api.field.validator.nome_obrigatorio}")
    private String nome;
    @Email(message = "{message.api.field.validator.email_invalido}")
    @NotNull(message = "{message.api.field.validator.email_obrigatorio}")
    private String email;
    @NotNull(message = "{message.api.field.validator.senha_obrigatoria}")
    private String senha;
    @NotNull(message = "{message.api.field.validator.confirmacao_senha_obrigatoria}")
    private String confirmacaoSenha;

    public Usuario converterParaEntidade(String senhaCriptografada) {
        return converterParaEntidade(new Usuario(), senhaCriptografada);
    }

    public Usuario converterParaEntidade(Usuario usuario, String senhaCriptografada) {
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setAtivo(true);
        usuario.setSenha(senhaCriptografada);

        return usuario;
    }
}
