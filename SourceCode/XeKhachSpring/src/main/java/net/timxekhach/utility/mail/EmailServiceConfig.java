package net.timxekhach.utility.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "xekhach.email")
@Component
@Getter
@Setter
public class EmailServiceConfig {
  private String host;
  private int port;
  private String email;
  private String password;
}
