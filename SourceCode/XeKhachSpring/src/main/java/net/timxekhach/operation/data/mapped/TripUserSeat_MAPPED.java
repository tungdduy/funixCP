package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.entity.User;


@MappedSuperclass @Getter @Setter
@IdClass(TripUserSeat_MAPPED.Pk.class)
public abstract class TripUserSeat_MAPPED extends XeEntity {

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

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long seatTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripUserSeatId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long tripId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
        protected Long userId;
        protected Long seatTypeId;
        protected Long tripUserSeatId;
    }
    protected TripUserSeat_MAPPED(){}
    protected TripUserSeat_MAPPED(Trip trip, User user, SeatType seatType) {
        this.trip = trip;
        this.user = user;
        this.seatType = seatType;
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
        name = "bussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "seatTypeId",
        referencedColumnName = "seatTypeId",
        insertable = false,
        updatable = false)
    })
    protected SeatType seatType;

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
        this.bussTypeId = seatType.getBussTypeId();
        this.seatTypeId = seatType.getSeatTypeId();
    }

}
