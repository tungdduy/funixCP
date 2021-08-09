package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.entity.XeLocation;
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
import net.timxekhach.operation.data.entity.Location;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(BussPoint_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussPoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long xeLocationId;

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
        protected Long xeLocationId;
        protected Long companyId;
        protected Long bussPointId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long xeLocationIdLong = Long.parseLong(data.get("xeLocationId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long bussPointIdLong = Long.parseLong(data.get("bussPointId"));
            if(NumberUtils.min(new long[]{xeLocationIdLong, companyIdLong, bussPointIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussPoint_MAPPED.Pk(xeLocationIdLong, companyIdLong, bussPointIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussPoint_MAPPED.Pk(0L, 0L, 0L);
    }

    protected BussPoint_MAPPED(){}
    protected BussPoint_MAPPED(Company company, XeLocation xeLocation) {
        this.setCompany(company);
        this.setXeLocation(xeLocation);
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
        name = "xeLocationId",
        referencedColumnName = "xeLocationId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "xeLocationId")
    protected XeLocation xeLocation;

    public XeLocation getXeLocation(){
        if (this.xeLocation == null) {
            this.xeLocation = CommonUpdateService.getXeLocationRepository().findByXeLocationId(this.xeLocationId);
        }
        return this.xeLocation;
    }

    public void setXeLocation(XeLocation xeLocation) {
        this.xeLocation = xeLocation;
        this.xeLocationId = xeLocation.getXeLocationId();
    }
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//

    @Size(max = 255)
    protected String bussPointDesc;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussPointDesc")) {
                this.bussPointDesc = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("xeLocationId")) {
                this.xeLocationId = Long.valueOf(value);
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
