package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.utility.XeDateUtils;
import java.util.Date;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.BussPoint;
import java.util.List;
import java.util.ArrayList;
import net.timxekhach.operation.data.entity.BussSchedulePoint;
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
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(BussSchedule_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussSchedule_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussScheduleId;

    protected Long getIncrementId() {
        return this.bussScheduleId;
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
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussScheduleIdLong = Long.parseLong(data.get("bussScheduleId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{bussScheduleIdLong, bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussSchedule_MAPPED.Pk(bussScheduleIdLong, bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussSchedule_MAPPED.Pk(0L, 0L, 0L, 0L);
    }

    protected BussSchedule_MAPPED(){}
    protected BussSchedule_MAPPED(Buss buss) {
        this.setBuss(buss);
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
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bussId")
    protected Buss buss;

    public Buss getBuss(){
        if (this.buss == null) {
            this.buss = CommonUpdateService.getBussRepository().findByBussId(this.bussId);
        }
        return this.buss;
    }

    public void setBuss(Buss buss) {
        this.buss = buss;
        this.companyId = buss.getCompanyId();
        this.bussTypeId = buss.getBussTypeId();
        this.bussId = buss.getBussId();
    }
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "startPointLocationId",
        referencedColumnName = "locationId",
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
        property = "bussPointId")
    protected BussPoint startPoint;

    public void setStartPoint(BussPoint startPoint) {
        this.startPoint = startPoint;
        this.startPointLocationId = startPoint.getLocationId();
        this.startPointBussPointId = startPoint.getBussPointId();
        this.startPointCompanyId = startPoint.getCompanyId();
    }

    @OneToMany(
        mappedBy = "bussSchedule",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<BussSchedulePoint> middlePoints = new ArrayList<>();
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "endPointBussPointId",
        referencedColumnName = "bussPointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "endPointLocationId",
        referencedColumnName = "locationId",
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
        property = "bussPointId")
    protected BussPoint endPoint;

    public void setEndPoint(BussPoint endPoint) {
        this.endPoint = endPoint;
        this.endPointBussPointId = endPoint.getBussPointId();
        this.endPointLocationId = endPoint.getLocationId();
        this.endPointCompanyId = endPoint.getCompanyId();
    }

//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//

    @Setter(AccessLevel.PRIVATE)
    protected Long startPointLocationId;
    @Setter(AccessLevel.PRIVATE)
    protected Long startPointBussPointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long startPointCompanyId;
    @Setter(AccessLevel.PRIVATE)
    protected Long endPointBussPointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long endPointLocationId;
    @Setter(AccessLevel.PRIVATE)
    protected Long endPointCompanyId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//

    protected Long price = 0L;

    protected Date launchTime;

    protected Date effectiveDateFrom;
    @Size(max = 255)
    protected String workingDays;
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
            if (fieldName.equals("launchTime")) {
                this.launchTime = XeDateUtils.timeAppToApi(value);
                continue;
            }
            if (fieldName.equals("effectiveDateFrom")) {
                this.effectiveDateFrom = XeDateUtils.dateAppToApi(value);
                continue;
            }
            if (fieldName.equals("workingDays")) {
                this.workingDays = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussScheduleId")) {
                this.bussScheduleId = Long.valueOf(value);
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
