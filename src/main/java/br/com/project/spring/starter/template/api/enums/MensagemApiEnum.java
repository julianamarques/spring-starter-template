package br.com.project.spring.starter.template.api.enums;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

@Getter
public enum MensagemApiEnum {
    REQUISICAO_CONCLUIDA("message.api.requisicao_concluida"),
    ROTA_NAO_ENCONTRADA("message.api.rota_nao_encontrada"),
    FALTAM_PARAMETROS_NA_REQUISICAO("message.api.faltam_parametros_na_requisicao"),
    ERRO_ENDPOINT("message.api.erro_no_endpoint"),
    ERRO_VALIDACAO("message.api.argumento_invalido"),
    METODO_NAO_SUPORTADO("message.api.metodo_nao_suportado"),
    ACESSO_NEGADO("message.api.acesso_negado"),
    ERRO_DESCONHECIDO("message.api.erro_desconhecido"),
    ERRO_SSL_HANDSHAKE("message.api.erro_ssl_handshake"),
    SERVICO_EXTERNO_INDISPONIVEL("message.api.externo.servico_indisponivel");

    MensagemApiEnum(String descricao) {
        this.descricao = descricao;
    }

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private final String descricao;

    public String getMessage(String... args) {
        String msg = convertToUTF8(resourceBundle.getString(this.descricao));

        if (msg.contains("�")) {
            msg = resourceBundle.getString(this.descricao);
        }

        return Objects.isNull(args) ? msg : MessageFormat.format(msg, (Object) args);
    }

    private static String convertToUTF8(String message) {
        try {
            return new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return message;
        }
    }
}
