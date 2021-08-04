package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import java.util.Map;
import net.timxekhach.operation.data.enumeration.TripUserStatus;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass @Getter @Setter
@IdClass(TripUser_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class TripUser_MAPPED extends XeEntity {

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long tripUserId;

    protected Long getIncrementId() {
        return this.tripUserId;
    }
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

    public static Pk pk(Map<String, String> data) {
        try {
            Long tripIdLong = Long.parseLong(data.get("tripId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long tripUserIdLong = Long.parseLong(data.get("tripUserId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long userIdLong = Long.parseLong(data.get("userId"));
            if(NumberUtils.min(new long[]{tripIdLong, bussTypeIdLong, tripUserIdLong, bussIdLong, companyIdLong, userIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new TripUser_MAPPED.Pk(tripIdLong, bussTypeIdLong, tripUserIdLong, bussIdLong, companyIdLong, userIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new TripUser_MAPPED.Pk(0L, 0L, 0L, 0L, 0L, 0L);
    }

    protected TripUser_MAPPED(){}
    protected TripUser_MAPPED(Trip trip, User user) {
        this.setTrip(trip);
        this.setUser(user);
    }
//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
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
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "confirmedByUserId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false), 
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
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "confirmedById")
    protected Employee confirmedBy;

    public void setConfirmedBy(Employee confirmedBy) {
        this.confirmedBy = confirmedBy;
        this.confirmedByUserId = confirmedBy.getUserId();
        this.confirmedByEmployeeId = confirmedBy.getEmployeeId();
        this.confirmedByCompanyId = confirmedBy.getCompanyId();
    }

//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//
    @Setter(AccessLevel.PRIVATE)
    protected Long confirmedByUserId;
    @Setter(AccessLevel.PRIVATE)
    protected Long confirmedByEmployeeId;
    @Setter(AccessLevel.PRIVATE)
    protected Long confirmedByCompanyId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//

    protected Long totalPrice;

    @Enumerated(EnumType.STRING)
    protected TripUserStatus status = net.timxekhach.operation.data.enumeration.TripUserStatus.PENDING;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("totalPrice")) {
                this.totalPrice = Long.valueOf(value);
                continue;
            }
            if (fieldName.equals("status")) {
                this.status = TripUserStatus.valueOf(value);
                continue;
            }
            if (fieldName.equals("tripId")) {
                this.tripId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("tripUserId")) {
                this.tripUserId = Long.valueOf(value);
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
            if (fieldName.equals("userId")) {
                this.userId = Long.valueOf(value);
            }
        }
    }
}
