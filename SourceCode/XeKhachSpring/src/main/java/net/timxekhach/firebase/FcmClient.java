package net.timxekhach.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.timxekhach.firebase.fcm.FcmSettings;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.repository.UserRepository;
import net.timxekhach.operation.response.ErrorCode;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
@Log4j2
@RequiredArgsConstructor
public class FcmClient {

  private final UserRepository userRepository;
  private final FcmSettings fcmSettings;

  /**
   * Initial Firebase App based on config file
   */
  @PostConstruct
  private void initialize() {
    log.info("Initial Firebase...");
    log.info("Looking firebase service account file at: "+fcmSettings.getServiceAccountFile());
    Path p = Paths.get(fcmSettings.getServiceAccountFile());
    try (InputStream serviceAccount = Files.newInputStream(p)) {
      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://capstone-308403-default-rtdb.asia-southeast1.firebasedatabase.app")
          .build();

      FirebaseApp.initializeApp(options);
    } catch (IOException e) {
      log.error("Create FirebaseApp Error", e);
    }
  }

  /**
   * Sending a messsage to the topic with given data
   * @param topic
   * @param data
   */
  public void send(String topic, Map<String, String> data){

    Message message = Message.builder()
        .putData("abc", "abc")
        .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
            .setNotification(new WebpushNotification("Background Title (server)",
                "Background Body (server)", "mail2.png"))
            .build())
        //.setTopic(topic)
        .setToken("cQRxREVS0O4VK5fTZ1C3xx:APA91bF8KveXl6cv_LvtUQN6mAyV8gZK-CGZM0aDYuxH4Af5ph_3kW9CCmpQmF1Hu8eD5mRkTGUrkPT4_9yaiJYIEmIe9vIkUE3IQCsvxXzfMe2xe8GVASJTTn6ZGdG6_XTPVy4LITe-")
        .build();

    try {
      String result = FirebaseMessaging.getInstance().send(message);
      log.info("Send result: "+result);
    } catch (FirebaseMessagingException e) {
      log.error("Fail to send firebase notification", e);
    }

  }

  /**
   * Register a token to the topic
   * See {@link #getTopic(String)}
   * @param clientToken
   */
  public void subscribe(String clientToken, String username) {
    try {
      TopicManagementResponse response = FirebaseMessaging.getInstance()
          .subscribeToTopicAsync(Collections.singletonList(clientToken), getTopic(username)).get();
      log.info("subscribe >>> " +response.getSuccessCount()+ " tokens were subscribed successfully");
    }
    catch (InterruptedException | ExecutionException e) {
      log.error("subscribe", e);
    }
  }

  /**
   * Register a token to the topic
   * See {@link #getTopic(String)}
   * @param clientToken
   */
  public void unsubscribe(String clientToken, String username) {
    try {
      TopicManagementResponse response = FirebaseMessaging.getInstance()
          .unsubscribeFromTopicAsync(Collections.singletonList(clientToken), getTopic(username)).get();
      log.info("unsubscribe >>> " +response.getSuccessCount()+ " tokens were unsubscribed successfully");
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

  private String getTopic(String username){
    User user = userRepository.findFirstByUsernameOrEmail(username, username);

    ErrorCode.ACCESS_DENIED.throwIf(Boolean.FALSE.equals(user.getNonLocked()));

    //Filter by role?
    //ErrorCode.ACCESS_DENIED.throwIf(user.getRoles().);

    //Getting company for subscribing the topic
    return String.valueOf(user.getEmployee().getCompany().getCompanyId());

  }
}
