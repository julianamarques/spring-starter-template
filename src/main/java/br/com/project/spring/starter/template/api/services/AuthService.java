package br.com.project.spring.starter.template.api.services;

import br.com.project.spring.starter.template.api.dtos.request.AuthRequestDTO;
import br.com.project.spring.starter.template.api.dtos.request.UserRequestDTO;
import br.com.project.spring.starter.template.api.dtos.response.UserResponseDTO;
import br.com.project.spring.starter.template.api.entities.User;
import br.com.project.spring.starter.template.api.enums.ApiMessageEnum;
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
    private final UserService userService;
    private final TokenService tokenService;
    private final RoleService roleService;

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDTO auth(AuthRequestDTO request) throws ApiException {
        try {
            User user = loadUserByUsername(request.getEmail());
            validatePassword(request.getPassword(), user.getPassword());
            String token = tokenService.generateToken(user);
            Date expirationDate = tokenService.getExpirationDate(token);

            return new UserResponseDTO(token, expirationDate, user);
        } catch (Exception e) {
            log.error(e);
            throw new ApiException(HttpStatus.UNAUTHORIZED, ApiMessageEnum.INVALID_USER_OR_PASSWORD);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDTO save(UserRequestDTO request) throws ApiException {
        try {
            User user = request.convertToEntity(encryptPassword(request.getPassword()));
            user.setRoles(List.of(roleService.buscarPorNome(RoleEnum.USER)));
            user = userService.save(user);
            String token = tokenService.generateToken(user);
            Date expirationDate = tokenService.getExpirationDate(token);

            return new UserResponseDTO(token, expirationDate, user);
        } catch (DataIntegrityViolationException e) {
            log.error(e);
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiMessageEnum.EMAIL_EXISTS);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDTO edit(String token, UserRequestDTO request) throws ApiException {
        try {
            User user = loadUserByUsername(request.getEmail());
            user = request.convertToEntity(user, encryptPassword(request.getPassword()));
            user = userService.save(user);
            Date expirationDate = tokenService.getExpirationDate(token);

            return new UserResponseDTO(token, expirationDate, user);
        } catch (DataIntegrityViolationException e) {
            log.error(e);
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiMessageEnum.EMAIL_EXISTS);
        }
    }

    public User getAuthUser() throws ApiException {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, ApiMessageEnum.ACCESS_DENIED);
        }
    }

    public User validateToken(String token) throws ApiException {
        String email = tokenService.getSubject(token);
        User user = userService.findByEmail(email);
        boolean isTokenNotExpired = tokenService.getExpirationDate(token).after(new Date());

        if (isTokenNotExpired) {
            return user;
        }

        throw new ApiException(HttpStatus.UNAUTHORIZED, ApiMessageEnum.ACCESS_DENIED);
    }

    public UserResponseDTO getAuthUserDTO(String token) throws ApiException {
        token = token.substring(7);
        User usuario = getAuthUser();
        Date expirationDate = tokenService.getExpirationDate(token);

        return new UserResponseDTO(token, expirationDate, usuario);
    }

    @Override
    public User loadUserByUsername(String username) {
        try {
            return userService.findByEmail(username);
        } catch (UsernameNotFoundException | ApiException e) {
            log.error(e);
            throw new UsernameNotFoundException(ApiMessageEnum.USER_NOT_FOUND.getMessage());
        }
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private void validatePassword(String rawPassword, String encryptedPassword) throws ApiException {
        boolean isValidPassword = new BCryptPasswordEncoder().matches(rawPassword, encryptedPassword);

        if (!isValidPassword) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, ApiMessageEnum.INVALID_PASSWORD);
        }
    }
}
