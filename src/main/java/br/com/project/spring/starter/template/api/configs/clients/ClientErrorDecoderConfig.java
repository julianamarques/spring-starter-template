package br.com.project.spring.starter.template.api.configs.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.project.spring.starter.template.api.enums.MensagemApiEnum;
import br.com.project.spring.starter.template.api.exceptions.ApiExternalException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.InputStream;

public class ClientErrorDecoderConfig implements ErrorDecoder {
    private static final int STATUS_BAD_REQUEST = 400;
    private static final int STATUS_INTERNAL_SERVER_ERROR = 500;
    private static final int STATUS_UNAVALIABLE_SERVICE = 503;

    @Override
    public Exception decode(String s, Response response) {
        String url = response.request().url();
        String methodName = response.request().httpMethod().name();

        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            Object exceptionMessage = mapper.readValue(body, Object.class);

            return switch (response.status()) {
                case STATUS_BAD_REQUEST -> new ApiExternalException(url, HttpStatus.BAD_REQUEST,
                        exceptionMessage, response.reason(), methodName);
                case STATUS_INTERNAL_SERVER_ERROR, STATUS_UNAVALIABLE_SERVICE -> new ApiExternalException(url,
                        HttpStatus.valueOf(response.status()), MensagemApiEnum.SERVICO_EXTERNO_INDISPONIVEL.getDescricao());
                default -> new ApiExternalException(url, HttpStatus.valueOf(response.status()), exceptionMessage, response.reason(), methodName);
            };
        } catch (Exception e) {
            return new ApiExternalException(url, HttpStatus.valueOf(response.status()), response.reason(), methodName);
        }
    }
}
