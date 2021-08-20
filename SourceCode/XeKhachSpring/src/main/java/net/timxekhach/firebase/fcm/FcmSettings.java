package net.timxekhach.firebase.fcm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "fcm")
@Getter
@Setter
@Component
public class FcmSettings {
  private String serviceAccountFile;
}
