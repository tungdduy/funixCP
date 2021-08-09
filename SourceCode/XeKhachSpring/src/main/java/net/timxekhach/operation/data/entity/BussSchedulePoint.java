package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.BussSchedulePoint_MAPPED;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class BussSchedulePoint extends BussSchedulePoint_MAPPED {

    public BussSchedulePoint() {}
    public BussSchedulePoint(BussPoint bussPoint, BussSchedule bussSchedule) {
        super(bussPoint, bussSchedule);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

