package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(User_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class User_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long userId;

    protected Long getIncrementId() {
        return this.userId;
    }
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
            }
            return new User_MAPPED.Pk(userIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new User_MAPPED.Pk(0L);
    }

//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @OneToOne(
        mappedBy = "user",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "employeeId")
    protected Employee employee;
//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//


    @Email
    @Column(unique = true)
    protected String email;

    @Pattern(regexp = "(03|05|07|08|09)+\\d{8,10}")
    @Column(unique = true)
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
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("email")) {
                this.setEmail(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("phoneNumber")) {
                this.setPhoneNumber(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("password")) {
                this.setPassword(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("username")) {
                this.setUsername(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("fullName")) {
                this.setFullName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("role")) {
                this.setRole(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("nonLocked")) {
                this.setNonLocked(Boolean.valueOf(value));
                continue;
            }
            if (fieldName.equals("secretPasswordKey")) {
                this.setSecretPasswordKey(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("employee")) {
                this.setEmployee(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getEmployeeRepository().findByEmployeeId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("userId")) {
                this.userId = value == null ? null : Long.valueOf(value);
            }
        }
    }
}
