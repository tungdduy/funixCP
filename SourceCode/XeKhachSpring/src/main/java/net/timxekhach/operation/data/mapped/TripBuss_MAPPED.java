package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Date;
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.entity.TripPoint;
import net.timxekhach.operation.data.entity.Buss;
import java.util.ArrayList;


@MappedSuperclass @Getter @Setter
@IdClass(TripBuss_MAPPED.Pk.class)
public abstract class TripBuss_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripBussId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussId;
        protected Long tripBussId;
    }
    protected TripBuss_MAPPED(){}
    protected TripBuss_MAPPED(Buss buss) {
        this.buss = buss;
    }
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    protected Buss buss;

    public void setBuss(Buss buss) {
        this.buss = buss;
        this.bussId = buss.getBussId();
    }

    @OneToMany(
        mappedBy = "tripBuss",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<TripPoint> tripPoints = new ArrayList<>();

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "startPointLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    protected BussPoint startPoint;

    public void setStartPoint(BussPoint startPoint) {
        this.startPoint = startPoint;
        this.startPointLocationId = startPoint.getLocationId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "endPointLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    protected BussPoint endPoint;

    public void setEndPoint(BussPoint endPoint) {
        this.endPoint = endPoint;
        this.endPointLocationId = endPoint.getLocationId();
    }

    @Column
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long startPointLocationId;

    @Column
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long endPointLocationId;


    @Column
    protected Date launchTime;


    @Column
    protected Date effectiveDateFrom;


    @Column
    protected Long price;


    @Column
    protected Boolean sunday = false;


    @Column
    protected Boolean monday = false;


    @Column
    protected Boolean tuesday = false;


    @Column
    protected Boolean wednesday = false;


    @Column
    protected Boolean thursday = false;


    @Column
    protected Boolean friday = false;


    @Column
    protected Boolean saturday = false;

}
