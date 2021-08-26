package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.PathPoint;
import net.timxekhach.operation.data.entity.TripUser;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(TripUserPoint_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class TripUserPoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long pathPointId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long pathId;

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
    protected Long locationId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long tripUserPointId;

    protected Long getIncrementId() {
        return this.tripUserPointId;
    }
    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long tripUserId;

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
        protected Long pathPointId;
        protected Long pathId;
        protected Long bussScheduleId;
        protected Long tripId;
        protected Long locationId;
        protected Long tripUserPointId;
        protected Long bussTypeId;
        protected Long tripUserId;
        protected Long bussId;
        protected Long companyId;
        protected Long userId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long pathPointIdLong = Long.parseLong(data.get("pathPointId"));
            Long pathIdLong = Long.parseLong(data.get("pathId"));
            Long bussScheduleIdLong = Long.parseLong(data.get("bussScheduleId"));
            Long tripIdLong = Long.parseLong(data.get("tripId"));
            Long locationIdLong = Long.parseLong(data.get("locationId"));
            Long tripUserPointIdLong = Long.parseLong(data.get("tripUserPointId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long tripUserIdLong = Long.parseLong(data.get("tripUserId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long userIdLong = Long.parseLong(data.get("userId"));
            if(NumberUtils.min(new long[]{pathPointIdLong, pathIdLong, bussScheduleIdLong, tripIdLong, locationIdLong, tripUserPointIdLong, bussTypeIdLong, tripUserIdLong, bussIdLong, companyIdLong, userIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new TripUserPoint_MAPPED.Pk(pathPointIdLong, pathIdLong, bussScheduleIdLong, tripIdLong, locationIdLong, tripUserPointIdLong, bussTypeIdLong, tripUserIdLong, bussIdLong, companyIdLong, userIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new TripUserPoint_MAPPED.Pk(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L);
    }

    protected TripUserPoint_MAPPED(){}
    protected TripUserPoint_MAPPED(PathPoint pathPoint, TripUser tripUser) {
        this.setPathPoint(pathPoint);
        this.setTripUser(tripUser);
    }
//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "pathPointId",
        referencedColumnName = "pathPointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "locationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pathId",
        referencedColumnName = "pathId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "pathPointId")
    protected PathPoint pathPoint;

    public PathPoint getPathPoint(){
        if (this.pathPoint == null) {
            this.pathPoint = CommonUpdateService.getPathPointRepository().findByPathPointId(this.pathPointId);
        }
        return this.pathPoint;
    }

    public void setPathPoint(PathPoint pathPoint) {
        this.pathPoint = pathPoint;
        if(pathPoint == null) {
            this.pathPointId = null;
            this.companyId = null;
            this.locationId = null;
            this.pathId = null;
            return;
        }
        this.pathPointId = pathPoint.getPathPointId();
        this.companyId = pathPoint.getCompanyId();
        this.locationId = pathPoint.getLocationId();
        this.pathId = pathPoint.getPathId();
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
        name = "tripUserId",
        referencedColumnName = "tripUserId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "userId",
        referencedColumnName = "userId",
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
        property = "tripUserId")
    protected TripUser tripUser;

    public TripUser getTripUser(){
        if (this.tripUser == null) {
            this.tripUser = CommonUpdateService.getTripUserRepository().findByTripUserId(this.tripUserId);
        }
        return this.tripUser;
    }

    public void setTripUser(TripUser tripUser) {
        this.tripUser = tripUser;
        if(tripUser == null) {
            this.companyId = null;
            this.bussTypeId = null;
            this.bussScheduleId = null;
            this.tripId = null;
            this.tripUserId = null;
            this.userId = null;
            this.bussId = null;
            return;
        }
        this.companyId = tripUser.getCompanyId();
        this.bussTypeId = tripUser.getBussTypeId();
        this.bussScheduleId = tripUser.getBussScheduleId();
        this.tripId = tripUser.getTripId();
        this.tripUserId = tripUser.getTripUserId();
        this.userId = tripUser.getUserId();
        this.bussId = tripUser.getBussId();
    }

//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//


    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("pathPoint")) {
                this.setPathPoint(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getPathPointRepository().findByPathPointId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("tripUser")) {
                this.setTripUser(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getTripUserRepository().findByTripUserId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("pathPointId")) {
                this.pathPointId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("pathId")) {
                this.pathId = Long.valueOf(value);
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
            if (fieldName.equals("locationId")) {
                this.locationId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("tripUserPointId")) {
                this.tripUserPointId = Long.valueOf(value);
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
