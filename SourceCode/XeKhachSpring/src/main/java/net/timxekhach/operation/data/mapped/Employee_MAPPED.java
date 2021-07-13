package net.timxekhach.operation.data.mapped;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeStringUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@MappedSuperclass
public abstract class Employee_MAPPED extends XeEntity {

    @EmbeddedId
    protected Pk pk;

    @Column(insertable = false, updatable = false)
    protected String employeeId;

    @Column(insertable = false, updatable = false)
    protected String companyId;

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

    public Employee_MAPPED(String companyId, String employeeId) {
        ErrorCode.VALIDATOR_NOT_BLANK.throwIfBlank(employeeId, "Employee ID");
        this.pk = new Pk(companyId, employeeId);
        this.setEmployeeId(employeeId);
        this.setCompanyId(companyId);
    }


    @Embeddable @Getter
    public static class Pk extends XePk {
        @Column
        protected String companyId;
        @Column
        protected String employeeId;

        public Pk() {
        }

        public Pk(String companyId, String employeeId) {
            this.employeeId = XeStringUtils.idTrim(employeeId);
            this.companyId = companyId;
        }

    }

}
