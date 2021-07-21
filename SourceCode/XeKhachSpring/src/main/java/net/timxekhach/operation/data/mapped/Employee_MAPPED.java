package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.entity.User;


@MappedSuperclass @Getter @Setter
@IdClass(Employee_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class Employee_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long employeeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long companyId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long employeeId;
        protected Long companyId;
    }
    protected Employee_MAPPED(){}
    protected Employee_MAPPED(Company company) {
        this.company = company;
    }
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    protected Company company;

    public void setCompany(Company company) {
        this.company = company;
        this.companyId = company.getCompanyId();
    }

    @OneToOne
    @JoinColumns({
        @JoinColumn(
        name = "userUserId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false)
    })
    protected User user;

    public void setUser(User user) {
        this.user = user;
        this.userUserId = user.getUserId();
    }

    @Column(unique = true)
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long userUserId;


    protected Boolean isLock = false;

}
