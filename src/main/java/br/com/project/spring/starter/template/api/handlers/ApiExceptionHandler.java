package br.com.project.spring.starter.template.api.handlers;

import br.com.project.spring.starter.template.api.dtos.response.Response;
import br.com.project.spring.starter.template.api.enums.ApiMessageEnum;
import br.com.project.spring.starter.template.api.exceptions.ApiException;
import br.com.project.spring.starter.template.api.exceptions.ApiExternalException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.net.ssl.SSLHandshakeException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
    @NonNull
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NonNull HttpStatus status, WebRequest request) {
        String body = String.format("Route %s not found!", request.getDescription(false).substring(4));

        log.error("Route not found");

        return new Response<>(status, ApiMessageEnum.ROUTE_NOT_FOUND.getMessage(), body);
    }

    @NonNull
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, @NonNull HttpStatus status) {
        String body = String.format("Required parameter: %s", ex.getParameterName());

        return new Response<>(status, ApiMessageEnum.REQUEST_PARAMETERS_MISSING.getMessage(), body);
    }

    @NonNull
    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, @NonNull HttpStatus status) {
        String body = String.format("Error in parameter conversion [%s]: %s", ex.getPropertyName(), ex.getMessage());

        return new Response<>(status, ApiMessageEnum.ENDPOINT_ERROR.getMessage(), body);
    }

    @NonNull
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @NonNull HttpStatus status) {
        log.error(ex);

        return new Response<>(status, ApiMessageEnum.UNKNOWN_ERROR.getMessage(), ex.getCause().getMessage());
    }

    @NonNull
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpStatus status) {
        log.error("A validation error occurred!");
        log.error(ex);

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new Response<>(status, ApiMessageEnum.INVALID_ARGUMENT.getMessage(), errors);
    }

    @NonNull
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" ");
        builder.append("not supported for this route. The supported methods are: ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(", "));
        String body = builder.substring(0, builder.length() - 2);

        return new Response<>(HttpStatus.METHOD_NOT_ALLOWED, ApiMessageEnum.UNSUPPORTED_METHOD.getMessage(), body);
    }

    @ExceptionHandler(SSLHandshakeException.class)
    public ResponseEntity<Object> handleSslHandshakeException(@NonNull SSLHandshakeException ex, @NonNull HttpStatus status) {
        log.error("{}", ApiMessageEnum.SSL_HANDSHAKE_ERROR.getMessage());
        log.error("{}", ex.getCause().getMessage());

        return new Response<>(status, ApiMessageEnum.SSL_HANDSHAKE_ERROR.getMessage(), ex.getCause().getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(@NonNull AccessDeniedException ex, @NonNull HttpStatus status) {
        log.error("{}", ApiMessageEnum.ACCESS_DENIED.getMessage());
        log.error("{}", ex.getCause().getMessage());

        return new Response<>(status, ApiMessageEnum.ACCESS_DENIED.getMessage(), ex.getCause().getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiErrorException(ApiException exception) {
        printErrorLog(exception, null, null);

        return new Response<>(exception.getHttpStatus(), exception
                .getApiMessage().getMessage(), exception.getErrorMessage());
    }

    @ExceptionHandler(ApiExternalException.class)
    public ResponseEntity<Object> handleApiExternalException(ApiExternalException exception) {
        Response<Object> response = new Response<>(exception.getHttpStatus(), exception.getMessage(), exception.getResponseBody());

        printErrorLog(null, exception, response.getMessage());

        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                ApiMessageEnum.UNKNOWN_ERROR, exception.getCause().getMessage());

        printErrorLog(apiException, null, null);

        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ApiMessageEnum.UNKNOWN_ERROR.getMessage(),
                exception.getCause().getMessage());
    }

    private void printErrorLog(ApiException exception, ApiExternalException externalException,
                                 String externalClientMessage) {
        String typeMessageApi = "";
        String messageApi = "";

        if (Objects.nonNull(exception)) {
            log.error("An unknown error has occurred!");
            typeMessageApi = exception.getApiMessage().name();
            messageApi = exception.getApiMessage().getMessage();
        } else if (Objects.nonNull(externalException)) {
            typeMessageApi = externalException.getApiMessage();
            messageApi = externalClientMessage;

            log.error("An external client error, reason: [{}] in type call: [{}]",
                    externalException.getReason(), externalException.getMethodKey());
        }

        log.error("[{}] :: {}", typeMessageApi, messageApi);
    }
}
