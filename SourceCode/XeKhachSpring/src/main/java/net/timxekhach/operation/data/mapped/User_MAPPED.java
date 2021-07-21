package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.TripUser;


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
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "allMyTripsCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allMyTripsBussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allMyTripsUserId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allMyTripsBussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allMyTripsTripId",
        referencedColumnName = "tripId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allMyTripsTripUserId",
        referencedColumnName = "tripUserId",
        insertable = false,
        updatable = false)
    })
    protected TripUser allMyTrips;

    public void setAllMyTrips(TripUser allMyTrips) {
        this.allMyTrips = allMyTrips;
        this.allMyTripsCompanyId = allMyTrips.getCompanyId();
        this.allMyTripsBussId = allMyTrips.getBussId();
        this.allMyTripsUserId = allMyTrips.getUserId();
        this.allMyTripsBussTypeId = allMyTrips.getBussTypeId();
        this.allMyTripsTripId = allMyTrips.getTripId();
        this.allMyTripsTripUserId = allMyTrips.getTripUserId();
    }

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long allMyTripsCompanyId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long allMyTripsBussId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long allMyTripsUserId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long allMyTripsBussTypeId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long allMyTripsTripId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long allMyTripsTripUserId;


    @Email
    protected String email;


    @Pattern(regexp = "(03|05|07|08|09)+\\d{8,10}")
    protected String phoneNumber;

    @Size(max = 255)
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

}
