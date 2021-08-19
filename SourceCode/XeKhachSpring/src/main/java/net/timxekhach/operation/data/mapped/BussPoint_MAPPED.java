package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.entity.Location;
import javax.validation.constraints.*;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(BussPoint_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussPoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long locationId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long companyId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussPointId;

    protected Long getIncrementId() {
        return this.bussPointId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long locationId;
        protected Long companyId;
        protected Long bussPointId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long locationIdLong = Long.parseLong(data.get("locationId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long bussPointIdLong = Long.parseLong(data.get("bussPointId"));
            if(NumberUtils.min(new long[]{locationIdLong, companyIdLong, bussPointIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussPoint_MAPPED.Pk(locationIdLong, companyIdLong, bussPointIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussPoint_MAPPED.Pk(0L, 0L, 0L);
    }

    protected BussPoint_MAPPED(){}
    protected BussPoint_MAPPED(Company company, Location location) {
        this.setCompany(company);
        this.setLocation(location);
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
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "locationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "locationId")
    protected Location location;

    public Location getLocation(){
        if (this.location == null) {
            this.location = CommonUpdateService.getLocationRepository().findByLocationId(this.locationId);
        }
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
        this.locationId = location.getLocationId();
    }
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//

    @Size(max = 255)
    protected String bussPointName;
    @Size(max = 255)
    protected String bussPointDesc;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussPointName")) {
                this.bussPointName = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussPointDesc")) {
                this.bussPointDesc = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("locationId")) {
                this.locationId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussPointId")) {
                this.bussPointId = Long.valueOf(value);
            }
        }
    }
}
