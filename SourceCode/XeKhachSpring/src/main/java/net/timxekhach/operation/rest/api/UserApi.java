package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.rest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static net.timxekhach.utility.XeResponseUtils.success;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/user"})
public class UserApi {

    private final UserService userService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	@GetMapping("/login")
	public ResponseEntity<User> login (@RequestBody Map<String, String> info) {
		return success(userService.login(info));
	}

	@GetMapping("/register")
	public ResponseEntity<User> register (@RequestBody User user) {
		return success(userService.register(user));
	}

	@GetMapping("/forgot-password")
	public ResponseEntity<Void> forgotPassword (@RequestBody String email) {
		userService.forgotPassword(email);
		return success();
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
