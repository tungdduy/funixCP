package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import java.util.Map;
import net.timxekhach.operation.data.entity.SeatType;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass @Getter @Setter
@IdClass(TripUserSeat_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class TripUserSeat_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long tripId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long companyId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long seatTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long userId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long tripUserSeatId;

    protected Long getIncrementId() {
        return this.tripUserSeatId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long tripId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
        protected Long seatTypeId;
        protected Long userId;
        protected Long tripUserSeatId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long tripIdLong = Long.parseLong(data.get("tripId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long seatTypeIdLong = Long.parseLong(data.get("seatTypeId"));
            Long userIdLong = Long.parseLong(data.get("userId"));
            Long tripUserSeatIdLong = Long.parseLong(data.get("tripUserSeatId"));
            if(NumberUtils.min(new long[]{tripIdLong, bussTypeIdLong, bussIdLong, companyIdLong, seatTypeIdLong, userIdLong, tripUserSeatIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new TripUserSeat_MAPPED.Pk(tripIdLong, bussTypeIdLong, bussIdLong, companyIdLong, seatTypeIdLong, userIdLong, tripUserSeatIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new TripUserSeat_MAPPED.Pk(0L, 0L, 0L, 0L, 0L, 0L, 0L);
    }

    protected TripUserSeat_MAPPED(){}
    protected TripUserSeat_MAPPED(SeatType seatType, Trip trip, User user) {
        this.setSeatType(seatType);
        this.setTrip(trip);
        this.setUser(user);
    }
//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
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
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "seatTypeId")
    protected SeatType seatType;

    public SeatType getSeatType(){
        if (this.seatType == null) {
            this.seatType = CommonUpdateService.getSeatTypeRepository().findBySeatTypeId(this.seatTypeId);
        }
        return this.seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
        this.bussTypeId = seatType.getBussTypeId();
        this.seatTypeId = seatType.getSeatTypeId();
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
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "tripId")
    protected Trip trip;

    public Trip getTrip(){
        if (this.trip == null) {
            this.trip = CommonUpdateService.getTripRepository().findByTripId(this.tripId);
        }
        return this.trip;
    }

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
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
    protected User user;

    public User getUser(){
        if (this.user == null) {
            this.user = CommonUpdateService.getUserRepository().findByUserId(this.userId);
        }
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getUserId();
    }
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//


    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("tripId")) {
                this.tripId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussId")) {
                this.bussId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("seatTypeId")) {
                this.seatTypeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("userId")) {
                this.userId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("tripUserSeatId")) {
                this.tripUserSeatId = Long.valueOf(value);
            }
        }
    }
}