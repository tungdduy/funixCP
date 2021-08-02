package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import javax.persistence.*;;
import net.timxekhach.operation.data.entity.TripUser;
import java.util.Date;
import net.timxekhach.operation.response.ErrorCode;;
import net.timxekhach.operation.data.entity.TripUserSeat;
import java.util.ArrayList;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;;
import java.util.List;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.timxekhach.operation.data.entity.BussPoint;
import lombok.*;;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.enumeration.TripStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass @Getter @Setter
@IdClass(Trip_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Trip_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long tripId;

    protected Long getIncrementId() {
        return this.tripId;
    }

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

    public static Pk pk(Map<String, String> data) {
        try {
            Long tripIdLong = Long.parseLong(data.get("tripId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{tripIdLong, bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new Trip_MAPPED.Pk(tripIdLong, bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Trip_MAPPED.Pk(0L, 0L, 0L, 0L);
    }

    protected Trip_MAPPED(){}
    protected Trip_MAPPED(Buss buss) {
        this.setBuss(buss);
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
    @JsonIgnore
    protected Buss buss;

    public void setBuss(Buss buss) {
        this.buss = buss;
        this.companyId = buss.getCompanyId();
        this.bussTypeId = buss.getBussTypeId();
        this.bussId = buss.getBussId();
    }

    @OneToMany(
        mappedBy = "trip",
        cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<TripUserSeat> allTripUserSeats = new ArrayList<>();

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
    @JsonIgnore
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
    @JsonIgnore
    protected BussPoint endPoint;

    public void setEndPoint(BussPoint endPoint) {
        this.endPoint = endPoint;
        this.endPointBussPointId = endPoint.getBussPointId();
        this.endPointLocationId = endPoint.getLocationId();
    }

    @OneToMany(
        mappedBy = "trip",
        cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<TripUser> tripUsers = new ArrayList<>();


    
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long startPointLocationId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long startPointBussPointId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long endPointBussPointId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long endPointLocationId;


    protected Long price;


    @Enumerated(EnumType.STRING)
    protected TripStatus status;


    protected Date startTime;

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("price")) {
                this.price = Long.valueOf(value);
                continue;
            }
            if (fieldName.equals("status")) {
                this.status = TripStatus.valueOf(value);
                continue;
            }
            if (fieldName.equals("startTime")) {
                try {
                this.startTime = DateUtils.parseDate(value);
                } catch (Exception e) {
                ErrorCode.INVALID_TIME_FORMAT.throwNow(fieldName);
                }
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
