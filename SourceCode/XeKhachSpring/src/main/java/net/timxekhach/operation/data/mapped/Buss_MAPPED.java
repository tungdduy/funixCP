package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.entity.BussType;


@MappedSuperclass @Getter @Setter
@IdClass(Buss_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class Buss_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long companyId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussTypeId;
        protected Long bussId;
        protected Long companyId;
    }
    protected Buss_MAPPED(){}
    protected Buss_MAPPED(Company company, BussType bussType) {
        this.company = company;
        this.bussType = bussType;
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

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "bussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false)
    })
    protected BussType bussType;

    public void setBussType(BussType bussType) {
        this.bussType = bussType;
        this.bussTypeId = bussType.getBussTypeId();
    }

    @Size(max = 255)
    protected String bussDesc;

    public void updateByOtherBuss(Buss buss) {
        if (buss.bussDesc != null) {
            this.bussDesc = buss.bussDesc;
        }

    }

}
