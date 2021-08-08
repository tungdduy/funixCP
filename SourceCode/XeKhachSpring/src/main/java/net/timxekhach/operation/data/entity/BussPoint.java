package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.BussPoint_MAPPED;
import javax.persistence.Entity;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class BussPoint extends BussPoint_MAPPED {

    public BussPoint() {}
    public BussPoint(Location location) {
        super(location);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //



// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

