package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.enumeration.TripUserStatus;
import net.timxekhach.operation.data.entity.User;


@MappedSuperclass @Getter @Setter
@IdClass(TripUser_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class TripUser_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripUserId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long companyId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long userId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long tripId;
        protected Long bussTypeId;
        protected Long tripUserId;
        protected Long bussId;
        protected Long companyId;
        protected Long userId;
    }
    protected TripUser_MAPPED(){}
    protected TripUser_MAPPED(Trip trip, User user) {
        this.trip = trip;
        this.user = user;
    }
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "bussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripId",
        referencedColumnName = "tripId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    protected Trip trip;

    public void setTrip(Trip trip) {
        this.trip = trip;
        this.companyId = trip.getCompanyId();
        this.bussTypeId = trip.getBussTypeId();
        this.tripId = trip.getTripId();
        this.bussId = trip.getBussId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "userId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false)
    })
    protected User user;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getUserId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "confirmedByEmployeeId",
        referencedColumnName = "employeeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "confirmedByCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    protected Employee confirmedBy;

    public void setConfirmedBy(Employee confirmedBy) {
        this.confirmedBy = confirmedBy;
        this.confirmedByEmployeeId = confirmedBy.getEmployeeId();
        this.confirmedByCompanyId = confirmedBy.getCompanyId();
    }

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long confirmedByEmployeeId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long confirmedByCompanyId;


    protected Long totalPrice;


    @Enumerated(EnumType.STRING)
    protected TripUserStatus status = net.timxekhach.operation.data.enumeration.TripUserStatus.PENDING;

}
