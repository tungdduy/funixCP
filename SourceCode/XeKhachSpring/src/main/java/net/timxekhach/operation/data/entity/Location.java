package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.Location_MAPPED;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import javax.persistence.Transient;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.VietNamAccentUtil;
import org.apache.commons.lang3.StringUtils;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Location extends Location_MAPPED {


// ____________________ ::BODY_SEPARATOR:: ____________________ //
    //displayName
    @JsonInclude
    @Transient
    protected String displayName;

    public void setDisplayName(String displayName) {
        String parentName = this.parent == null ? null : this.parent.getDisplayName();
        parentName = parentName == null ? this.locationName : String.format("%s, %s", this.locationName, parentName);
        this.displayName = String.format("%s, %s", displayName, parentName);
    }

    public String getDisplayName() {
        if (StringUtils.isEmpty(displayName)){
            //if display name is empty
            String parentName = this.parent == null ? null : this.parent.getDisplayName();
            return parentName == null ? this.locationName : String.format("%s, %s", this.locationName, parentName);
        }
        return displayName;
    };

    private void updateSearchTextThenSave() {
        this.searchText = VietNamAccentUtil.removeAccent(this.getDisplayName()).toLowerCase().replace(", ", " ");
        this.save();
    }

    public static void updateAllLocationSearchText() {
        CommonUpdateService.getLocationRepository().findAll().forEach(Location::updateSearchTextThenSave);
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

