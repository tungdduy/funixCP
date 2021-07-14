package net.timxekhach.operation.data.mapped;

import lombok.Getter;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass @Getter
public abstract class Company_MAPPED extends XeEntity {

    @EmbeddedId
    protected Pk pk;

    @Column(insertable = false, updatable = false)
    protected String companyId;

    protected Company_MAPPED(){}

    public Company_MAPPED(String companyId) {
        ErrorCode.VALIDATOR_NOT_BLANK.throwIfBlank(companyId, "Company ID");
        this.pk = new Pk(companyId);
        this.companyId = companyId;
    }

    @Embeddable @Getter
    public static class Pk extends XePk {
        @Column
        private String companyId;
        public Pk(){}
        public Pk(String companyId) {
            this.companyId = companyId;
        }
    }

    @OneToMany(
            mappedBy = "company",
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("employeeId")
    private final List<Employee> allEmployees = new ArrayList<>();
}
