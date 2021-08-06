package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.SeatType_MAPPED;
import javax.persistence.Entity;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class SeatType extends SeatType_MAPPED {

    public SeatType() {}
    public SeatType(BussType bussType) {
        super(bussType);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //



// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

