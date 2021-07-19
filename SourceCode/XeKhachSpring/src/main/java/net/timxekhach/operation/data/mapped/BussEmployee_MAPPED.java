package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.entity.Buss;


@MappedSuperclass @Getter @Setter
@IdClass(BussEmployee_MAPPED.Pk.class)
public abstract class BussEmployee_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussEmployeeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long employeeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussEmployeeId;
        protected Long employeeId;
        protected Long bussId;
    }
    protected BussEmployee_MAPPED(){}
    protected BussEmployee_MAPPED(Buss buss, Employee employee) {
        this.buss = buss;
        this.employee = employee;
    }
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    protected Buss buss;

    public void setBuss(Buss buss) {
        this.buss = buss;
        this.bussId = buss.getBussId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "employeeId",
        referencedColumnName = "employeeId",
        insertable = false,
        updatable = false)
    })
    protected Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.employeeId = employee.getEmployeeId();
    }


    @Column
    protected Boolean isLock = false;

}
