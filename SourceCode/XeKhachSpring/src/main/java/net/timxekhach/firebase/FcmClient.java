package net.timxekhach.firebase;

import com.google.firebase.messaging.*;
import lombok.extern.log4j.Log4j2;
import net.timxekhach.firebase.fcm.FcmSettings;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.repository.UserRepository;
import net.timxekhach.operation.response.ErrorCode;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
@Log4j2
public class FcmClient {

  private UserRepository userRepository;

  /**
   * Initial Firebase App based on config file
   * which is available in {@link FcmSettings}
   * @param settings
   */
  public FcmClient(FcmSettings settings) {
    //TODO
//    Path p = Paths.get(settings.getServiceAccountFile());
//    try (InputStream serviceAccount = Files.newInputStream(p)) {
//      FirebaseOptions options = new FirebaseOptions.Builder()
//          .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
//
//      FirebaseApp.initializeApp(options);
//    }
//    catch (IOException e) {
//      log.error("init fcm", e);
//    }
  }

  /**
   * Sending a messsage to the topic with given data
   * @param topic
   * @param data
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public void send(String topic, Map<String, String> data)
      throws InterruptedException, ExecutionException {

    Message message = Message.builder().putAllData(data).setTopic(topic)
        .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
            .setNotification(new WebpushNotification("Background Title (server)",
                "Background Body (server)", "mail2.png"))
            .build())
        .build();

    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    System.out.println("Sent message: " + response);
  }

  /**
   * Register a token to the topic
   * See {@link #getTopic()}
   * @param clientToken
   */
  public void subscribe(String clientToken) {
    try {
      TopicManagementResponse response = FirebaseMessaging.getInstance()
          .subscribeToTopicAsync(Collections.singletonList(clientToken), getTopic()).get();
      System.out
          .println(response.getSuccessCount() + " tokens were subscribed successfully");
    }
    catch (InterruptedException | ExecutionException e) {
      log.error("subscribe", e);
    }
  }

  private String getUsername(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    ErrorCode.ACCESS_DENIED.throwIf(authentication instanceof AnonymousAuthenticationToken);
    return authentication.getName();
  }

  private String getTopic(){
    User user = userRepository.findFirstByUsernameOrEmail(getUsername(), null);

    ErrorCode.ACCESS_DENIED.throwIf(Boolean.TRUE.equals(user.getNonLocked()));

    //Filter by role?
    //ErrorCode.ACCESS_DENIED.throwIf(user.getRoles().);

    //Getting company for subscribing the topic
    return String.valueOf(user.getEmployee().getCompany().getCompanyId());

  }
}
