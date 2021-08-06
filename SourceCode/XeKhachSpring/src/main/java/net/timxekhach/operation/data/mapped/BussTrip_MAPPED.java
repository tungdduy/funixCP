package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.Buss;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import net.timxekhach.operation.data.entity.TripPoint;
import org.apache.commons.lang3.math.NumberUtils;
import net.timxekhach.operation.data.entity.Company;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import org.apache.commons.lang3.time.DateUtils;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.timxekhach.operation.data.entity.BussPoint;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(BussTrip_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussTrip_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussTripId;

    protected Long getIncrementId() {
        return this.bussTripId;
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
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussTripIdLong = Long.parseLong(data.get("bussTripId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{bussTripIdLong, bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussTrip_MAPPED.Pk(bussTripIdLong, bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussTrip_MAPPED.Pk(0L, 0L, 0L, 0L);
    }

    protected BussTrip_MAPPED(){}
    protected BussTrip_MAPPED(Buss buss, Company company) {
        this.setBuss(buss);
        this.setCompany(company);
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
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bussId")
    protected Buss buss;

    public Buss getBuss(){
        if (this.buss == null) {
            this.buss = CommonUpdateService.getBussRepository().findByBussId(this.bussId);
        }
        return this.buss;
    }

    public void setBuss(Buss buss) {
        this.buss = buss;
        this.companyId = buss.getCompanyId();
        this.bussTypeId = buss.getBussTypeId();
        this.bussId = buss.getBussId();
    }
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "companyId")
    protected Company company;

    public Company getCompany(){
        if (this.company == null) {
            this.company = CommonUpdateService.getCompanyRepository().findByCompanyId(this.companyId);
        }
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
        this.companyId = company.getCompanyId();
    }
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
    @OneToMany(
        mappedBy = "bussTrip",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
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
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "startPointId")
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
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "endPointId")
    protected BussPoint endPoint;

    public void setEndPoint(BussPoint endPoint) {
        this.endPoint = endPoint;
        this.endPointBussPointId = endPoint.getBussPointId();
        this.endPointLocationId = endPoint.getLocationId();
    }

//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//

    @Setter(AccessLevel.PRIVATE)
    protected Long startPointLocationId;
    @Setter(AccessLevel.PRIVATE)
    protected Long startPointBussPointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long endPointBussPointId;
    @Setter(AccessLevel.PRIVATE)
    protected Long endPointLocationId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//

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
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("launchTime")) {
                try {
                this.launchTime = DateUtils.parseDate(value);
                } catch (Exception e) {
                ErrorCode.INVALID_TIME_FORMAT.throwNow(fieldName);
                }
                continue;
            }
            if (fieldName.equals("effectiveDateFrom")) {
                try {
                this.effectiveDateFrom = DateUtils.parseDate(value);
                } catch (Exception e) {
                ErrorCode.INVALID_TIME_FORMAT.throwNow(fieldName);
                }
                continue;
            }
            if (fieldName.equals("price")) {
                this.price = Long.valueOf(value);
                continue;
            }
            if (fieldName.equals("sunday")) {
                this.sunday = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("monday")) {
                this.monday = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("tuesday")) {
                this.tuesday = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("wednesday")) {
                this.wednesday = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("thursday")) {
                this.thursday = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("friday")) {
                this.friday = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("saturday")) {
                this.saturday = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussTripId")) {
                this.bussTripId = Long.valueOf(value);
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
