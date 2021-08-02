package net.timxekhach.operation.data.mapped;

import net.timxekhach.operation.data.mapped.abstracts.XeEntity;;
import org.apache.commons.lang3.math.NumberUtils;;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.mapped.abstracts.XePk;;
import java.util.Map;;
import javax.persistence.*;;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.timxekhach.operation.data.entity.Location;
import lombok.*;;
import net.timxekhach.operation.response.ErrorCode;;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass @Getter @Setter
@IdClass(BussPoint_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussPoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long locationId;


    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussPointId;

    protected Long getIncrementId() {
        return this.bussPointId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long locationId;
        protected Long bussPointId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long locationIdLong = Long.parseLong(data.get("locationId"));
            Long bussPointIdLong = Long.parseLong(data.get("bussPointId"));
            if(NumberUtils.min(new long[]{locationIdLong, bussPointIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussPoint_MAPPED.Pk(locationIdLong, bussPointIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussPoint_MAPPED.Pk(0L, 0L);
    }

    protected BussPoint_MAPPED(){}
    protected BussPoint_MAPPED(Location location) {
        this.setLocation(location);
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "locationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    @JsonIgnore
    protected Location location;

    public void setLocation(Location location) {
        this.location = location;
        this.locationId = location.getLocationId();
    }


    
    @Size(max = 255)
    protected String bussPointDesc;

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussPointDesc")) {
                this.bussPointDesc = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("locationId")) {
                this.locationId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussPointId")) {
                this.bussPointId = Long.valueOf(value);
            }
        }
    }



}
