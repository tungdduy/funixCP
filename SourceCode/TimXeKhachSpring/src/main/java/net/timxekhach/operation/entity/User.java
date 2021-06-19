package net.timxekhach.operation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.security.RoleEnum;
import net.timxekhach.utility.XeStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.timxekhach.utility.XeStringUtils.PHONE_REGEX;

@Entity
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 4, max=30)
    @NotBlank()
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max=30)
    private String username;

    @Pattern(regexp = PHONE_REGEX)
    private String phoneNumber;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String role = RoleEnum.ROLE_USER.name();

    public List<String> getRoles() {
        return XeStringUtils.splitByComma(this.role);
    }

    public List<GrantedAuthority> getGrantedAuthority() {
        return this.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Boolean nonLocked = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public void encodePassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(this.password);
    }
}
