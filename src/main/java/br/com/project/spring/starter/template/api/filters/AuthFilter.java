package br.com.project.spring.starter.template.api.filters;

import br.com.project.spring.starter.template.api.dtos.response.Response;
import br.com.project.spring.starter.template.api.entities.User;
import br.com.project.spring.starter.template.api.enums.ApiMessageEnum;
import br.com.project.spring.starter.template.api.exceptions.ApiException;
import br.com.project.spring.starter.template.api.services.AuthService;
import br.com.project.spring.starter.template.api.utils.JsonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final JsonUtils jsonUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token = Objects.nonNull(authHeader) ? authHeader : null;

        try {
            User user = authService.validateToken(token);
            List<GrantedAuthority> roles = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                    .collect(Collectors.toList());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), roles);

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ApiException e) {
            if (e.getHttpStatus().equals(HttpStatus.UNAUTHORIZED)) {
                Response<Object> responseApi = new Response<>(HttpStatus.UNAUTHORIZED, ApiMessageEnum.ACCESS_DENIED.getMessage(), null);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().print(jsonUtils.objectToJson(responseApi));
            }
        }

        filterChain.doFilter(request, response);
    }
}
