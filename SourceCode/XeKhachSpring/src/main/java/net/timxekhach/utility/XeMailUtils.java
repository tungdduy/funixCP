package net.timxekhach.utility;

import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.model.MailModel;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class XeMailUtils {

    public static void sendEmailRegisterSuccessFully(User user){

        MailModel mail = new MailModel();

        mail.setTemplateName("register");
        mail.setSubject("Đăng ký TimXeKhach Thành Công!");
        mail.setToEmail("giapsoft@gmail.com");

        mail.getParams().put("username", user.getPossibleLoginName());

        try {
            sendMail(mail);
        } catch (Exception ex) {
            ErrorCode.SEND_EMAIL_FAILED.throwNow();
        }

    }

    private static void sendMail(MailModel mail) throws MessagingException, UnsupportedEncodingException {
        final String fromEmail = "ongyeuem@gmail.com";
        final String password = "moysktlmcn";
        final String toEmail = mail.getToEmail();
        final String subject = mail.getSubject();
        final String htmlContent = mail.getHtmlContent();
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
        MimeMessage msg = new MimeMessage(session);
        //set message headers
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
    }
}
