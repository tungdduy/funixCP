package net.timxekhach.utility;

import lombok.extern.log4j.Log4j2;
import net.timxekhach.operation.data.entity.TripUser;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.utility.mail.EmailService;
import org.thymeleaf.context.Context;

@Log4j2
public class XeMailUtils {

    private static EmailService emailService = EmailService.getInstance();

    public static void sendEmailRegisterSuccessFully(User user){

        Context context = new Context();
        context.setVariable("fullName", user.getFullName());
        context.setVariable("username", user.getPossibleLoginName());
        context.setVariable("password", user.getPasswordBeforeEncode());

        String template = "register";

        String subject = "Chào mừng bạn đến với Tìm Xe Khách";

        emailService.sendMail(context, template, subject, user.getEmail());
    }

    public static void sendEmailPasswordSecretKey(User user){
        Context context = new Context();
        context.setVariable("fullName", user.getFullName());
        context.setVariable("secretKey", user.getSecretPasswordKey());

        String template = "forgot-password-secret-key";

        String subject = "Cấp lại mật khẩu đăng nhập tại Tìm Xe Khách";

        emailService.sendMail(context, template, subject, user.getEmail());

    }

    public static void sendEmailTicket(TripUser trip){
        Context context = new Context();
        context.setVariable("fullName", trip.getFullName());
        context.setVariable("orderNo", String.format("%08d", trip.getTripId()));
        context.setVariable("tuyen", String.format("%s - %s", trip.getStartPoint().getPointName(), trip.getEndPoint().getPointName()));
        context.setVariable("nhaxe", trip.getTrip().getBussSchedule().getBuss().getCompany().getCompanyName());
        context.setVariable("bienso", trip.getTrip().getBussSchedule().getBuss().getBussLicense());
        context.setVariable("seats", trip.getSeats());
        context.setVariable("unitPrice", trip.getUnitPrice());
        context.setVariable("totalPrice", trip.getTotalPrice());
        context.setVariable("launchTime", trip.getTrip().getLaunchTime());
        context.setVariable("launchDate", trip.getTrip().getLaunchDate());
        context.setVariable("hotline", trip.getTrip().getBussSchedule().getBuss().getCompany().getHotLine());

        String template = "order-info";

        String subject = "Thông tin chuyến đi của bạn";

        emailService.sendMail(context, template, subject, trip.getEmail());
    }

    public static void sendConfirmEmailTicket(TripUser trip){
        Context context = new Context();
        context.setVariable("fullName", trip.getFullName());
        context.setVariable("orderNo", String.format("%08d", trip.getTripId()));
        context.setVariable("tuyen", String.format("%s - %s", trip.getStartPoint().getPointName(), trip.getEndPoint().getPointName()));
        context.setVariable("nhaxe", trip.getTrip().getBussSchedule().getBuss().getCompany().getCompanyName());
        context.setVariable("bienso", trip.getTrip().getBussSchedule().getBuss().getBussLicense());
        context.setVariable("seats", trip.getSeats());
        context.setVariable("unitPrice", trip.getUnitPrice());
        context.setVariable("totalPrice", trip.getTotalPrice());
        context.setVariable("launchTime", trip.getTrip().getLaunchTime());
        context.setVariable("launchDate", trip.getTrip().getLaunchDate());
        context.setVariable("hotline", trip.getTrip().getBussSchedule().getBuss().getCompany().getHotLine());

        String template = "order-confirm";

        String subject = new StringBuilder("Nhà xe ")
            .append(trip.getTrip().getBussSchedule().getBuss().getCompany().getCompanyName())
            .append(" đã xác nhận chuyến đi của bạn").toString();

        emailService.sendMail(context, template, subject, trip.getEmail());
    }

}
