package net.timxekhach.operation.rest.service;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.repository.UserRepository;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.security.jwt.JwtTokenProvider;
import net.timxekhach.utility.XeResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static net.timxekhach.operation.response.ErrorCode.*;
import static net.timxekhach.utility.XeMailUtils.sendEmailPasswordSecretKey;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

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
		userRepository.save(user);
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

	public void changePassword (Map<String, String> secretKeyInfo) {
		String email = secretKeyInfo.get("email");
		String secretKey = secretKeyInfo.get("secretKey");
		String newPassword = secretKeyInfo.get("newPassword");
		User user = EMAIL_NOT_EXIST.throwIfNull(userRepository.findFirstByEmail(email), email);
		SECRET_KEY_NOT_MATCH.throwIf(user.isMatchSecretPasswordKey(secretKey));
		user.encodePassword(newPassword);
		userRepository.save(user);
	}

	public void updatePassword (Map<String, String> data) {
		User user = ErrorCode.DATA_NOT_FOUND
				.throwIfNotPresent(userRepository.findById(User.pk(data)));
		String currentPassword = ErrorCode.CURRENT_PASSWORD_WRONG.throwIfNull(
				user.nullIfNotCurrentPassword(data.get("currentPassword")));
		String newPassword = data.get("newPassword");
		ErrorCode.PASSWORD_NOT_MATCH.throwIfFalse(newPassword.equals(data.get("reNewPassword")));
		ErrorCode.NOTHING_CHANGED.throwIf(newPassword.equals(currentPassword));
		user.encodePassword(newPassword);
		userRepository.save(user);
	}

	public String updateProfileImage(String userId, MultipartFile profileImage) {
		return null;
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
