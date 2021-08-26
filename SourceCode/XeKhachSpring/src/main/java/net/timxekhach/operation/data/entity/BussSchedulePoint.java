package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.BussSchedulePoint_MAPPED;
import net.timxekhach.utility.VietNamAccentUtil;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class BussSchedulePoint extends BussSchedulePoint_MAPPED {

    public BussSchedulePoint() {}
    public BussSchedulePoint(BussSchedule bussSchedule, PathPoint pathPoint) {
        super(bussSchedule, pathPoint);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    public static void init(BussSchedule bussSchedule, PathPoint pathPoint) {
        BussSchedulePoint point = new BussSchedulePoint(bussSchedule, pathPoint);
        point.price = bussSchedule.getScheduleUnitPrice();
        point.setIsDeductPrice(false);
        point.updateSearchText();
        point.save();
    }

    private void updateSearchText() {
        if(this.pathPoint == null) {
            this.searchText = "";
        } else {
            this.searchText = VietNamAccentUtil.removeAccent(String.format("%s %s %s",
                    this.pathPoint.getPointName(),
                    this.pathPoint.getPointDesc(),
                    this.pathPoint.getLocation().getDisplayName()))
                    .toLowerCase();
        }
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

