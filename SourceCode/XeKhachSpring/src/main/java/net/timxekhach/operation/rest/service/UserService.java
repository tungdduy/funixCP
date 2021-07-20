package net.timxekhach.operation.rest.service;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.repository.UserRepository;
import net.timxekhach.security.jwt.JwtTokenProvider;
import net.timxekhach.utility.XeMailUtils;
import net.timxekhach.utility.XeResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.Map;
import net.timxekhach.operation.data.entity.User;

import static net.timxekhach.operation.response.ErrorCode.EMAIL_EXISTED;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public ResponseEntity<User> login (Map<String, String> info) {
		String username = info.get("username");
		String password = info.get("password");
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
		);

		User loginUser = userRepository.findFirstByUsernameOrEmail(username, username);
		HttpHeaders jwtHeader = jwtTokenProvider.getJwtHeader(loginUser);
		return XeResponseUtils.successWithHeaders(loginUser, jwtHeader);
	}

	public User register (User user) {
		EMAIL_EXISTED.cumulativeIf(userRepository.existsByEmail(user.getEmail()));
		user.encodePassword(bCryptPasswordEncoder);
		userRepository.save(user);
		XeMailUtils.sendEmailRegisterSuccessFully(user);
		return user;
	}

	public void forgotPassword (String email) {
		// TODO : service forgotPassword method
	}
	public void forgotPasswordSecretKey (String email, String emailKey) {
		// TODO : service forgotPasswordSecretKey method
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
