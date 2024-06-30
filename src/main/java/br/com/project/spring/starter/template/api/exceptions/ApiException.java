package br.com.project.spring.starter.template.api.exceptions;

import br.com.project.spring.starter.template.api.enums.MensagemApiEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends Exception {
    private MensagemApiEnum mensagemApi;
    private HttpStatus httpStatus;
    private Object errorMessage;
    protected String[] args;

    public ApiException(HttpStatus httpStatus, MensagemApiEnum mensagemApi) {
        this.httpStatus = httpStatus;
        this.mensagemApi = mensagemApi;
    }

    public ApiException(HttpStatus httpStatus, MensagemApiEnum mensagemApi, Object errorMessage) {
        this.httpStatus = httpStatus;
        this.mensagemApi = mensagemApi;
        this.errorMessage = errorMessage;
    }
}
