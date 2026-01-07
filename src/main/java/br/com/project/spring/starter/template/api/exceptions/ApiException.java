package br.com.project.spring.starter.template.api.exceptions;

import br.com.project.spring.starter.template.api.enums.ApiMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends Exception {
    private ApiMessageEnum apiMessage;
    private HttpStatus httpStatus;
    private Object errorMessage;
    protected String[] args;

    public ApiException(HttpStatus httpStatus, ApiMessageEnum apiMessage) {
        this.httpStatus = httpStatus;
        this.apiMessage = apiMessage;
    }

    public ApiException(HttpStatus httpStatus, ApiMessageEnum apiMessage, Object errorMessage) {
        this.httpStatus = httpStatus;
        this.apiMessage = apiMessage;
        this.errorMessage = errorMessage;
    }
}
