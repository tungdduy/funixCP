package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.data.entity.TripBuss;
import net.timxekhach.operation.data.entity.BussPoint;


@MappedSuperclass @Getter @Setter
@IdClass(TripPoint_MAPPED.Pk.class)
public abstract class TripPoint_MAPPED extends XeEntity {

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

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripBussId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long tripPointId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
        protected Long tripBussId;
    }
    protected TripPoint_MAPPED(){}
    protected TripPoint_MAPPED(TripBuss tripBuss) {
        this.tripBuss = tripBuss;
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
        name = "tripBussId",
        referencedColumnName = "tripBussId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    protected TripBuss tripBuss;

    public void setTripBuss(TripBuss tripBuss) {
        this.tripBuss = tripBuss;
        this.companyId = tripBuss.getCompanyId();
        this.bussTypeId = tripBuss.getBussTypeId();
        this.tripBussId = tripBuss.getTripBussId();
        this.bussId = tripBuss.getBussId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "stopPointLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    protected BussPoint stopPoint;

    public void setStopPoint(BussPoint stopPoint) {
        this.stopPoint = stopPoint;
        this.stopPointLocationId = stopPoint.getLocationId();
    }

    @Column
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long stopPointLocationId;

}
