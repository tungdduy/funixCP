package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import java.util.Date;
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.entity.TripPoint;
import net.timxekhach.operation.data.entity.Buss;


@MappedSuperclass @Getter @Setter
@IdClass(TripBuss_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class TripBuss_MAPPED extends XeEntity {

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripBussId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
        protected Long tripBussId;
    }
    protected TripBuss_MAPPED(){}
    protected TripBuss_MAPPED(Buss buss) {
        this.buss = buss;
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
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    protected Buss buss;

    public void setBuss(Buss buss) {
        this.buss = buss;
        this.companyId = buss.getCompanyId();
        this.bussTypeId = buss.getBussTypeId();
        this.bussId = buss.getBussId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "tripPointsBussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripPointsCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripPointsTripPointId",
        referencedColumnName = "tripPointId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripPointsTripBussId",
        referencedColumnName = "tripBussId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripPointsBussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    protected TripPoint tripPoints;

    public void setTripPoints(TripPoint tripPoints) {
        this.tripPoints = tripPoints;
        this.tripPointsBussTypeId = tripPoints.getBussTypeId();
        this.tripPointsCompanyId = tripPoints.getCompanyId();
        this.tripPointsTripPointId = tripPoints.getTripPointId();
        this.tripPointsTripBussId = tripPoints.getTripBussId();
        this.tripPointsBussId = tripPoints.getBussId();
    }

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
        updatable = false)
    })
    protected BussPoint startPoint;

    public void setStartPoint(BussPoint startPoint) {
        this.startPoint = startPoint;
        this.startPointLocationId = startPoint.getLocationId();
        this.startPointBussPointId = startPoint.getBussPointId();
    }

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
        updatable = false)
    })
    protected BussPoint endPoint;

    public void setEndPoint(BussPoint endPoint) {
        this.endPoint = endPoint;
        this.endPointBussPointId = endPoint.getBussPointId();
        this.endPointLocationId = endPoint.getLocationId();
    }

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripPointsBussTypeId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripPointsCompanyId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripPointsTripPointId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripPointsTripBussId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripPointsBussId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long startPointLocationId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long startPointBussPointId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long endPointBussPointId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long endPointLocationId;


    protected Date launchTime;


    protected Date effectiveDateFrom;


    protected Long price;


    protected Boolean sunday = false;


    protected Boolean monday = false;


    protected Boolean tuesday = false;


    protected Boolean wednesday = false;


    protected Boolean thursday = false;


    protected Boolean friday = false;


    protected Boolean saturday = false;

}
