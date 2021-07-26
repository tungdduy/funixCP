package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import javax.persistence.*;;
import java.util.Date;
import net.timxekhach.operation.response.ErrorCode;;
import java.util.ArrayList;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;;
import java.util.List;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.entity.TripPoint;
import lombok.*;;
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

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long tripBussIdLong = Long.parseLong(data.get("tripBussId"));
            if(NumberUtils.min(new long[]{bussTypeIdLong, bussIdLong, companyIdLong, tripBussIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            };
            return new TripBuss_MAPPED.Pk(bussTypeIdLong, bussIdLong, companyIdLong, tripBussIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new TripBuss_MAPPED.Pk(0L, 0L, 0L, 0L);
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
    @JsonIgnore
    protected Buss buss;

    public void setBuss(Buss buss) {
        this.buss = buss;
        this.companyId = buss.getCompanyId();
        this.bussTypeId = buss.getBussTypeId();
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

    public void setFieldByName(Map<String, String> data) {
        data.forEach((fieldName, value) -> {
            if (fieldName.equals("launchTime")) {
                try {
                    this.launchTime = DateUtils.parseDate(value);
                } catch (Exception e) {
                    ErrorCode.INVALID_TIME_FORMAT.throwNow(fieldName);
                }
                return;
            }
            if (fieldName.equals("effectiveDateFrom")) {
                try {
                    this.effectiveDateFrom = DateUtils.parseDate(value);
                } catch (Exception e) {
                    ErrorCode.INVALID_TIME_FORMAT.throwNow(fieldName);
                }
                return;
            }
            if (fieldName.equals("price")) {
                this.price = Long.valueOf(value);
                return;
            }
            if (fieldName.equals("sunday")) {
                this.sunday = Boolean.valueOf(value);
                return;
            }
            if (fieldName.equals("monday")) {
                this.monday = Boolean.valueOf(value);
                return;
            }
            if (fieldName.equals("tuesday")) {
                this.tuesday = Boolean.valueOf(value);
                return;
            }
            if (fieldName.equals("wednesday")) {
                this.wednesday = Boolean.valueOf(value);
                return;
            }
            if (fieldName.equals("thursday")) {
                this.thursday = Boolean.valueOf(value);
                return;
            }
            if (fieldName.equals("friday")) {
                this.friday = Boolean.valueOf(value);
                return;
            }
            if (fieldName.equals("saturday")) {
                this.saturday = Boolean.valueOf(value);
            }
        });
    }



}
