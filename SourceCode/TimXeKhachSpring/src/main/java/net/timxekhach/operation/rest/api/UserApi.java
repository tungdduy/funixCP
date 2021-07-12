package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import net.timxekhach.operation.rest.service.UserService;
import static net.timxekhach.utility.XeResponseUtils.success;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import net.timxekhach.operation.entity.User;
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
