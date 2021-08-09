package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.XeLocation;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(XeLocation_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class XeLocation_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long xeLocationId;

    protected Long getIncrementId() {
        return this.xeLocationId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long xeLocationId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long xeLocationIdLong = Long.parseLong(data.get("xeLocationId"));
            if(NumberUtils.min(new long[]{xeLocationIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new XeLocation_MAPPED.Pk(xeLocationIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new XeLocation_MAPPED.Pk(0L);
    }

//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "parentXeLocationId",
        referencedColumnName = "xeLocationId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "parentId")
    protected XeLocation parent;

    public void setParent(XeLocation parent) {
        this.parent = parent;
        this.parentXeLocationId = parent.getXeLocationId();
    }

//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//

    @Setter(AccessLevel.PRIVATE)
    protected Long parentXeLocationId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//
    @Size(max = 255)
    protected String locationName;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("locationName")) {
                this.locationName = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("xeLocationId")) {
                this.xeLocationId = Long.valueOf(value);
            }
        }
    }
}
