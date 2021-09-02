package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.RequiredArgsConstructor;
import net.timxekhach.firebase.FcmClient;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.rest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static net.timxekhach.utility.XeResponseUtils.success;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/user"})
public class UserApi {

    private final UserService userService;
	/**
	 * See {@link FcmClient}
	 */
	private final FcmClient fcmClient;

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
	public ResponseEntity<Void> forgotPasswordSecretKey (@RequestBody Map<String, String> secretInfo) {
		userService.forgotPasswordSecretKey(secretInfo);
		return success();
	}

	@PostMapping("/change-password")
	public ResponseEntity<Void> changePassword (@RequestBody Map<String, String> secretKeyInfo) {
		userService.changePassword(secretKeyInfo);
		return success();
	}

	@PostMapping("/update-password")
	public ResponseEntity<Void> updatePassword (@RequestBody Map<String, String> data) {
		userService.updatePassword(data);
		return success();
	}

	@PostMapping("/update-profile-image")
	public ResponseEntity<String> updateProfileImage(@RequestParam("username") String userId,
												   @RequestParam(value = "profileImage") MultipartFile profileImage){
		return success(userService.updateProfileImage(userId, profileImage));
	}
	@GetMapping("/subscribe")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void subscribe (@RequestParam("token") String token, @RequestParam("username") String username) {
		username = "ddao";
		this.fcmClient.subscribe(token, username);
	}

	@GetMapping("/unsubscribe")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unsubscribe (@RequestParam("token") String token, @RequestParam("username") String username) {
		username = "ddao";
		this.fcmClient.unsubscribe(token, username);
	}

	@GetMapping("/send")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void send () {
		Map<String, String> data = new HashMap<>();
		data.put("bussId","51C1-123.45");
		this.fcmClient.send("ddao", data);
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
