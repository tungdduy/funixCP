package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.entity.PathPoint;
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
@IdClass(BussSchedulePoint_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussSchedulePoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long pathPointId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussScheduleId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long pathId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussSchedulePointId;

    protected Long getIncrementId() {
        return this.bussSchedulePointId;
    }
    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long locationId;

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
        protected Long pathPointId;
        protected Long bussScheduleId;
        protected Long pathId;
        protected Long bussSchedulePointId;
        protected Long locationId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long pathPointIdLong = Long.parseLong(data.get("pathPointId"));
            Long bussScheduleIdLong = Long.parseLong(data.get("bussScheduleId"));
            Long pathIdLong = Long.parseLong(data.get("pathId"));
            Long bussSchedulePointIdLong = Long.parseLong(data.get("bussSchedulePointId"));
            Long locationIdLong = Long.parseLong(data.get("locationId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{pathPointIdLong, bussScheduleIdLong, pathIdLong, bussSchedulePointIdLong, locationIdLong, bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussSchedulePoint_MAPPED.Pk(pathPointIdLong, bussScheduleIdLong, pathIdLong, bussSchedulePointIdLong, locationIdLong, bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussSchedulePoint_MAPPED.Pk(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L);
    }

    protected BussSchedulePoint_MAPPED(){}
    protected BussSchedulePoint_MAPPED(BussSchedule bussSchedule, PathPoint pathPoint) {
        this.setBussSchedule(bussSchedule);
        this.setPathPoint(pathPoint);
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
        if(bussSchedule == null) {
            this.companyId = null;
            this.bussTypeId = null;
            this.bussScheduleId = null;
            this.bussId = null;
            return;
        }
        this.companyId = bussSchedule.getCompanyId();
        this.bussTypeId = bussSchedule.getBussTypeId();
        this.bussScheduleId = bussSchedule.getBussScheduleId();
        this.bussId = bussSchedule.getBussId();
    }

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

//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//


    protected Long price = 0L;

    protected Boolean isDeductPrice = false;

    protected String searchText;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("price")) {
                this.setPrice(Long.valueOf(value));
                continue;
            }
            if (fieldName.equals("isDeductPrice")) {
                this.setIsDeductPrice(Boolean.valueOf(value));
                continue;
            }
            if (fieldName.equals("searchText")) {
                this.setSearchText(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("bussSchedule")) {
                this.setBussSchedule(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getBussScheduleRepository().findByBussScheduleId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("pathPoint")) {
                this.setPathPoint(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getPathPointRepository().findByPathPointId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("pathPointId")) {
                this.pathPointId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussScheduleId")) {
                this.bussScheduleId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("pathId")) {
                this.pathId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussSchedulePointId")) {
                this.bussSchedulePointId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("locationId")) {
                this.locationId = Long.valueOf(value);
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
