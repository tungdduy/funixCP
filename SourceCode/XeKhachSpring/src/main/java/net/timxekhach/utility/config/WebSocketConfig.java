package net.timxekhach.utility.config;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.repository.UserRepository;
import net.timxekhach.security.constant.RoleEnum;
import net.timxekhach.security.jwt.JwtTokenProvider;
import net.timxekhach.security.model.SecurityResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@AllArgsConstructor
@Log4j2
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final SecurityResource securityResource;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    StompWebSocketEndpointRegistration endPoint = registry.addEndpoint("/socket");
    securityResource.getAllowedOrigins().forEach(origin -> endPoint.setAllowedOrigins(origin));
    //endPoint.setAllowedOrigins("*");
    endPoint.withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    //subscribe, subscribe
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        MessageHeaders headers = message.getHeaders();

        log.info("Websocket command >>> "+accessor.getCommand());

        if(StompCommand.SUBSCRIBE == accessor.getCommand()){
          checkPermission(headers, StompCommand.SUBSCRIBE);
        }

        if (StompCommand.CONNECT == accessor.getCommand()) {
          checkPermission(headers, StompCommand.CONNECT);
        }

        return message;
      }
    });
  }

  private void checkPermission(MessageHeaders headers, StompCommand command){
    Map<String, List> nativeHeaders = (Map<String, List>) headers.get(StompHeaderAccessor.NATIVE_HEADERS);

    if (nativeHeaders.containsKey("Authorization")) {
      List<String> token = nativeHeaders.get("Authorization");

      if (token != null && token.size() > 0){
        String username = jwtTokenProvider.getSubject(token.get(0));
        User user = userRepository.findFirstByUsernameOrEmail(username, null);

        if (command == StompCommand.CONNECT){
          long count = user.getRoles().stream().filter(role -> RoleEnum.valueOf(role) == RoleEnum.ROLE_BUSS_ADMIN || RoleEnum.valueOf(role) == RoleEnum.ROLE_CALLER_STAFF).count();

          if (count <= 2 && count > 0) {
            return;
          }
          log.info("No permission grant for this user "+user.getUsername());
        }else{
          checkTopicSubscribe(headers, user);
          return;
        }

      }

    }

    throw new IllegalArgumentException("No permission for this user");
  }

  private void checkTopicSubscribe(MessageHeaders headers, User user){
    String topic = (String) headers.get("simpDestination");
    String expectTopic  = String.format("/topic/%d", user.getEmployee().getCompanyId());

    if(!StringUtils.equalsIgnoreCase(topic, expectTopic)){
      log.info("No permission grant for this topic "+topic);
      throw new IllegalArgumentException("No permission for this topic");
    }
  }
}
