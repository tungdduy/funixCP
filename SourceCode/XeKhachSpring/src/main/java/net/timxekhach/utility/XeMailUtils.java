package net.timxekhach.utility;

import lombok.extern.log4j.Log4j2;
import net.timxekhach.operation.data.entity.TripUser;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.geo.model.DeviceMetadata;
import net.timxekhach.utility.mail.EmailService;
import org.apache.commons.collections.CollectionUtils;
import org.thymeleaf.context.Context;

import java.util.stream.Collectors;

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

    public static void sendEmailTicket(Long tripUserId){
        TripUser tripUser = CommonUpdateService.getTripUserRepository().findByTripUserId(tripUserId);

        if(!isValidSeat(tripUser))
            return;

        Context context = createContext(tripUser);
        String template = "order-info";

        String subject = "Thông tin chuyến đi của bạn";

        emailService.sendMail(context, template, subject, tripUser.getEmail());
    }

    public static void sendConfirmEmailTicket(Long tripUserId){
        TripUser tripUser = CommonUpdateService.getTripUserRepository().findByTripUserId(tripUserId);

        if(!isValidSeat(tripUser))
            return;

        Context context = createContext(tripUser);
        String template = "order-confirm";

        String subject = new StringBuilder("Nhà xe ")
            .append(tripUser.getTrip().getBussSchedule().getBuss().getCompany().getCompanyName())
            .append(" đã xác nhận chuyến đi của bạn").toString();

        emailService.sendMail(context, template, subject, tripUser.getEmail());
    }

    private static Context createContext(TripUser tripUser){
        Context context = new Context();
        context.setVariable("fullName", tripUser.getFullName());
        context.setVariable("orderNo", String.format("%08d", tripUser.getTripId()));
        context.setVariable("tuyen", String.format("%s - %s", tripUser.getStartPoint().getPointName(), tripUser.getEndPoint().getPointName()));
        context.setVariable("nhaxe", tripUser.getTrip().getBussSchedule().getBuss().getCompany().getCompanyName());
        context.setVariable("bienso", tripUser.getTrip().getBussSchedule().getBuss().getBussLicense());
        context.setVariable("seats", tripUser.getSeats().stream().collect(Collectors.toList()));
        context.setVariable("unitPrice", tripUser.getUnitPrice().longValue());
        context.setVariable("totalPrice", tripUser.getTotalPrice().longValue());
        context.setVariable("launchTime", tripUser.getTrip().getLaunchTime());
        context.setVariable("launchDate", tripUser.getTrip().getLaunchDate());
        context.setVariable("hotline", tripUser.getTrip().getBussSchedule().getBuss().getCompany().getHotLine());
        return context;
    }

    private static boolean isValidSeat(TripUser tripUser){

        if(tripUser == null){
            log.info("Invalid ticket");
            return false;
        }

        if (CollectionUtils.isEmpty(tripUser.getSeats())){
            log.info("Ticket ID {} has empty seat, email process may rejected", tripUser.getTripUserId());
            return false;
        }
        else{
            log.info("Ticket ID {} has {} seat(s), email processing...", tripUser.getTripUserId(), tripUser.getSeats().size());
            return true;
        }
    }


    public static void sendUnknownLocationEmail(DeviceMetadata deviceMetadata){
        User user = CommonUpdateService.getUserRepository().findFirstByUsernameOrEmail(deviceMetadata.getUsername(), null);

        Context context = new Context();
        context.setVariable("fullName", user.getFullName());
        context.setVariable("username", user.getUsername());
        context.setVariable("location", deviceMetadata.getLocation());
        context.setVariable("device", deviceMetadata.getDeviceDetails());

        String template = "unknown-login";

        String subject = "Cảnh báo: Hoạt động đăng nhập bất thường";

        emailService.sendMail(context, template, subject, user.getEmail());

    }
}
