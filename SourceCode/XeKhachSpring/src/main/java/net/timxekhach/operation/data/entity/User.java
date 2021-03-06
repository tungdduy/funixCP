package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.security.constant.RoleEnum;
import net.timxekhach.security.handler.SecurityConfig;
import net.timxekhach.utility.XeStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

import static net.timxekhach.utility.XeMailUtils.sendEmailRegisterSuccessFully;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter @Log4j2
public class User extends User_MAPPED {

    @Override
    public String getProfileImageUrl() {
        return super.getProfileImageUrl();
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    @JsonIgnore
    public List<String> getRoles() {
        return XeStringUtils.splitByComma(this.role);
    }

    @JsonIgnore
    public List<GrantedAuthority> getGrantedAuthority() {
        return this.getFlatRoles().stream()
                .map(Enum::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Set<RoleEnum> getFlatRoles() {
       List<RoleEnum> roles  = this.getRoles().stream()
               .map(RoleEnum::valueOf)
               .collect(Collectors.toList());
       return flatRoles(roles, new HashSet<>());

    }

    Set<RoleEnum> flatRoles(List<RoleEnum> roles, Set<RoleEnum> result) {
        result.addAll(roles);
        roles.forEach(role -> {
            flatRoles(role.getRoleList(), result);
        });
        return result;
    }

    @Transient
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    boolean isPasswordEncoded;
    @Transient
    @JsonIgnore
    String passwordBeforeEncode;

    public void encodePassword(String password) {
        if (!this.isPasswordEncoded) {
            this.passwordBeforeEncode = password;
            this.password = SecurityConfig.getPasswordEncoder().encode(password);
            this.isPasswordEncoded = true;
        }
    }

    public void encodePassword() {
        this.encodePassword(this.password);
    }

    public String getPossibleLoginName() {
        return this.username != null ? username : email;
    }

    public void createSecretPasswordKey() {
        this.secretPasswordKey = XeStringUtils.randomAlphaNumerics(6);
        log.info("secret password key is: " + this.secretPasswordKey);
    }

    public boolean isMatchSecretPasswordKey(String secretKey) {
        return secretKey == null || !secretKey.equals(this.secretPasswordKey);
    }

    public String nullIfNotCurrentPassword(String currentPassword) {
        return currentPassword != null
                && SecurityConfig.getPasswordEncoder().matches(currentPassword, this.password) ? currentPassword : null;
    }

    @Override
    public void postSetFieldAction() {
        this.phoneNumber = XeStringUtils.getNoneDigitChars(this.phoneNumber);
    }

    @Override
    public void prePersist() {
        ErrorCode.PASSWORD_MUST_MORE_THAN_3_CHARS.throwIf(this.password == null || this.password.length() < 3);
        this.encodePassword();
    }

    @Override
    public void postPersist() {
        sendEmailRegisterSuccessFully(this);
        new Thread(() -> TripUser.getRepo()
                .findByPhoneNumberInOrEmailIn(Collections.singletonList(this.phoneNumber), Collections.singletonList(this.email))
                .stream()
                .filter(tripUser -> tripUser.getUser() == null)
                .peek(tripUser -> tripUser.setUser(this))
                .forEach(XeEntity::save)).start();

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

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @JsonProperty
    public void setPassword(String password) {
        super.setPassword(password);
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

