package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import org.apache.commons.lang3.math.NumberUtils;;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import javax.persistence.*;;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;;
import net.timxekhach.operation.response.ErrorCode;;
import net.timxekhach.operation.data.entity.User;


@MappedSuperclass @Getter @Setter
@IdClass(Employee_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class Employee_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long employeeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long companyId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long employeeId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long employeeIdLong = Long.parseLong(data.get("employeeId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{employeeIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            };
            return new Employee_MAPPED.Pk(employeeIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Employee_MAPPED.Pk(0L, 0L);
    }

    protected Employee_MAPPED(){}
    protected Employee_MAPPED(Company company) {
        this.company = company;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    @JsonIgnore
    protected Company company;

    public void setCompany(Company company) {
        this.company = company;
        this.companyId = company.getCompanyId();
    }

    @OneToOne
    @JoinColumns({
        @JoinColumn(
        name = "userUserId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false)
    })
    @JsonIgnore
    protected User user;

    public void setUser(User user) {
        this.user = user;
        this.userUserId = user.getUserId();
    }

    @Column(unique = true)
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long userUserId;


    protected Boolean isLock = false;

    public void setFieldByName(Map<String, String> data) {
        data.forEach((fieldName, value) -> {
            if (fieldName.equals("isLock")) {
                this.isLock = Boolean.valueOf(value);
            }
        });
    }



}
