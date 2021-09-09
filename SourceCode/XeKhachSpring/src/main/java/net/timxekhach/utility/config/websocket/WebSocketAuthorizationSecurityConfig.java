package net.timxekhach.utility.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketAuthorizationSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
  @Override
  protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
    // You can customize your authorization mapping here.
    messages
        .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.SUBSCRIBE).hasAnyAuthority("ROLE_CALLER_STAFF", "ROLE_BUSS_ADMIN")
        .simpTypeMatchers(SimpMessageType.UNSUBSCRIBE, SimpMessageType.DISCONNECT).authenticated()
        .simpSubscribeDestMatchers("/topic/**").hasAnyAuthority("ROLE_CALLER_STAFF", "ROLE_BUSS_ADMIN")
        .anyMessage().authenticated();

  }

  @Override
  protected boolean sameOriginDisabled() {
    return true;
  }
}
