package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import java.util.List;
import net.timxekhach.operation.data.entity.TripUser;
import java.util.ArrayList;


@MappedSuperclass @Getter @Setter
@IdClass(User_MAPPED.Pk.class)
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
    @OneToMany(
        mappedBy = "user",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<TripUser> allMyTrips = new ArrayList<>();


    @Email
    @Column
    protected String email;


    @Pattern(regexp = "(03|05|07|08|09)+\\d{8,10}")
    @Column
    protected String phoneNumber;

    @Size(max = 255)
    @Column
    protected String password;

    @Size(max = 255)
    @Column
    protected String username;

    @Size(max = 30, min = 3)
    @NotBlank
    @Column
    protected String fullName;

    @Size(max = 255)
    @Column
    protected String role = "ROLE_USER";


    @Column
    protected Boolean nonLocked = false;

}
