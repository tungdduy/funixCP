package net.timxekhach.operation.data.mapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;

import javax.persistence.*;
import javax.validation.constraints.Size;

@IdClass(Employee_MAPPED.Pk.class)
@Getter @Setter
@MappedSuperclass
public abstract class Employee_MAPPED extends XeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    Long employeeId;

    @Id
    @Column(nullable = false, updatable = false)
    Long companyId;

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

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pk extends XePk {
        protected Long companyId;
        protected Long employeeId;
    }

}
