package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.enumeration.TripStatus;
import java.util.Date;
import net.timxekhach.operation.data.entity.TripUserSeat;
import net.timxekhach.operation.data.entity.BussPoint;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.timxekhach.operation.data.entity.TripUser;
import org.apache.commons.lang3.time.DateUtils;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.timxekhach.operation.data.entity.Buss;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(Trip_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Trip_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussScheduleId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long tripId;

    protected Long getIncrementId() {
        return this.tripId;
    }
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

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussScheduleId;
        protected Long tripId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussScheduleIdLong = Long.parseLong(data.get("bussScheduleId"));
            Long tripIdLong = Long.parseLong(data.get("tripId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{bussScheduleIdLong, tripIdLong, bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new Trip_MAPPED.Pk(bussScheduleIdLong, tripIdLong, bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Trip_MAPPED.Pk(0L, 0L, 0L, 0L, 0L);
    }

    protected Trip_MAPPED(){}
    protected Trip_MAPPED(BussSchedule bussSchedule) {
        this.setBussSchedule(bussSchedule);
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
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bussScheduleId")
    protected BussSchedule bussSchedule;

    public BussSchedule getBussSchedule(){
        if (this.bussSchedule == null) {
            this.bussSchedule = CommonUpdateService.getBussScheduleRepository().findByBussScheduleId(this.bussScheduleId);
        }
        return this.bussSchedule;
    }

    public void setBussSchedule(BussSchedule bussSchedule) {
        this.bussSchedule = bussSchedule;
        this.companyId = bussSchedule.getCompanyId();
        this.bussTypeId = bussSchedule.getBussTypeId();
        this.bussScheduleId = bussSchedule.getBussScheduleId();
        this.bussId = bussSchedule.getBussId();
    }
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "allTripUserSeatsBussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allTripUserSeatsUserId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allTripUserSeatsBussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allTripUserSeatsCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allTripUserSeatsTripId",
        referencedColumnName = "tripId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allTripUserSeatsTripUserId",
        referencedColumnName = "tripUserId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allTripUserSeatsTripUserSeatId",
        referencedColumnName = "tripUserSeatId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "allTripUserSeatsBussScheduleId",
        referencedColumnName = "bussScheduleId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "allTripUserSeatsId")
    protected TripUserSeat allTripUserSeats;

    public void setAllTripUserSeats(TripUserSeat allTripUserSeats) {
        this.allTripUserSeats = allTripUserSeats;
        this.allTripUserSeatsBussId = allTripUserSeats.getBussId();
        this.allTripUserSeatsUserId = allTripUserSeats.getUserId();
        this.allTripUserSeatsBussTypeId = allTripUserSeats.getBussTypeId();
        this.allTripUserSeatsCompanyId = allTripUserSeats.getCompanyId();
        this.allTripUserSeatsTripId = allTripUserSeats.getTripId();
        this.allTripUserSeatsTripUserId = allTripUserSeats.getTripUserId();
        this.allTripUserSeatsTripUserSeatId = allTripUserSeats.getTripUserSeatId();
        this.allTripUserSeatsBussScheduleId = allTripUserSeats.getBussScheduleId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "startPointXeLocationId",
        referencedColumnName = "xeLocationId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "startPointBussPointId",
        referencedColumnName = "bussPointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "startPointCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "startPointId")
    protected BussPoint startPoint;

    public void setStartPoint(BussPoint startPoint) {
        this.startPoint = startPoint;
        this.startPointXeLocationId = startPoint.getXeLocationId();
        this.startPointBussPointId = startPoint.getBussPointId();
        this.startPointCompanyId = startPoint.getCompanyId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "endPointBussPointId",
        referencedColumnName = "bussPointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "endPointXeLocationId",
        referencedColumnName = "xeLocationId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "endPointCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "endPointId")
    protected BussPoint endPoint;

    public void setEndPoint(BussPoint endPoint) {
        this.endPoint = endPoint;
        this.endPointBussPointId = endPoint.getBussPointId();
        this.endPointXeLocationId = endPoint.getXeLocationId();
        this.endPointCompanyId = endPoint.getCompanyId();
    }

    @OneToMany(
        mappedBy = "trip",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    protected List<TripUser> tripUsers = new ArrayList<>();
//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//

    @Setter(AccessLevel.PRIVATE)
    protected Long allTripUserSeatsBussId;
    @Setter(AccessLevel.PRIVATE)
    protected Long allTripUserSeatsUserId;
    @Setter(AccessLevel.PRIVATE)
    protected Long allTripUserSeatsBussTypeId;
    @Setter(AccessLevel.PRIVATE)
    protected Long allTripUserSeatsCompanyId;
    @Setter(AccessLevel.PRIVATE)
    protected Long allTripUserSeatsTripId;
    @Setter(AccessLevel.PRIVATE)
    protected Long allTripUserSeatsTripUserId;
    @Setter(AccessLevel.PRIVATE)
    protected Long allTripUserSeatsTripUserSeatId;
    @Setter(AccessLevel.PRIVATE)
    protected Long allTripUserSeatsBussScheduleId;
    @Setter(AccessLevel.PRIVATE)
    protected Long startPointXeLocationId;
    @Setter(AccessLevel.PRIVATE)
    protected Long startPointBussPointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long startPointCompanyId;
    @Setter(AccessLevel.PRIVATE)
    protected Long endPointBussPointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long endPointXeLocationId;
    @Setter(AccessLevel.PRIVATE)
    protected Long endPointCompanyId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//

    protected Long price = 0L;

    @Enumerated(EnumType.STRING)
    protected TripStatus status;

    protected Date startTime;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("price")) {
                this.price = Long.valueOf(value);
                continue;
            }
            if (fieldName.equals("status")) {
                this.status = TripStatus.valueOf(value);
                continue;
            }
            if (fieldName.equals("startTime")) {
                try {
                this.startTime = DateUtils.parseDate(value);
                } catch (Exception e) {
                ErrorCode.INVALID_TIME_FORMAT.throwNow(fieldName);
                }
                continue;
            }
            if (fieldName.equals("bussScheduleId")) {
                this.bussScheduleId = Long.valueOf(value);
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
            if (fieldName.equals("bussId")) {
                this.bussId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = Long.valueOf(value);
            }
        }
    }
}
