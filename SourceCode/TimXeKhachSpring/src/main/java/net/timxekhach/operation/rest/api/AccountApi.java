package net.timxekhach.operation.rest.api;

import net.timxekhach.operation.entity.User;
import net.timxekhach.operation.rest.service.AccountService;
import net.timxekhach.utility.XeResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Map;

@RestController
@RequestMapping(path = {"/user"})
public class AccountApi {

    private final AccountService accountService;
    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        return accountService.login(username, password);
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user)  {
        return XeResponseUtils.success(accountService.register(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @RequestBody User updateUser,
            @PathVariable("id") Long id) {

        accountService.updateUser(updateUser, id);
        return XeResponseUtils.success();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        accountService.deleteUser(id);
        return XeResponseUtils.success();
    }
    //==========================================
    // _______ START_IMPORT_METHOD __________ //
    //==========================================
}
