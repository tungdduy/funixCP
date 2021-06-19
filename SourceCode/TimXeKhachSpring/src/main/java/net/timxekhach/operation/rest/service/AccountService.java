package net.timxekhach.operation.rest.service;

import net.timxekhach.operation.entity.User;
import net.timxekhach.operation.repository.UserRepository;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.security.jwt.JwtTokenProvider;
import net.timxekhach.utility.XeResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public ResponseEntity<User> login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User loginUser = userRepository.findByUsername(username);
        HttpHeaders jwtHeader = jwtTokenProvider.getJwtHeader(loginUser);

        return XeResponseUtils.successWithHeaders(loginUser, jwtHeader);
    }

    public ResponseEntity<User> register(User user) {
        ErrorCode.USERNAME_EXISTED.cumulativeIf(userRepository.existsByUsername(user.getUsername()));
        ErrorCode.EMAIL_EXISTED.cumulativeIf(userRepository.existsByEmail(user.getEmail()));

        user.encodePassword(bCryptPasswordEncoder);
        userRepository.save(user);
        return XeResponseUtils.success(user);
    }

    public ResponseEntity<Void> updateUser(User updateUser, Long id) {
        return userRepository.findById(id).map(user -> {
           updateUser.setId(id);
           userRepository.save(updateUser);
           return XeResponseUtils.success();
        }).orElseGet(ErrorCode.USER_NOT_FOUND::getErrorResponse);
    }
}
