package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.entity.User;
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
@IdClass(Employee_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Employee_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long employeeId;

    protected Long getIncrementId() {
        return this.employeeId;
    }
    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long companyId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long userId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long employeeId;
        protected Long companyId;
        protected Long userId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long employeeIdLong = Long.parseLong(data.get("employeeId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long userIdLong = Long.parseLong(data.get("userId"));
            if(NumberUtils.min(new long[]{employeeIdLong, companyIdLong, userIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new Employee_MAPPED.Pk(employeeIdLong, companyIdLong, userIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Employee_MAPPED.Pk(0L, 0L, 0L);
    }

    protected Employee_MAPPED(){}
    protected Employee_MAPPED(Company company, User user) {
        this.setCompany(company);
        this.setUser(user);
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
        if(company == null) {
            this.companyId = null;
            return;
        }
        this.companyId = company.getCompanyId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "userId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
    protected User user;

    public User getUser(){
        if (this.user == null) {
            this.user = CommonUpdateService.getUserRepository().findByUserId(this.userId);
        }
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user == null) {
            this.userId = null;
            return;
        }
        this.userId = user.getUserId();
    }

//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
    public Integer getCountBusses() {
        return CommonUpdateService.getBussEmployeeRepository().countBussEmployeeIdByEmployeeId(this.employeeId);
    }
//=====================================================================//
//==================== END of MAP COUNT ENTITIES ======================//
//====================================================================//


    protected Boolean isLock = false;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("isLock")) {
                this.setIsLock(Boolean.valueOf(value));
                continue;
            }
            if (fieldName.equals("company")) {
                this.setCompany(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getCompanyRepository().findByCompanyId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("user")) {
                this.setUser(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getUserRepository().findByUserId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("employeeId")) {
                this.employeeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("userId")) {
                this.userId = Long.valueOf(value);
            }
        }
    }
}
