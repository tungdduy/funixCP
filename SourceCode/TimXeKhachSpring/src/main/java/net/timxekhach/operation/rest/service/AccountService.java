package net.timxekhach.operation.rest.service;

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.entity.User;
import net.timxekhach.operation.repository.UserRepository;
import net.timxekhach.security.jwt.JwtTokenProvider;
import net.timxekhach.utility.XeResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.timxekhach.operation.response.ErrorCode.EMAIL_EXISTED;
import static net.timxekhach.operation.response.ErrorCode.UNDEFINED_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<User> login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User loginUser = userRepository.findFirstByUsernameOrEmail(username, username);
        HttpHeaders jwtHeader = jwtTokenProvider.getJwtHeader(loginUser);
        return XeResponseUtils.successWithHeaders(loginUser, jwtHeader);
    }

    public User register(User user) {
        EMAIL_EXISTED.cumulativeIf(userRepository.existsByEmail(user.getEmail()));
        user.encodePassword(bCryptPasswordEncoder);
        userRepository.save(user);
        return user;
    }

    public void updateUser(User updateUser, Long id) {
        if (!userRepository.findById(id).isPresent()) {
            UNDEFINED_ERROR.throwNow();
        }
        updateUser.setId(id);
        userRepository.save(updateUser);
    }

    public void deleteUser(Long id) {
    }
}
