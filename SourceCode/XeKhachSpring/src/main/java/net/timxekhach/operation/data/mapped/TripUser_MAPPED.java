package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.enumeration.TripUserStatus;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.XeDateUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Map;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(TripUser_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class TripUser_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussScheduleId;

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

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussScheduleId;
        protected Long tripId;
        protected Long bussTypeId;
        protected Long tripUserId;
        protected Long bussId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussScheduleIdLong = Long.parseLong(data.get("bussScheduleId"));
            Long tripIdLong = Long.parseLong(data.get("tripId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long tripUserIdLong = Long.parseLong(data.get("tripUserId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{bussScheduleIdLong, tripIdLong, bussTypeIdLong, tripUserIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new TripUser_MAPPED.Pk(bussScheduleIdLong, tripIdLong, bussTypeIdLong, tripUserIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new TripUser_MAPPED.Pk(0L, 0L, 0L, 0L, 0L, 0L);
    }

    protected TripUser_MAPPED(){}
    protected TripUser_MAPPED(Trip trip) {
        this.setTrip(trip);
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
        name = "bussScheduleId",
        referencedColumnName = "bussScheduleId",
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
        if(trip == null) {
            this.companyId = null;
            this.bussTypeId = null;
            this.bussScheduleId = null;
            this.tripId = null;
            this.bussId = null;
            return;
        }
        this.companyId = trip.getCompanyId();
        this.bussTypeId = trip.getBussTypeId();
        this.bussScheduleId = trip.getBussScheduleId();
        this.tripId = trip.getTripId();
        this.bussId = trip.getBussId();
    }

//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "userUserId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
    protected User user;

    public void setUser(User user) {
        this.user = user;
        if(user == null) {
            this.userUserId = null;
            return;
        }
        this.userUserId = user.getUserId();
    }

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
        property = "employeeId")
    protected Employee confirmedBy;

    public void setConfirmedBy(Employee confirmedBy) {
        this.confirmedBy = confirmedBy;
        if(confirmedBy == null) {
            this.confirmedByUserId = null;
            this.confirmedByEmployeeId = null;
            this.confirmedByCompanyId = null;
            return;
        }
        this.confirmedByUserId = confirmedBy.getUserId();
        this.confirmedByEmployeeId = confirmedBy.getEmployeeId();
        this.confirmedByCompanyId = confirmedBy.getCompanyId();
    }

//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//

    @Setter(AccessLevel.PRIVATE)
    protected Long userUserId;
    @Setter(AccessLevel.PRIVATE)
    protected Long confirmedByUserId;
    @Setter(AccessLevel.PRIVATE)
    protected Long confirmedByEmployeeId;
    @Setter(AccessLevel.PRIVATE)
    protected Long confirmedByCompanyId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//

    @Pattern(regexp = "(03|05|07|08|09)+\\d{8,10}")
    protected String phoneNumber;
    @Size(max = 255)
    protected String fullName;

    @Email
    protected String email;

    @Enumerated(EnumType.STRING)
    protected TripUserStatus status = TripUserStatus.PENDING;

    protected Long unitPrice = 0L;

    protected Long totalPrice = 0L;

    protected String tripUserPointsString;

    protected String seatsString;

    protected Date confirmedDateTime;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("phoneNumber")) {
                if(value == null) {this.setPhoneNumber(null); continue;}
                if(value.equals(this.getPhoneNumber())) continue;
                this.setPhoneNumber(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("fullName")) {
                if(value == null) {this.setFullName(null); continue;}
                if(value.equals(this.getFullName())) continue;
                this.setFullName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("email")) {
                if(value == null) {this.setEmail(null); continue;}
                if(value.equals(this.getEmail())) continue;
                this.setEmail(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("status")) {
                if(value == null) {this.setStatus(null); continue;}
                if(value.equals(this.getStatus())) continue;
                this.setStatus(TripUserStatus.valueOf(value));
                continue;
            }
            if (fieldName.equals("unitPrice")) {
                if(value == null) {this.setUnitPrice(null); continue;}
                if(value.equals(this.getUnitPrice())) continue;
                this.setUnitPrice(Long.valueOf(value));
                continue;
            }
            if (fieldName.equals("totalPrice")) {
                if(value == null) {this.setTotalPrice(null); continue;}
                if(value.equals(this.getTotalPrice())) continue;
                this.setTotalPrice(Long.valueOf(value));
                continue;
            }
            if (fieldName.equals("tripUserPointsString")) {
                if(value == null) {this.setTripUserPointsString(null); continue;}
                if(value.equals(this.getTripUserPointsString())) continue;
                this.setTripUserPointsString(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("seatsString")) {
                if(value == null) {this.setSeatsString(null); continue;}
                if(value.equals(this.getSeatsString())) continue;
                this.setSeatsString(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("confirmedDateTime")) {
                if(value == null) {this.setConfirmedDateTime(null); continue;}
                if(value.equals(this.getConfirmedDateTime())) continue;
                this.setConfirmedDateTime(XeDateUtils.dateTimeAppToApi(value));
                continue;
            }
            if (fieldName.equals("user")) {
                if(value == null) {this.setUser(null); continue;}
                this.setUser(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getUserRepository().findByUserId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("confirmedBy")) {
                if(value == null) {this.setConfirmedBy(null); continue;}
                this.setConfirmedBy(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getEmployeeRepository().findByEmployeeId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("trip")) {
                if(value == null) {this.setTrip(null); continue;}
                this.setTrip(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getTripRepository().findByTripId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("bussScheduleId")) {
                this.bussScheduleId = value == null ? null : Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("tripId")) {
                this.tripId = value == null ? null : Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = value == null ? null : Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("tripUserId")) {
                this.tripUserId = value == null ? null : Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussId")) {
                this.bussId = value == null ? null : Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = value == null ? null : Long.valueOf(value);
            }
        }
    }
}
