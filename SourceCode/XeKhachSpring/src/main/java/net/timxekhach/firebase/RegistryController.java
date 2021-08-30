package net.timxekhach.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class RegistryController {

  /**
   * See {@link FcmClient}
   */
  private FcmClient fcmClient;

  /**
   * Register a token to specific topic for firebase
   */
  @PostMapping("/subscribe")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void register(@RequestBody String token) {
    this.fcmClient.subscribe(token);
  }
}
