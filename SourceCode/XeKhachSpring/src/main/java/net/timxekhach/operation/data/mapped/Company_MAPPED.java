package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.Employee;


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
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "employeesEmployeeId",
        referencedColumnName = "employeeId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "employeesCompanyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    protected Employee employees;

    public void setEmployees(Employee employees) {
        this.employees = employees;
        this.employeesEmployeeId = employees.getEmployeeId();
        this.employeesCompanyId = employees.getCompanyId();
    }

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long employeesEmployeeId;

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long employeesCompanyId;

    @Size(max = 255)
    protected String companyDesc;

    @Size(max = 255)
    protected String companyName;


    protected Boolean isLock = false;

}
