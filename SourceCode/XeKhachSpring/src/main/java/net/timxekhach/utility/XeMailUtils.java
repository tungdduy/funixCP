package net.timxekhach.utility;

import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.model.MailModel;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class XeMailUtils {

    public static void sendEmailRegisterSuccessFully(User user){
        MailModel mail = new MailModel();
        mail.setTemplateName("register");
        mail.setToEmail(user.getEmail());
        mail.getParams().put("username", user.getPossibleLoginName());
        mail.getParams().put("fullName", user.getFullName());
        mail.send();
    }

    public static void sendEmailPasswordSecretKey(User user){
        MailModel mail = new MailModel();
        mail.setTemplateName("forgot-password-secret-key");
        mail.setToEmail(user.getEmail());
        mail.getParams().put("fullName", user.getFullName());
        mail.getParams().put("secretKey", user.getSecretPasswordKey());
        mail.send();
    }

    public static void sendEmailPasswordHasBeenChanged(User user){
        MailModel mail = new MailModel();
        mail.setTemplateName("password-has-been-changed");
        mail.setToEmail(user.getEmail());
        mail.getParams().put("fullName", user.getFullName());
        mail.send();
    }


}
