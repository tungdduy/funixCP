package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.timxekhach.operation.data.entity.BussEmployee;
import net.timxekhach.operation.data.entity.BussType;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        if(bussType == null) {
            this.bussTypeId = null;
            return;
        }
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
        if(company == null) {
            this.companyId = null;
            return;
        }
        this.companyId = company.getCompanyId();
    }

//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
    @OneToMany(
        mappedBy = "buss",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<BussEmployee> bussEmployees = new ArrayList<>();
//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//
    public Integer getTotalBussEmployees() {
        return CommonUpdateService.getBussEmployeeRepository().countBussEmployeeIdByBussId(this.bussId);
    }
    public Integer getTotalSchedules() {
        return CommonUpdateService.getBussScheduleRepository().countBussScheduleIdByBussId(this.bussId);
    }
//=====================================================================//
//==================== END of MAP COUNT ENTITIES ======================//
//====================================================================//

    @Size(max = 30)
    protected String bussLicense;
    @Size(max = 255)
    protected String bussDesc;
    @Size(max = 255)
    protected String lockedSeatsString;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussLicense")) {
                if(value == null) {this.setBussLicense(null); continue;}
                if(value.equals(this.getBussLicense())) continue;
                this.setBussLicense(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("bussDesc")) {
                if(value == null) {this.setBussDesc(null); continue;}
                if(value.equals(this.getBussDesc())) continue;
                this.setBussDesc(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("lockedSeatsString")) {
                if(value == null) {this.setLockedSeatsString(null); continue;}
                if(value.equals(this.getLockedSeatsString())) continue;
                this.setLockedSeatsString(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("bussType")) {
                if(value == null) {this.setBussType(null); continue;}
                this.setBussType(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getBussTypeRepository().findByBussTypeId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("company")) {
                if(value == null) {this.setCompany(null); continue;}
                this.setCompany(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getCompanyRepository().findByCompanyId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = value == null ? null : Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussId")) {
                this.bussId = value == null ? null : Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = value == null ? null : Long.valueOf(value);
            }
        }
    }
}
