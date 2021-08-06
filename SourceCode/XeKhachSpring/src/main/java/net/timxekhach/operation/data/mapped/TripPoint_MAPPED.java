package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.apache.commons.lang3.math.NumberUtils;
import net.timxekhach.operation.data.entity.BussTrip;
import javax.persistence.*;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.timxekhach.operation.data.entity.BussPoint;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(TripPoint_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class TripPoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussTripId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long tripPointId;

    protected Long getIncrementId() {
        return this.tripPointId;
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
        protected Long bussTripId;
        protected Long tripPointId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussTripIdLong = Long.parseLong(data.get("bussTripId"));
            Long tripPointIdLong = Long.parseLong(data.get("tripPointId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{bussTripIdLong, tripPointIdLong, bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new TripPoint_MAPPED.Pk(bussTripIdLong, tripPointIdLong, bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new TripPoint_MAPPED.Pk(0L, 0L, 0L, 0L, 0L);
    }

    protected TripPoint_MAPPED(){}
    protected TripPoint_MAPPED(BussTrip bussTrip) {
        this.setBussTrip(bussTrip);
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
        name = "bussTripId",
        referencedColumnName = "bussTripId",
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
        property = "bussTripId")
    protected BussTrip bussTrip;

    public BussTrip getBussTrip(){
        if (this.bussTrip == null) {
            this.bussTrip = CommonUpdateService.getBussTripRepository().findByBussTripId(this.bussTripId);
        }
        return this.bussTrip;
    }

    public void setBussTrip(BussTrip bussTrip) {
        this.bussTrip = bussTrip;
        this.companyId = bussTrip.getCompanyId();
        this.bussTypeId = bussTrip.getBussTypeId();
        this.bussTripId = bussTrip.getBussTripId();
        this.bussId = bussTrip.getBussId();
    }
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "stopPointLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "stopPointBussPointId",
        referencedColumnName = "bussPointId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "stopPointId")
    protected BussPoint stopPoint;

    public void setStopPoint(BussPoint stopPoint) {
        this.stopPoint = stopPoint;
        this.stopPointLocationId = stopPoint.getLocationId();
        this.stopPointBussPointId = stopPoint.getBussPointId();
    }

//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//

    @Setter(AccessLevel.PRIVATE)
    protected Long stopPointLocationId;
    @Setter(AccessLevel.PRIVATE)
    protected Long stopPointBussPointId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussTripId")) {
                this.bussTripId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("tripPointId")) {
                this.tripPointId = Long.valueOf(value);
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
