package net.timxekhach.utility.geo;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.log4j.Log4j2;
import net.timxekhach.utility.XeMailUtils;
import net.timxekhach.utility.geo.model.DeviceMetadata;
import net.timxekhach.utility.geo.repo.DeviceMetadataRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Service
@Log4j2
public class GeoIPLocationService {

  private GeoConfig geoConfig;
  private DeviceMetadataRepository deviceMetadataRepository;

  @Autowired
  public GeoIPLocationService(GeoConfig geoConfig, DeviceMetadataRepository deviceMetadataRepository) {
    this.geoConfig = geoConfig;
    this.deviceMetadataRepository = deviceMetadataRepository;
  }
  //private final Parser parser;

  private DatabaseReader dbReader;
  private final String UNKNOWN = "UNKNOWN";

  @PostConstruct
  public void init() throws IOException {
    if (geoConfig == null || StringUtils.isEmpty(geoConfig.getDbPath())) {
      log.info("No configuration for geo config, terminate deploy process");
      throw new IllegalArgumentException("No configuration for geo config, terminate deploy process");
    }
    File database = new File(geoConfig.getDbPath());
    dbReader = new DatabaseReader.Builder(database).build();
  }

  public void verifyDevice(String username, String ip, String userAgent) throws IOException, GeoIp2Exception {

    String location = getIpLocation(ip);
    String deviceDetails = getDeviceDetails(userAgent);

    DeviceMetadata deviceMetadata = new DeviceMetadata();
    deviceMetadata.setUsername(username);
    deviceMetadata.setLocation(location);
    deviceMetadata.setDeviceDetails(deviceDetails);
    deviceMetadata.setLastLoggedIn(new Date());

    //first time login then save the location
    int firstTime = deviceMetadataRepository.countByUsername(username);
    if (firstTime == 0) {
      deviceMetadataRepository.save(deviceMetadata);
      return;
    }

    DeviceMetadata existingDevice
        = findExistingDevice(username, deviceDetails, location);

    if (Objects.isNull(existingDevice)) {
      unknownDeviceNotification(deviceMetadata);
      deviceMetadataRepository.save(deviceMetadata);
    }
  }

  private void unknownDeviceNotification(DeviceMetadata deviceMetadata) {
    log.info("Unknown Location logging detected: user {} in location {}", deviceMetadata.getUsername(), deviceMetadata.getLocation());
    XeMailUtils.sendUnknownLocationEmail(deviceMetadata);
  }

  private String getIpLocation(String ip) throws IOException, GeoIp2Exception {
    String location = UNKNOWN;
    InetAddress ipAddress = InetAddress.getByName(ip);
    CityResponse cityResponse = dbReader.city(ipAddress);

    if (Objects.nonNull(cityResponse) &&
        Objects.nonNull(cityResponse.getCity()) &&
        !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {
      location = cityResponse.getCity().getName();
    }
    return location;
  }

  private String getDeviceDetails(String userAgent) {
    String deviceDetails = UNKNOWN;

    Client client = new Parser().parse(userAgent);
    if (Objects.nonNull(client)) {
      deviceDetails = client.userAgent.family
          + " " + client.userAgent.major + "."
          + client.userAgent.minor + " - "
          + client.os.family + " " + client.os.major
          + "." + client.os.minor;
    }
    return deviceDetails;
  }

  private DeviceMetadata findExistingDevice(
      String username, String deviceDetails, String location) {
    List<DeviceMetadata> knownDevices
        = deviceMetadataRepository.findByUsername(username);

    return Optional.ofNullable(knownDevices).orElse(Collections.emptyList())
        .stream()
        .filter(device -> location.equals(device.getLocation()))
        .findFirst().orElse(null);
  }


  public static void main(String args[]) throws IOException, GeoIp2Exception {
    List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13);
    int primeGreaterThanFive = primes.stream()
        .peek(num -> System.out.println("will filter " + num))
        .filter(x -> x > 15).findFirst().orElse(printInteger());
    System.out.println(primeGreaterThanFive);

    InetAddress ipAddress = InetAddress.getByName("27.67.94.165");
    File database = new File("C:\\Users\\ddao\\IdeaProjects\\funixCP\\SourceCode\\XeKhachSpring\\src\\main\\resources\\GeoLite2-City_20210908\\GeoLite2-City.mmdb");
    DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
    CityResponse cityResponse = dbReader.city(ipAddress);
    cityResponse.getCity();

    System.out.println(cityResponse.getCity());
    System.out.println(cityResponse.getCity().getName());
  }

  private static int printInteger(){
    System.out.println("Running orElse");
    return 1;
  }
}
