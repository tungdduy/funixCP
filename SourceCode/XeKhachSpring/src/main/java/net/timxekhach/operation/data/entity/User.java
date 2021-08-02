package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import net.timxekhach.operation.data.repository.UserRepository;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.security.handler.SecurityConfig;
import net.timxekhach.security.model.SecurityResource;
import net.timxekhach.utility.XeStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import static net.timxekhach.operation.response.ErrorCode.EMAIL_EXISTED;
import static net.timxekhach.utility.XeMailUtils.sendEmailRegisterSuccessFully;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class User extends User_MAPPED {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

public List<String> getRoles() {
        return XeStringUtils.splitByComma(this.role);
    }

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

    @Transient
    private boolean isPersisted;

    @PrePersist
    public void prePersist(){
        if (!this.isPersisted) { //avoid call twice on persist
            ErrorCode.PASSWORD_MUST_MORE_THAN_3_CHARS.throwIf(this.password == null || this.password.length() < 3);
            this.isPersisted = true;
            sendEmailRegisterSuccessFully(this);
            this.encodePassword();
        }
    }

    @PostPersist
    public void postPersist() {
    }

    @PreRemove
    public void preRemove() {
        this.deleteProfileImage();
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


}

