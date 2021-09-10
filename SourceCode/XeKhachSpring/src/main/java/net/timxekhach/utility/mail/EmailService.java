package net.timxekhach.utility.mail;

import lombok.extern.log4j.Log4j2;
import net.timxekhach.operation.data.entity.TripUser;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.config.ApplicationContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class EmailService {
  private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
  private static final String MAIL_TEMPLATE_SUFFIX = ".html";
  private static final String UTF_8 = "UTF-8";

  private static EmailServiceConfig emailCfg;

  private static TemplateEngine templateEngine;

  // private instance, so that it can be
  // accessed by only by getInstance() method
  private static EmailService instance;

  public static EmailService getInstance()
  {
    if (instance == null)
    {
      //synchronized block to remove overhead
      synchronized (EmailService.class)
      {
        if(instance==null)
        {
          // if instance is null, initialize
          instance = new EmailService();
          emailCfg = ApplicationContextUtils.getApplicationContext().getBean(EmailServiceConfig.class);
          templateEngine = thymeleafTemplateEngine();
        }

      }
    }
    return instance;
  }

  public void sendMail(Context context, String template, String subject, String recipient) {
    if (emailCfg == null
        || StringUtils.isEmpty(emailCfg.getEmail())
        || StringUtils.isEmpty(emailCfg.getPassword())
        || emailCfg.getPort() == 0
        || StringUtils.isEmpty(emailCfg.getHost())){
      log.info("No available email config, do nothing!");
      return;
    }

    CompletableFuture.runAsync(() -> {
      try {
        log.info("sendMail email running...");
        if (StringUtils.isEmpty(recipient)){
          log.info("Recipient >>> IS NULL >>> REJECTED sending email request");
          return;
        }
        log.info("Recipient: "+recipient);
        log.info("Subject: "+subject);

        Properties props = new Properties();
        props.put("mail.smtp.host", emailCfg.getHost());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", emailCfg.getPort());

        Session session = Session.getInstance(props,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailCfg.getEmail(), emailCfg.getPassword());
              }
            });
        Message message = new MimeMessage(session);

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
        message.setFrom(new InternetAddress(emailCfg.getEmail(), "TìmXeKhách"));
        message.setSubject(subject);
        String content = getContent(context, template);
        message.setContent(content, CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        ErrorCode.SEND_EMAIL_FAILED.throwNow();
      }
    });
  }

  public String getContent(final Context context, final String templateName) {
    return templateEngine.process(templateName, context);
  }

  private static ITemplateResolver thymeleafTemplateResolver() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("/email-templates/");
    templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding(UTF_8);
    return templateResolver;
  }

  private static TemplateEngine thymeleafTemplateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
    return templateEngine;
  }

  public static void sendNotification(TripUser tripUser, boolean isCancel){
    SimpMessagingTemplate template = ApplicationContextUtils.getApplicationContext().getBean(SimpMessagingTemplate.class);

    String message = String.format("Khách hàng %s đã %s %d ghế cho chặng từ %s đi %s vào ngày %s lúc %s",
        tripUser.getFullName(), isCancel ? "hủy" : "đặt", tripUser.getSeats().size(),
        tripUser.getStartPoint().getPointName(), tripUser.getEndPoint().getPointName(),
        DateFormatUtils.format(tripUser.getTrip().getLaunchDate(), "dd/MM/yyyy"),
        DateFormatUtils.format(tripUser.getTrip().getLaunchTime(), "HH:mm"));

    Map<String, String> payload = new HashMap<>();
    payload.put("message", message);
    payload.put("type", isCancel ? "cancel" : "new");
    payload.put("tripUserId", String.valueOf(tripUser.getTripUserId()));
    payload.put("tripId", String.valueOf(tripUser.getTripId()));

    log.info("Notification to {}: {}", "/topic/"+tripUser.getCompanyId(), payload);

    if (template != null){
      template.convertAndSend("/topic/"+tripUser.getCompanyId(), payload);
    }
  }
}
