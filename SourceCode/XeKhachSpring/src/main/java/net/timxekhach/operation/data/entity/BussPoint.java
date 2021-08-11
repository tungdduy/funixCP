package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.BussPoint_MAPPED;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class BussPoint extends BussPoint_MAPPED {

    public BussPoint() {}
    public BussPoint(Company company, XeLocation xeLocation) {
        super(company, xeLocation);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

