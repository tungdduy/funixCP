package net.timxekhach.utility.model;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeFileUtils;
import org.springframework.util.ResourceUtils;

import javax.mail.Message;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter @Setter
public class MailModel {
    String toEmail, subject, templateName, htmlContent;
    Map<String, String> params = new HashMap<>();

    public String getHtmlContent() {
        if (this.htmlContent == null) {
            loadSubjectAndContent();
        }
        return htmlContent;
    }

    private void loadSubjectAndContent() {
        try {
            this.htmlContent = XeFileUtils.readFileAsString(ResourceUtils.getFile("classpath:email-templates/" + this.templateName + ".html"));

            final Pattern pattern = Pattern.compile("<title>(.+?)</title>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(this.htmlContent);
            matcher.find();
            this.subject = matcher.group(1);

            params.forEach(this::updateParam);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateParam(String name, String value) {
        this.subject = this.subject.replace(String.format("${%s}", name), value);
        this.htmlContent = this.htmlContent.replace(String.format("${%s}", name), value);
    }

    public void send(){
        new Thread(this::prepareMailThenSend).start();
    }

    private void prepareMailThenSend() {
        final String fromEmail = "ongyeuem@gmail.com";
        final String password = "moysktlmcn";
        final String toEmail = this.getToEmail();
        final String htmlContent = this.getHtmlContent();
        final String subject = this.getSubject();
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, "TìmXeKhách"));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(htmlContent, "text/html;charset=UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);
        } catch (Exception ex) {
            ErrorCode.SEND_EMAIL_FAILED.throwNow();
        }
    }

}
