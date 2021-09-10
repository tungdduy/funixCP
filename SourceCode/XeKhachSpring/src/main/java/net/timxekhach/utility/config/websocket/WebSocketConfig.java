package net.timxekhach.utility.config.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.repository.UserRepository;
import net.timxekhach.security.constant.RoleEnum;
import net.timxekhach.security.jwt.JwtTokenProvider;
import net.timxekhach.security.model.SecurityResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@AllArgsConstructor
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final SecurityResource securityResource;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;
  private final WebSocketAuthenticatorService authenticatorService;

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

        if (StompCommand.CONNECT == accessor.getCommand()) {
          String token = accessor.getFirstNativeHeader("Authorization");
          final UsernamePasswordAuthenticationToken user = authenticatorService.getAuthenticatedOrFail(token);
          accessor.setUser(user);
        }else if (StompCommand.SUBSCRIBE == accessor.getCommand()){
          String login = accessor.getUser().getName();
          User user = userRepository.findFirstByUsernameOrEmail(login, null);

          long count = user.getRoles().stream().filter(role -> RoleEnum.valueOf(role) == RoleEnum.ROLE_BUSS_ADMIN || RoleEnum.valueOf(role) == RoleEnum.ROLE_CALLER_STAFF).count();

          if (count <= 2 && count > 0) {
            checkTopicSubscribe(headers, user);
          }else{
            log.info("Notification >> Access denied for user {}", login);
            throw new IllegalArgumentException("No permission for this user");
          }
        }

        logCommand(accessor);
        return message;
      }
    });
  }

  private void checkTopicSubscribe(MessageHeaders headers, User user){
    String topic = (String) headers.get("simpDestination");
    String expectTopic  = String.format("/topic/%d", user.getEmployee().getCompanyId());

    if(!StringUtils.equalsIgnoreCase(topic, expectTopic)){
      log.info("No permission grant for this topic "+topic);
      throw new IllegalArgumentException("No permission for this topic");
    }
  }

  private void logCommand(StompHeaderAccessor accessor){
    Principal principal = accessor.getUser();
    String topic = accessor.getDestination();
    switch (accessor.getCommand()){
      case CONNECT:
        log.info("Connect notification successfully for user: {}", principal.getName());
        break;
      case SUBSCRIBE:
        log.info("Subscribe topic {} successfully for user: {}",
            topic,
            principal.getName());
        break;
      case UNSUBSCRIBE:
        log.info("Unsubscribe successfully for user: {}",
            principal.getName());
        break;
      case DISCONNECT:
        log.info("Disconnect notification successfully for user: {}", principal.getName());
        break;
      default:
        break;
    }
  }
}
