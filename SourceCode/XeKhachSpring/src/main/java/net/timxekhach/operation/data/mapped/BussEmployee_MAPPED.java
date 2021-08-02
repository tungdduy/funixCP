package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import org.apache.commons.lang3.math.NumberUtils;;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import javax.persistence.*;;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;;
import net.timxekhach.operation.response.ErrorCode;;
import net.timxekhach.operation.data.entity.Buss;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass @Getter @Setter
@IdClass(BussEmployee_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussEmployee_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussEmployeeId;

    protected Long getIncrementId() {
        return this.bussEmployeeId;
    }

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussTypeId;


    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long employeeId;


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
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long userId;


    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussEmployeeId;
        protected Long bussTypeId;
        protected Long employeeId;
        protected Long bussId;
        protected Long companyId;
        protected Long userId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussEmployeeIdLong = Long.parseLong(data.get("bussEmployeeId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long employeeIdLong = Long.parseLong(data.get("employeeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            Long userIdLong = Long.parseLong(data.get("userId"));
            if(NumberUtils.min(new long[]{bussEmployeeIdLong, bussTypeIdLong, employeeIdLong, bussIdLong, companyIdLong, userIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussEmployee_MAPPED.Pk(bussEmployeeIdLong, bussTypeIdLong, employeeIdLong, bussIdLong, companyIdLong, userIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussEmployee_MAPPED.Pk(0L, 0L, 0L, 0L, 0L, 0L);
    }

    protected BussEmployee_MAPPED(){}
    protected BussEmployee_MAPPED(Buss buss, Employee employee) {
        this.setBuss(buss);
        this.setEmployee(employee);
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

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "employeeId",
        referencedColumnName = "employeeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "userId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false)
    })
    @JsonIgnore
    protected Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.companyId = employee.getCompanyId();
        this.employeeId = employee.getEmployeeId();
        this.userId = employee.getUserId();
    }


    

    protected Boolean isLock = false;

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("isLock")) {
                this.isLock = Boolean.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussEmployeeId")) {
                this.bussEmployeeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("employeeId")) {
                this.employeeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussId")) {
                this.bussId = Long.valueOf(value);
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
