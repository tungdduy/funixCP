package net.timxekhach.operation.rest.service;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.repository.UserRepository;
import net.timxekhach.security.jwt.JwtTokenProvider;
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

import static net.timxekhach.operation.response.ErrorCode.*;
import static net.timxekhach.utility.XeMailUtils.sendEmailPasswordSecretKey;
import static net.timxekhach.utility.XeMailUtils.sendEmailRegisterSuccessFully;
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
		EMAIL_EXISTED.throwIf(userRepository.existsByEmail(user.getEmail()));
		user.encodePassword();
		userRepository.save(user);
		sendEmailRegisterSuccessFully(user);
		return user;
	}

	public void forgotPassword (String email) {
		User user = EMAIL_NOT_EXIST.throwIfNull(userRepository.findFirstByEmail(email), email);
		user.createSecretPasswordKey();
		userRepository.save(user);
		sendEmailPasswordSecretKey(user);
	}

	public void forgotPasswordSecretKey (Map<String, String> secretInfo) {
		String email = secretInfo.get("email");
		String secretKey = secretInfo.get("secretKey");
		User user = EMAIL_NOT_EXIST.throwIfNull(userRepository.findFirstByEmail(email), email);
		SECRET_KEY_NOT_MATCH.throwIf(user.isMatchSecretPasswordKey(secretKey));
	}

	public void updateUser () {
		// TODO : service updateUser method
	}

	public void updateThumbnails () {
		// TODO : service updateThumbnails method

	}
	public void changePassword (Map<String, String> secretKeyInfo) {
		String email = secretKeyInfo.get("email");
		String secretKey = secretKeyInfo.get("secretKey");
		String newPassword = secretKeyInfo.get("newPassword");
		User user = EMAIL_NOT_EXIST.throwIfNull(userRepository.findFirstByEmail(email), email);
		SECRET_KEY_NOT_MATCH.throwIf(user.isMatchSecretPasswordKey(secretKey));
		user.encodePassword(newPassword);
		userRepository.save(user);
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
