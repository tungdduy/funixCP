package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.entity.BussSchedulePoint;
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
@IdClass(BussSchedulePrice_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussSchedulePrice_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussScheduleId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussSchedulePriceId;

    protected Long getIncrementId() {
        return this.bussSchedulePriceId;
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
        protected Long bussSchedulePriceId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussScheduleIdLong = Long.parseLong(data.get("bussScheduleId"));
            Long bussSchedulePriceIdLong = Long.parseLong(data.get("bussSchedulePriceId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{bussScheduleIdLong, bussSchedulePriceIdLong, bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussSchedulePrice_MAPPED.Pk(bussScheduleIdLong, bussSchedulePriceIdLong, bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussSchedulePrice_MAPPED.Pk(0L, 0L, 0L, 0L, 0L);
    }

    protected BussSchedulePrice_MAPPED(){}
    protected BussSchedulePrice_MAPPED(BussSchedule bussSchedule) {
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
        name = "pointFromCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointFromBussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointFromBussSchedulePointId",
        referencedColumnName = "bussSchedulePointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointFromBussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointFromBussPointId",
        referencedColumnName = "bussPointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointFromLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointFromBussScheduleId",
        referencedColumnName = "bussScheduleId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bussSchedulePointId")
    protected BussSchedulePoint pointFrom;

    public void setPointFrom(BussSchedulePoint pointFrom) {
        this.pointFrom = pointFrom;
        this.pointFromCompanyId = pointFrom.getCompanyId();
        this.pointFromBussTypeId = pointFrom.getBussTypeId();
        this.pointFromBussSchedulePointId = pointFrom.getBussSchedulePointId();
        this.pointFromBussId = pointFrom.getBussId();
        this.pointFromBussPointId = pointFrom.getBussPointId();
        this.pointFromLocationId = pointFrom.getLocationId();
        this.pointFromBussScheduleId = pointFrom.getBussScheduleId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "pointToLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointToBussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointToBussSchedulePointId",
        referencedColumnName = "bussSchedulePointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointToBussScheduleId",
        referencedColumnName = "bussScheduleId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointToBussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointToBussPointId",
        referencedColumnName = "bussPointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pointToCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bussSchedulePointId")
    protected BussSchedulePoint pointTo;

    public void setPointTo(BussSchedulePoint pointTo) {
        this.pointTo = pointTo;
        this.pointToLocationId = pointTo.getLocationId();
        this.pointToBussId = pointTo.getBussId();
        this.pointToBussSchedulePointId = pointTo.getBussSchedulePointId();
        this.pointToBussScheduleId = pointTo.getBussScheduleId();
        this.pointToBussTypeId = pointTo.getBussTypeId();
        this.pointToBussPointId = pointTo.getBussPointId();
        this.pointToCompanyId = pointTo.getCompanyId();
    }

//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//

    @Setter(AccessLevel.PRIVATE)
    protected Long pointFromCompanyId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointFromBussTypeId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointFromBussSchedulePointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointFromBussId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointFromBussPointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointFromLocationId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointFromBussScheduleId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointToLocationId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointToBussId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointToBussSchedulePointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointToBussScheduleId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointToBussTypeId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointToBussPointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long pointToCompanyId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//

    protected Long price = 0L;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("price")) {
                this.price = Long.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussScheduleId")) {
                this.bussScheduleId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussSchedulePriceId")) {
                this.bussSchedulePriceId = Long.valueOf(value);
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
