package net.timxekhach.operation.rest.api;

import net.timxekhach.operation.entity.User;
import net.timxekhach.operation.rest.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"/user"})
public class AccountApi {

    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user)  {
        return accountService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username,
                                      @RequestParam String password) {
        return accountService.login(username, password);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User updateUser, @PathVariable("id") Long id) {
        return accountService.updateUser(updateUser, id);
    }
}
