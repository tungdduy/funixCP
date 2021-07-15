package net.timxekhach.operation.data.mapped;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass @Getter @Setter
public abstract class Company_MAPPED extends XeEntity {

    @EmbeddedId
    protected Pk pk;

    protected Company_MAPPED(){}

    public Company_MAPPED(Long companyId) {
        this.pk = new Pk(companyId);
    }

    @Embeddable @Getter
    public static class Pk extends XePk {
        @Column
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
        @SequenceGenerator(name="company_generator", sequenceName = "comp_seq")
        private Long companyId;
        public Pk(){}
        public Pk(Long companyId) {
            this.companyId = companyId;
        }
    }

    @OneToMany(
            mappedBy = "company",
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("pk.employeeId")
    private final List<Employee> allEmployees = new ArrayList<>();
}
