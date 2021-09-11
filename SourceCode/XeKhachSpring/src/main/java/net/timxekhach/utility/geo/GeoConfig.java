package net.timxekhach.utility.geo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "xekhach.maxmind")
@Component
@Getter
@Setter
public class GeoConfig {
  private String dbPath;
}
