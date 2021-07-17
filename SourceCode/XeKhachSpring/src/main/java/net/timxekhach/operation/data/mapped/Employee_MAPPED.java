package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.data.entity.Company;
import java.util.List;
import net.timxekhach.operation.data.entity.EmployeeSeat;
import net.timxekhach.operation.data.entity.City;
import net.timxekhach.operation.data.entity.User;
import java.util.ArrayList;


@MappedSuperclass @Getter @Setter
@IdClass(Employee_MAPPED.Pk.class)
public abstract class Employee_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long employeeId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
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
        name = "userId",
        referencedColumnName = "userId",
        insertable = false,
        updatable = false)
    })
    protected User user;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getUserId();
    }

    @OneToMany(
        mappedBy = "employee",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<EmployeeSeat> seats = new ArrayList<>();

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "cityId",
        referencedColumnName = "cityId",
        insertable = false,
        updatable = false)
    })
    protected City city;

    public void setCity(City city) {
        this.city = city;
        this.cityId = city.getCityId();
    }

    @Column
    @Setter(AccessLevel.PRIVATE)
    protected Long userId;

    @Column
    @Setter(AccessLevel.PRIVATE)
    protected Long cityId;

}
