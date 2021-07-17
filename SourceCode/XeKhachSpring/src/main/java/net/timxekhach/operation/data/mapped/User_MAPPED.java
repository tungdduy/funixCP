package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;


@MappedSuperclass @Getter @Setter
@IdClass(User_MAPPED.Pk.class)
public abstract class User_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long userId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long userId;
    }

    @Email
    @Column
    protected String email;


    @Pattern(regexp = "(03|05|07|08|09)+\\d{8,10}")
    @Column
    protected String phoneNumber;

    @Size(max = 30, min = 3)
    @NotBlank
    @Column
    protected String password;

    @Size(max = 30, min = 3)
    @NotBlank
    @Column
    protected String username;

    @Size(max = 255)
    @Column
    protected String role = "ROLE_USER";


    @Column
    protected Boolean nonLocked = false;

}
