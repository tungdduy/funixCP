package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.data.entity.Seat;
import net.timxekhach.operation.data.entity.Employee;


@MappedSuperclass @Getter @Setter
@IdClass(EmployeeSeat_MAPPED.Pk.class)
public abstract class EmployeeSeat_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long seatId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long employeeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long companyId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long seatId;
        protected Long employeeId;
        protected Long bussId;
        protected Long companyId;
    }
    protected EmployeeSeat_MAPPED(){}
    protected EmployeeSeat_MAPPED(Employee employee, Seat seat) {
        this.employee = employee;
        this.seat = seat;
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
        updatable = false)
    })
    protected Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.companyId = employee.getCompanyId();
        this.employeeId = employee.getEmployeeId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "seatId",
        referencedColumnName = "seatId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "bussId",
        referencedColumnName = "bussId",
        insertable = false,
        updatable = false)
    })
    protected Seat seat;

    public void setSeat(Seat seat) {
        this.seat = seat;
        this.companyId = seat.getCompanyId();
        this.seatId = seat.getSeatId();
        this.bussId = seat.getBussId();
    }

}
