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
@IdClass(Location_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Location_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long locationId;

    protected Long getIncrementId() {
        return this.locationId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long locationId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long locationIdLong = Long.parseLong(data.get("locationId"));
            if(NumberUtils.min(new long[]{locationIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new Location_MAPPED.Pk(locationIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new Location_MAPPED.Pk(0L);
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "parentLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    @JsonIgnore
    protected Location parent;

    public void setParent(Location parent) {
        this.parent = parent;
        this.parentLocationId = parent.getLocationId();
    }


    
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long parentLocationId;

    @Size(max = 255)
    protected String locationName;

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("locationName")) {
                this.locationName = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("locationId")) {
                this.locationId = Long.valueOf(value);
            }
        }
    }



}
