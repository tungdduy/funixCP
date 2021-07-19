package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import java.util.List;
import net.timxekhach.operation.data.entity.Employee;
import java.util.ArrayList;


@MappedSuperclass @Getter @Setter
@IdClass(Company_MAPPED.Pk.class)
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
    @OneToMany(
        mappedBy = "company",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<Employee> employees = new ArrayList<>();

    @Size(max = 255)
    @Column
    protected String companyDesc;

    @Size(max = 255)
    @Column
    protected String companyName;


    @Column
    protected Boolean isLock = false;

}
