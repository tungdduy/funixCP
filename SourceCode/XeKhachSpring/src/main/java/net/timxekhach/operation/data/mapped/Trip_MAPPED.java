package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import java.util.List;
import net.timxekhach.operation.data.entity.TripUser;
import java.util.Date;
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.enumeration.TripStatus;
import java.util.ArrayList;


@MappedSuperclass @Getter @Setter
@IdClass(Trip_MAPPED.Pk.class)
public abstract class Trip_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long tripId;
        protected Long bussId;
    }
    protected Trip_MAPPED(){}
    protected Trip_MAPPED(Buss buss) {
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

    @OneToMany(
        mappedBy = "trip",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<TripUser> tripUsers = new ArrayList<>();

    @Column
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long startPointLocationId;

    @Column
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long endPointLocationId;


    @Column
    protected Long price;


    @Column
    protected TripStatus status;


    @Column
    protected Date startTime;

}
