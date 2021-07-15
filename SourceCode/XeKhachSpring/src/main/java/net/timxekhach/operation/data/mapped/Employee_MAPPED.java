package net.timxekhach.operation.data.mapped;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@MappedSuperclass
public abstract class Employee_MAPPED extends XeEntity {
    @EmbeddedId
    protected Pk pk;

    @ManyToOne
    @JoinColumns({@JoinColumn(
            name = "companyId",
            referencedColumnName = "companyId",
            insertable = false,
            updatable = false
    )})
    protected Company company;

    @Column
    @Size(max = 255)
    protected String empDesc;

    public Employee_MAPPED() {
    }
    public Employee_MAPPED(Long companyId) {
        this.pk = new Pk(companyId);
    }
    @Embeddable @Getter
    public static class Pk extends XePk {
        @Column
        protected Long companyId;
        @Column
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
        @SequenceGenerator(name="employee_generator", sequenceName = "emp_seq")
        protected Long employeeId;

        public Pk() {
        }

        public Pk(Long companyId) {
            this.companyId = companyId;
        }

    }

}
