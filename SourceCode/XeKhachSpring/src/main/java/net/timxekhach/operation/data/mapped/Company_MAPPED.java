package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import org.apache.commons.lang3.math.NumberUtils;;
import javax.validation.constraints.*;
import java.util.List;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import javax.persistence.*;;
import lombok.*;;
import net.timxekhach.operation.response.ErrorCode;;
import java.util.ArrayList;


@MappedSuperclass @Getter @Setter
@IdClass(Company_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class Company_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long companyId;

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
            };
            return new Company_MAPPED.Pk(companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Company_MAPPED.Pk(0L);
    }

    @OneToMany(
        mappedBy = "company",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<Employee> employees = new ArrayList<>();

    @Size(max = 255)
    protected String companyDesc;

    @Size(max = 255)
    protected String companyName;


    protected Boolean isLock = false;

    public void setFieldByName(Map<String, String> data) {
        data.forEach((fieldName, value) -> {
            if (fieldName.equals("companyDesc")) {
                this.companyDesc = String.valueOf(value);
                return;
            }
            if (fieldName.equals("companyName")) {
                this.companyName = String.valueOf(value);
                return;
            }
            if (fieldName.equals("isLock")) {
                this.isLock = Boolean.valueOf(value);
            }
        });
    }



}
