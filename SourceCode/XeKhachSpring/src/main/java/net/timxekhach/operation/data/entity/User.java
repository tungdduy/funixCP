package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.security.handler.SecurityConfig;
import net.timxekhach.utility.XeStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.timxekhach.utility.XeMailUtils.sendEmailRegisterSuccessFully;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class User extends User_MAPPED {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

@JsonIgnore
    public List<String> getRoles() {
        return XeStringUtils.splitByComma(this.role);
    }

    @JsonIgnore
    public List<GrantedAuthority> getGrantedAuthority() {
        return this.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public void encodePassword() {
        this.password = SecurityConfig.getPasswordEncoder().encode(this.password);
    }
    public void encodePassword(String password) {
        this.password = SecurityConfig.getPasswordEncoder().encode(password);
    }

    public String getPossibleLoginName() {
        return this.username != null ? username : email;
    }

    public void createSecretPasswordKey() {
        this.secretPasswordKey = XeStringUtils.randomAlphaNumerics(6);
        logger.info("secret password key is: " + this.secretPasswordKey);
    }

    public boolean isMatchSecretPasswordKey(String secretKey) {
        return secretKey == null || !secretKey.equals(this.secretPasswordKey);
    }

    public String nullIfNotCurrentPassword(String currentPassword) {
        return currentPassword != null
                && SecurityConfig.getPasswordEncoder().matches(currentPassword, this.password) ? currentPassword : null;
    }

    @Override
    public void prePersist(){
        ErrorCode.PASSWORD_MUST_MORE_THAN_3_CHARS.throwIf(this.password == null || this.password.length() < 3);
        this.encodePassword();
    }

    @Override
    public void postPersist() {
        sendEmailRegisterSuccessFully(this);
    }

    @Override
    public void postRemove() {
        this.deleteProfileImage();
    }

    @Override
    public void setFieldByName(Map<String, String> data) {
        super.setFieldByName(data);
        if (data.containsKey("password")) {
           this.password = data.get("password");
           this.encodePassword();
        }
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


}

