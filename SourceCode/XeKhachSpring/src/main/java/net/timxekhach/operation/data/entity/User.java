package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import net.timxekhach.utility.XeStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;
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

    public void encodePassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(this.password);
    }

    public String getPossibleLoginName() {
        return this.username != null ? username : email;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


}

