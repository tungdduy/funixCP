package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.timxekhach.operation.data.entity.Location;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Map;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(Location_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Location_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
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

//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "parentLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "locationId")
    protected Location parent;

    public void setParent(Location parent) {
        this.parent = parent;
        if(parent == null) {
            this.parentLocationId = null;
            return;
        }
        this.parentLocationId = parent.getLocationId();
    }

//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//

    @Setter(AccessLevel.PRIVATE)
    protected Long parentLocationId;
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//
    @Size(max = 255)
    protected String locationName;
    @Size(max = 255)
    protected String searchText;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("locationName")) {
                if(value == null) {this.setLocationName(null); continue;}
                if(value.equals(this.getLocationName())) continue;
                this.setLocationName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("searchText")) {
                if(value == null) {this.setSearchText(null); continue;}
                if(value.equals(this.getSearchText())) continue;
                this.setSearchText(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("parent")) {
                if(value == null) {this.setParent(null); continue;}
                this.setParent(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getLocationRepository().findByLocationId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("locationId")) {
                this.locationId = value == null ? null : Long.valueOf(value);
            }
        }
    }
}
