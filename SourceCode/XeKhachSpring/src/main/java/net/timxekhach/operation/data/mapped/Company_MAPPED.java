package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import java.util.ArrayList;
import javax.validation.constraints.*;
import java.util.List;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass @Getter @Setter
@IdClass(Company_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Company_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long companyId;

    protected Long getIncrementId() {
        return this.companyId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new Company_MAPPED.Pk(companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Company_MAPPED.Pk(0L);
    }

//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @OneToMany(
        mappedBy = "company",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    protected List<Employee> employees = new ArrayList<>();
    public Integer getTotalEmployees() {
        return CommonUpdateService.getEmployeeRepository().countEmployeeIdByCompanyId(this.companyId);
    }
//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//
    @Size(max = 255)
    protected String companyDesc;
    @Size(max = 255)
    protected String companyName;

    protected Boolean isLock = false;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("companyDesc")) {
                this.companyDesc = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("companyName")) {
                this.companyName = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("isLock")) {
                this.isLock = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = Long.valueOf(value);
            }
        }
    }
}
