package br.com.project.spring.starter.template.api.dtos.response;

import br.com.project.spring.starter.template.api.enums.ApiMessageEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Response<B> extends ResponseEntity<B> implements Serializable {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
    private B body;

    public Response() {
        super((B) null, HttpStatus.OK);
        this.status = HttpStatus.OK.value();
        this.message = ApiMessageEnum.REQUEST_COMPLETED.getMessage();
        this.timestamp = LocalDateTime.now();
        this.body = null;
    }

    public Response(B body) {
        super(body, HttpStatus.OK);
        this.status = HttpStatus.OK.value();
        this.message = ApiMessageEnum.REQUEST_COMPLETED.getMessage();
        this.timestamp = LocalDateTime.now();
        this.body = body;
    }

    public Response(HttpStatus status, B body) {
        super(body, status);
        this.status = status.value();
        this.message = ApiMessageEnum.REQUEST_COMPLETED.getMessage();
        this.timestamp = LocalDateTime.now();
        this.body = body;
    }

    public Response(HttpStatus status, String message, B body) {
        super(body, status);
        this.status = status.value();
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.body = body;
    }
}
