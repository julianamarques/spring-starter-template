package br.com.project.spring.starter.template.api.services;

import br.com.project.spring.starter.template.api.dtos.request.AuthRequestDTO;
import br.com.project.spring.starter.template.api.dtos.request.UsuarioRequestDTO;
import br.com.project.spring.starter.template.api.dtos.response.UsuarioResponseDTO;
import br.com.project.spring.starter.template.api.entities.Usuario;
import br.com.project.spring.starter.template.api.enums.MensagemApiEnum;
import br.com.project.spring.starter.template.api.enums.RoleEnum;
import br.com.project.spring.starter.template.api.exceptions.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final RoleService roleService;

    @Transactional(rollbackFor = Exception.class)
    public UsuarioResponseDTO autenticar(AuthRequestDTO request) throws ApiException {
        try {
            Usuario usuario = loadUserByUsername(request.getEmail());
            validarSenha(request.getSenha(), usuario.getPassword());
            String token = tokenService.gerarToken(usuario);
            Date dataExpiracao = tokenService.getExpirationDate(token);

            return new UsuarioResponseDTO(token, dataExpiracao, usuario);
        } catch (Exception e) {
            log.error(e);
            throw new ApiException(HttpStatus.UNAUTHORIZED, MensagemApiEnum.USUARIO_SENHA_INVALIDA);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO request) throws ApiException {
        try {
            Usuario usuario = request.converterParaEntidade(encriptarSenha(request.getSenha()));
            usuario.setRoles(List.of(roleService.buscarPorNome(RoleEnum.USUARIO)));
            usuario = usuarioService.salvar(usuario);
            String token = tokenService.gerarToken(usuario);
            Date dataExpiracao = tokenService.getExpirationDate(token);

            return new UsuarioResponseDTO(token, dataExpiracao, usuario);
        } catch (DataIntegrityViolationException e) {
            log.error(e);
            throw new ApiException(HttpStatus.BAD_REQUEST, MensagemApiEnum.EMAIL_EXISTE);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UsuarioResponseDTO editarUsuario(String token, UsuarioRequestDTO request) throws ApiException {
        try {
            Usuario usuario = loadUserByUsername(request.getEmail());
            usuario = request.converterParaEntidade(usuario, encriptarSenha(request.getSenha()));
            usuario = usuarioService.salvar(usuario);
            Date dataExpiracao = tokenService.getExpirationDate(token);

            return new UsuarioResponseDTO(token, dataExpiracao, usuario);
        } catch (DataIntegrityViolationException e) {
            log.error(e);
            throw new ApiException(HttpStatus.BAD_REQUEST, MensagemApiEnum.EMAIL_EXISTE);
        }
    }

    public Usuario getUsuarioLogado() throws ApiException {
        try {
            return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, MensagemApiEnum.ACESSO_NEGADO);
        }
    }

    public Usuario validarToken(String token) throws ApiException {
        String email = tokenService.getSubject(token);
        Usuario usuario = usuarioService.buscarPorEmail(email);
        boolean isTokenNaoExpirado = tokenService.getExpirationDate(token).after(new Date());

        if (isTokenNaoExpirado) {
            return usuario;
        }

        throw new ApiException(HttpStatus.UNAUTHORIZED, MensagemApiEnum.ACESSO_NEGADO);
    }

    public UsuarioResponseDTO getUsuarioLogadoDTO(String token) throws ApiException {
        token = token.substring(7);
        Usuario usuario = getUsuarioLogado();
        Date dataExpiracao = tokenService.getExpirationDate(token);

        return new UsuarioResponseDTO(token, dataExpiracao, usuario);
    }

    @Override
    public Usuario loadUserByUsername(String username) {
        try {
            return usuarioService.buscarPorEmail(username);
        } catch (UsernameNotFoundException | ApiException e) {
            log.error(e);
            throw new UsernameNotFoundException(MensagemApiEnum.USUARIO_NAO_ENCONTRADO.getMessage());
        }
    }

    private String encriptarSenha(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }

    private void validarSenha(String senhaRaw, String senhaEncriptada) throws ApiException {
        boolean isSenhaValida = new BCryptPasswordEncoder().matches(senhaRaw, senhaEncriptada);

        if (!isSenhaValida) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, MensagemApiEnum.SENHA_INVALIDA);
        }
    }
}
