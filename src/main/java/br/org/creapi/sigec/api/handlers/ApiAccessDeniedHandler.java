package br.org.creapi.sigec.api.handlers;

import br.org.creapi.sigec.api.dtos.response.Response;
import br.org.creapi.sigec.api.enums.ApiMessageEnum;
import br.org.creapi.sigec.api.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiAccessDeniedHandler implements AccessDeniedHandler {
    private final JsonUtils jsonUtils;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse servletResponse,
                       AccessDeniedException accessDeniedException) throws IOException {
        Response<Object> response = new Response<>(HttpStatus.UNAUTHORIZED, ApiMessageEnum.ACCESS_DENIED.getMessage(), null);
        servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().print(jsonUtils.objectToJson(response));
    }
}
