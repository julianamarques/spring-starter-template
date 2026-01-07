package br.com.project.spring.starter.template.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiExternalException extends Exception {
    private String apiMessage;
    private HttpStatus httpStatus;
    private String reason;
    private String methodKey;
    private String url;
    private Object responseBody;

    public ApiExternalException(String url, HttpStatus httpStatus, String apiMessage) {
        this.url = url;
        this.httpStatus = httpStatus;
        this.apiMessage = apiMessage;
    }

    public ApiExternalException(HttpStatus httpStatus, Object responseBody,
                                String reason, String methodKey) {
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.reason = reason;
        this.methodKey = methodKey;
    }

    public ApiExternalException(String url, HttpStatus httpStatus, String reason, String methodKey) {
        this.url = url;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.methodKey = methodKey;
    }

    public ApiExternalException(String url, HttpStatus httpStatus, Object responseBody,
                                String reason, String methodKey) {
        this.url = url;
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.reason = reason;
        this.methodKey = methodKey;
    }
}
