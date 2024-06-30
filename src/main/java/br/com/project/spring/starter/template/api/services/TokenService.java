package br.com.project.spring.starter.template.api.services;

import br.com.project.spring.starter.template.api.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class TokenService {
    @Value("${application.key.secret}")
    private String secretKey;
    private static final Integer VALIDADE_TOKEN = 1000 * 60 * 24;

    public String gerarToken(Usuario usuario) {
        return gerarToken(new HashMap<>(), usuario);
    }

    public String gerarToken(Map<String, Object> extraClaims, Usuario usuario) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(usuario.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + VALIDADE_TOKEN))
                .signWith(getSecretKey(), Jwts.SIG.HS512)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
