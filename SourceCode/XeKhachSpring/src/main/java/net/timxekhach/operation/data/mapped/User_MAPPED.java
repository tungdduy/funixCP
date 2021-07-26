package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import org.apache.commons.lang3.math.NumberUtils;;
import javax.validation.constraints.*;
import java.util.List;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import javax.persistence.*;;
import net.timxekhach.operation.data.entity.TripUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;;
import net.timxekhach.operation.response.ErrorCode;;
import java.util.ArrayList;


@MappedSuperclass @Getter @Setter
@IdClass(User_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class User_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long userId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long userId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long userIdLong = Long.parseLong(data.get("userId"));
            if(NumberUtils.min(new long[]{userIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            };
            return new User_MAPPED.Pk(userIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new User_MAPPED.Pk(0L);
    }

    @OneToMany(
        mappedBy = "user",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<TripUser> allMyTrips = new ArrayList<>();


    @Email
    protected String email;


    @Pattern(regexp = "(03|05|07|08|09)+\\d{8,10}")
    protected String phoneNumber;

    @Size(max = 255)
    @JsonIgnore
    protected String password;

    @Size(max = 255)
    @Column(unique = true)
    protected String username;

    @Size(max = 30, min = 3)
    @NotBlank
    protected String fullName;

    @Size(max = 255)
    protected String role = "ROLE_USER";


    protected Boolean nonLocked = false;

    @Size(max = 255)
    @JsonIgnore
    protected String secretPasswordKey;

    public void setFieldByName(Map<String, String> data) {
        data.forEach((fieldName, value) -> {
            if (fieldName.equals("email")) {
                this.email = String.valueOf(value);
                return;
            }
            if (fieldName.equals("phoneNumber")) {
                this.phoneNumber = String.valueOf(value);
                return;
            }
            if (fieldName.equals("password")) {
                this.password = String.valueOf(value);
                return;
            }
            if (fieldName.equals("username")) {
                this.username = String.valueOf(value);
                return;
            }
            if (fieldName.equals("fullName")) {
                this.fullName = String.valueOf(value);
                return;
            }
            if (fieldName.equals("role")) {
                this.role = String.valueOf(value);
                return;
            }
            if (fieldName.equals("nonLocked")) {
                this.nonLocked = Boolean.valueOf(value);
                return;
            }
            if (fieldName.equals("secretPasswordKey")) {
                this.secretPasswordKey = String.valueOf(value);
            }
        });
    }



}
