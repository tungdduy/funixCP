package net.timxekhach.utility.mail;

import lombok.extern.log4j.Log4j2;
import net.timxekhach.operation.response.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class EmailService {
  private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
  private static final String MAIL_TEMPLATE_SUFFIX = ".html";
  private static final String UTF_8 = "UTF-8";

  private static Properties properties;

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
          loadProperties();
          templateEngine = thymeleafTemplateEngine();
        }

      }
    }
    return instance;
  }

  public void sendMail(Context context, String template, String subject, String recipient) {
    if (properties == null){
      log.info("No available email config, do nothing!");
      return;
    }

    String host = properties.getProperty("host");
    String port = properties.getProperty("port");
    String email = properties.getProperty("email");
    String password = properties.getProperty("password");

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
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
              }
            });
        Message message = new MimeMessage(session);

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
        message.setFrom(new InternetAddress(email, "TìmXeKhách"));
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

  private static void loadProperties(){
    log.info("Parsing mail.properties");
    try (InputStream input = new FileInputStream("/home/funix/config/mail.properties")) {

      properties = new Properties();

      // load a properties file
      properties.load(input);

      // get the property value and print it out
      properties.stringPropertyNames().forEach(key -> log.info(String.format("%s : %s", key, properties.getProperty(key))));

    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
    }
  }
}
