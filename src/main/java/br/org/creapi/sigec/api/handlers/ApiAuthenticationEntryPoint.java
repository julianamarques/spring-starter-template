package br.org.creapi.sigec.api.handlers;

import br.org.creapi.sigec.api.dtos.response.Response;
import br.org.creapi.sigec.api.enums.ApiMessageEnum;
import br.org.creapi.sigec.api.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final JsonUtils jsonUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse servletResponse,
                         AuthenticationException authException) throws IOException {
        Response<Object> response = new Response<>(HttpStatus.UNAUTHORIZED, ApiMessageEnum.ACCESS_DENIED.getMessage(), null);

        servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().print(jsonUtils.objectToJson(response));
    }
}
