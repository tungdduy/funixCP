package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.BussType;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.apache.commons.lang3.math.NumberUtils;
import net.timxekhach.operation.data.entity.Company;
import javax.persistence.*;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(Buss_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Buss_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussId;

    protected Long getIncrementId() {
        return this.bussId;
    }
    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long companyId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new Buss_MAPPED.Pk(bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Buss_MAPPED.Pk(0L, 0L, 0L);
    }

    protected Buss_MAPPED(){}
    protected Buss_MAPPED(BussType bussType, Company company) {
        this.setBussType(bussType);
        this.setCompany(company);
    }
//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "bussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bussTypeId")
    protected BussType bussType;

    public BussType getBussType(){
        if (this.bussType == null) {
            this.bussType = CommonUpdateService.getBussTypeRepository().findByBussTypeId(this.bussTypeId);
        }
        return this.bussType;
    }

    public void setBussType(BussType bussType) {
        this.bussType = bussType;
        this.bussTypeId = bussType.getBussTypeId();
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

    @Size(max = 255)
    protected String bussDesc;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussDesc")) {
                this.bussDesc = String.valueOf(value);
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
