package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.Location_MAPPED;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.VietNamAccentUtil;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Location extends Location_MAPPED {


// ____________________ ::BODY_SEPARATOR:: ____________________ //
    public String getDisplayName() {
        String parentName = this.parent == null ? null : this.parent.getDisplayName();
        return parentName == null ? this.locationName : String.format("%s, %s", this.locationName, parentName);
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

