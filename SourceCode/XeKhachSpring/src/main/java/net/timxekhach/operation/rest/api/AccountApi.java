package net.timxekhach.operation.rest.api;

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.rest.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static net.timxekhach.utility.XeResponseUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/account"})
public class AccountApi {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        return accountService.login(username, password);
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user)  {
        return success(accountService.register(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @RequestBody User updateUser,
            @PathVariable("id") Long id) {

        accountService.updateUser(updateUser, id);
        return success();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        accountService.deleteUser(id);
        return success();
    }
     /* || ========================================== ||
        || _________ AUTO_IMPORT_ABOVE_THIS _________ ||
        || ========================================== || */
}
