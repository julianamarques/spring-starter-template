package br.com.project.spring.starter.template.api.configs.clients;

import br.com.project.spring.starter.template.api.enums.ApiMessageEnum;
import br.com.project.spring.starter.template.api.exceptions.ApiExternalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.InputStream;

public class ClientErrorDecoderConfig implements ErrorDecoder {
    private static final int BAD_REQUEST_STATUS = 400;
    private static final int INTERNAL_SERVER_ERROR_STATUS = 500;
    private static final int UNAVALIABLE_SERVICE_STATUS = 503;

    @Override
    public Exception decode(String s, Response response) {
        String url = response.request().url();
        String methodName = response.request().httpMethod().name();

        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            Object exceptionMessage = mapper.readValue(body, Object.class);

            return switch (response.status()) {
                case BAD_REQUEST_STATUS -> new ApiExternalException(url, HttpStatus.BAD_REQUEST,
                        exceptionMessage, response.reason(), methodName);
                case INTERNAL_SERVER_ERROR_STATUS, UNAVALIABLE_SERVICE_STATUS -> new ApiExternalException(url,
                        HttpStatus.valueOf(response.status()), ApiMessageEnum.EXTERNAL_SERVICE_UNAVALIABLE.getDescription());
                default -> new ApiExternalException(url, HttpStatus.valueOf(response.status()), exceptionMessage, response.reason(), methodName);
            };
        } catch (Exception e) {
            return new ApiExternalException(url, HttpStatus.valueOf(response.status()), response.reason(), methodName);
        }
    }
}
