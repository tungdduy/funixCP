package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.entity.BussSchedule;
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
    protected Long bussScheduleId;

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

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussPointId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussScheduleId;
        protected Long bussSchedulePointId;
        protected Long locationId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
        protected Long bussPointId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussScheduleIdLong = Long.parseLong(data.get("bussScheduleId"));
            Long bussSchedulePointIdLong = Long.parseLong(data.get("bussSchedulePointId"));
            Long locationIdLong = Long.parseLong(data.get("locationId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long bussPointIdLong = Long.parseLong(data.get("bussPointId"));
            if(NumberUtils.min(new long[]{bussScheduleIdLong, bussSchedulePointIdLong, locationIdLong, bussTypeIdLong, bussIdLong, companyIdLong, bussPointIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussSchedulePoint_MAPPED.Pk(bussScheduleIdLong, bussSchedulePointIdLong, locationIdLong, bussTypeIdLong, bussIdLong, companyIdLong, bussPointIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussSchedulePoint_MAPPED.Pk(0L, 0L, 0L, 0L, 0L, 0L, 0L);
    }

    protected BussSchedulePoint_MAPPED(){}
    protected BussSchedulePoint_MAPPED(BussPoint bussPoint, BussSchedule bussSchedule) {
        this.setBussPoint(bussPoint);
        this.setBussSchedule(bussSchedule);
    }
//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "bussPointId",
        referencedColumnName = "bussPointId",
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
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bussPointId")
    protected BussPoint bussPoint;

    public BussPoint getBussPoint(){
        if (this.bussPoint == null) {
            this.bussPoint = CommonUpdateService.getBussPointRepository().findByBussPointId(this.bussPointId);
        }
        return this.bussPoint;
    }

    public void setBussPoint(BussPoint bussPoint) {
        this.bussPoint = bussPoint;
        this.bussPointId = bussPoint.getBussPointId();
        this.companyId = bussPoint.getCompanyId();
        this.locationId = bussPoint.getLocationId();
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


    protected Long priceToEndPoint = 0L;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("priceToEndPoint")) {
                this.priceToEndPoint = Long.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussScheduleId")) {
                this.bussScheduleId = Long.valueOf(value);
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
                    continue;
            }
            if (fieldName.equals("bussPointId")) {
                this.bussPointId = Long.valueOf(value);
            }
        }
    }
}
