package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.utility.XeDateUtils;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
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

//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
    @OneToMany(
        mappedBy = "trip",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<TripUser> tripUsers = new ArrayList<>();
//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//


    protected String lockedSeatsString;

    protected Long tripUnitPrice = 0L;

    protected Date launchTime;

    protected Date launchDate;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("lockedSeatsString")) {
                this.setLockedSeatsString(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("tripUnitPrice")) {
                this.setTripUnitPrice(Long.valueOf(value));
                continue;
            }
            if (fieldName.equals("launchTime")) {
                this.setLaunchTime(XeDateUtils.timeAppToApi(value));
                continue;
            }
            if (fieldName.equals("launchDate")) {
                this.setLaunchDate(XeDateUtils.dateAppToApi(value));
                continue;
            }
            if (fieldName.equals("bussSchedule")) {
                this.setBussSchedule(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getBussScheduleRepository().findByBussScheduleId(Long.valueOf(value))));
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
