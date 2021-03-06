package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


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
    public Integer getTotalEmployees() {
        return CommonUpdateService.getEmployeeRepository().countEmployeeIdByCompanyId(this.companyId);
    }
    public Integer getTotalBusses() {
        return CommonUpdateService.getBussRepository().countBussIdByCompanyId(this.companyId);
    }
    public Integer getTotalBussEmployees() {
        return CommonUpdateService.getBussEmployeeRepository().countBussEmployeeIdByCompanyId(this.companyId);
    }
    public Integer getTotalTrips() {
        return CommonUpdateService.getTripRepository().countTripIdByCompanyId(this.companyId);
    }
    public Integer getTotalSchedules() {
        return CommonUpdateService.getBussScheduleRepository().countBussScheduleIdByCompanyId(this.companyId);
    }
//=====================================================================//
//==================== END of MAP COUNT ENTITIES ======================//
//====================================================================//

    @Size(max = 255)
    protected String companyName;
    @Size(max = 255)
    protected String companyDesc;

    @Pattern(regexp = "(03|05|07|08|09)+\\d{8,10}")
    protected String hotLine;

    protected Boolean isLock = false;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("companyName")) {
                if(value == null) {this.setCompanyName(null); continue;}
                if(value.equals(this.getCompanyName())) continue;
                this.setCompanyName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("companyDesc")) {
                if(value == null) {this.setCompanyDesc(null); continue;}
                if(value.equals(this.getCompanyDesc())) continue;
                this.setCompanyDesc(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("hotLine")) {
                if(value == null) {this.setHotLine(null); continue;}
                if(value.equals(this.getHotLine())) continue;
                this.setHotLine(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("isLock")) {
                if(value == null) {this.setIsLock(null); continue;}
                if(value.equals(this.getIsLock())) continue;
                this.setIsLock(Boolean.valueOf(value));
                continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = value == null ? null : Long.valueOf(value);
            }
        }
    }
}
