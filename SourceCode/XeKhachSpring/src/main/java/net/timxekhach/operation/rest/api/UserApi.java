package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import net.timxekhach.operation.data.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import net.timxekhach.operation.rest.service.UserService;
import static net.timxekhach.utility.XeResponseUtils.success;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import net.timxekhach.operation.data.entity.User;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/user"})
public class UserApi {

    private final UserService userService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	@PostMapping("/login")
	public ResponseEntity<User> login (@RequestBody Map<String, String> info) {
		return userService.login(info);
	}

	@PostMapping("/register")
	public ResponseEntity<User> register (@RequestBody User user) {
		return success(userService.register(user));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<Void> forgotPassword (@RequestBody String email) {
		userService.forgotPassword(email);
		return success();
	}
	@PostMapping("/forgot-password-secret-key")
	public ResponseEntity<Void> forgotPasswordSecretKey (@RequestBody String email, @RequestBody String emailKey) {
		userService.forgotPasswordSecretKey(email, emailKey);
		return success();
	}


	private final UserRepository userRepository;
	@PostMapping("/test-api")
	public ResponseEntity<Void> testApi (@RequestBody Buss buss) {

		return success();
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
