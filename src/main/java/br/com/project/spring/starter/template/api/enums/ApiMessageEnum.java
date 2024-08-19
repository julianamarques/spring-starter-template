package br.com.project.spring.starter.template.api.enums;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

@Getter
public enum ApiMessageEnum {
    REQUEST_COMPLETED("message.api.request_completed"),
    ROUTE_NOT_FOUND("message.api.route_not_found"),
    REQUEST_PARAMETERS_MISSING("message.api.request_parameters_missing"),
    ENDPOINT_ERROR("message.api.endpoint_error"),
    INVALID_ARGUMENT("message.api.invalid_argument"),
    UNSUPPORTED_METHOD("message.api.unsupported_method"),
    ACCESS_DENIED("message.api.access_denied"),
    UNKNOWN_ERROR("message.api.unknown_error"),
    SSL_HANDSHAKE_ERROR("message.api.ssl_handshake_error"),
    EXTERNAL_SERVICE_UNAVALIABLE("message.api.external.unavaliable_service");

    ApiMessageEnum(String descricao) {
        this.descricao = descricao;
    }

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private final String descricao;

    public String getMessage(String... args) {
        String message = convertToUTF8(resourceBundle.getString(this.descricao));

        if (message.contains("�")) {
            message = resourceBundle.getString(this.descricao);
        }

        return Objects.isNull(args) ? message : MessageFormat.format(message, (Object) args);
    }

    private static String convertToUTF8(String message) {
        try {
            return new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return message;
        }
    }
}
