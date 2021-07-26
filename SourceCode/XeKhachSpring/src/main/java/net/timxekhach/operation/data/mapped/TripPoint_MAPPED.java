package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import org.apache.commons.lang3.math.NumberUtils;;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import javax.persistence.*;;
import net.timxekhach.operation.data.entity.BussTrip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.timxekhach.operation.data.entity.BussPoint;
import lombok.*;;
import net.timxekhach.operation.response.ErrorCode;;


@MappedSuperclass @Getter @Setter
@IdClass(TripPoint_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class TripPoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussTripId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripPointId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
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
            };
            return new TripPoint_MAPPED.Pk(bussTripIdLong, tripPointIdLong, bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new TripPoint_MAPPED.Pk(0L, 0L, 0L, 0L, 0L);
    }

    protected TripPoint_MAPPED(){}
    protected TripPoint_MAPPED(BussTrip bussTrip) {
        this.bussTrip = bussTrip;
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
    @JsonIgnore
    protected BussTrip bussTrip;

    public void setBussTrip(BussTrip bussTrip) {
        this.bussTrip = bussTrip;
        this.companyId = bussTrip.getCompanyId();
        this.bussTypeId = bussTrip.getBussTypeId();
        this.bussTripId = bussTrip.getBussTripId();
        this.bussId = bussTrip.getBussId();
    }

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
    @JsonIgnore
    protected BussPoint stopPoint;

    public void setStopPoint(BussPoint stopPoint) {
        this.stopPoint = stopPoint;
        this.stopPointLocationId = stopPoint.getLocationId();
        this.stopPointBussPointId = stopPoint.getBussPointId();
    }

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long stopPointLocationId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long stopPointBussPointId;

    public void setFieldByName(Map<String, String> data) {
        data.forEach((fieldName, value) -> {
        });
    }



}
