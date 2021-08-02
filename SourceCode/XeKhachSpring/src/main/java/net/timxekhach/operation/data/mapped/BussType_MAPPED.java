package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import org.apache.commons.lang3.math.NumberUtils;;
import javax.validation.constraints.*;
import java.util.List;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import javax.persistence.*;;
import net.timxekhach.operation.data.entity.SeatType;
import lombok.*;;
import net.timxekhach.operation.response.ErrorCode;;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

@MappedSuperclass @Getter @Setter
@IdClass(BussType_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussType_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussTypeId;

    protected Long getIncrementId() {
        return this.bussTypeId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussTypeId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            if(NumberUtils.min(new long[]{bussTypeIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussType_MAPPED.Pk(bussTypeIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussType_MAPPED.Pk(0L);
    }

    @OneToMany(
        mappedBy = "bussType",
        cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<SeatType> allSeatTypes = new ArrayList<>();


    
    @Size(max = 255)
    protected String bussTypeName;

    @Size(max = 255)
    protected String bussTypeDesc;

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussTypeName")) {
                this.bussTypeName = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussTypeDesc")) {
                this.bussTypeDesc = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = Long.valueOf(value);
            }
        }
    }



}
