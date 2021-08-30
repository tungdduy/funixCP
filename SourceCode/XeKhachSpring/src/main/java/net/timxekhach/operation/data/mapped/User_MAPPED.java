package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;
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
                if(value == null) {this.setEmail(null); continue;}
                if(value.equals(this.getEmail())) continue;
                this.setEmail(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("phoneNumber")) {
                if(value == null) {this.setPhoneNumber(null); continue;}
                if(value.equals(this.getPhoneNumber())) continue;
                this.setPhoneNumber(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("password")) {
                if(value == null) {this.setPassword(null); continue;}
                if(value.equals(this.getPassword())) continue;
                this.setPassword(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("username")) {
                if(value == null) {this.setUsername(null); continue;}
                if(value.equals(this.getUsername())) continue;
                this.setUsername(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("fullName")) {
                if(value == null) {this.setFullName(null); continue;}
                if(value.equals(this.getFullName())) continue;
                this.setFullName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("role")) {
                if(value == null) {this.setRole(null); continue;}
                if(value.equals(this.getRole())) continue;
                this.setRole(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("nonLocked")) {
                if(value == null) {this.setNonLocked(null); continue;}
                if(value.equals(this.getNonLocked())) continue;
                this.setNonLocked(Boolean.valueOf(value));
                continue;
            }
            if (fieldName.equals("secretPasswordKey")) {
                if(value == null) {this.setSecretPasswordKey(null); continue;}
                if(value.equals(this.getSecretPasswordKey())) continue;
                this.setSecretPasswordKey(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("employee")) {
                if(value == null) {this.setEmployee(null); continue;}
                this.setEmployee(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getEmployeeRepository().findByEmployeeId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("userId")) {
                this.userId = value == null ? null : Long.valueOf(value);
            }
        }
    }
}
