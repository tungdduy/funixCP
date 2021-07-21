package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.data.entity.TripUser;
import java.util.Date;
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.enumeration.TripStatus;


@MappedSuperclass @Getter @Setter
@IdClass(Trip_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class Trip_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripId;

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
        protected Long tripId;
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }
    protected Trip_MAPPED(){}
    protected Trip_MAPPED(Buss buss) {
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

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "tripUsersTripUserId",
        referencedColumnName = "tripUserId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripUsersBussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripUsersCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripUsersBussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripUsersUserId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "tripUsersTripId",
        referencedColumnName = "tripId",
        insertable = false,
        updatable = false)
    })
    protected TripUser tripUsers;

    public void setTripUsers(TripUser tripUsers) {
        this.tripUsers = tripUsers;
        this.tripUsersTripUserId = tripUsers.getTripUserId();
        this.tripUsersBussId = tripUsers.getBussId();
        this.tripUsersCompanyId = tripUsers.getCompanyId();
        this.tripUsersBussTypeId = tripUsers.getBussTypeId();
        this.tripUsersUserId = tripUsers.getUserId();
        this.tripUsersTripId = tripUsers.getTripId();
    }

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long startPointLocationId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long startPointBussPointId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long endPointBussPointId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long endPointLocationId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripUsersTripUserId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripUsersBussId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripUsersCompanyId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripUsersBussTypeId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripUsersUserId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long tripUsersTripId;


    protected Long price;


    @Enumerated(EnumType.STRING)
    protected TripStatus status;


    protected Date startTime;

}
