package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import org.apache.commons.lang3.math.NumberUtils;;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import javax.persistence.*;;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;;
import net.timxekhach.operation.response.ErrorCode;;
import net.timxekhach.operation.data.entity.BussType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass @Getter @Setter
@IdClass(Buss_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    protected Long getIncrementId() {
        return this.bussId;
    }

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

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long bussIdLong = Long.parseLong(data.get("bussId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{bussTypeIdLong, bussIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new Buss_MAPPED.Pk(bussTypeIdLong, bussIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Buss_MAPPED.Pk(0L, 0L, 0L);
    }

    protected Buss_MAPPED(){}
    protected Buss_MAPPED(Company company, BussType bussType) {
        this.setCompany(company);
        this.setBussType(bussType);
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false)
    })
    @JsonIgnore
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
    @JsonIgnore
    protected BussType bussType;

    public void setBussType(BussType bussType) {
        this.bussType = bussType;
        this.bussTypeId = bussType.getBussTypeId();
    }


    
    @Size(max = 255)
    protected String bussDesc;

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussDesc")) {
                this.bussDesc = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussId")) {
                this.bussId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = Long.valueOf(value);
            }
        }
    }



}
