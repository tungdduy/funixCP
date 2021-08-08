package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.Buss_MAPPED;
import javax.persistence.Entity;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Buss extends Buss_MAPPED {

    public Buss() {}
    public Buss(BussType bussType, Company company) {
        super(bussType, company);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //

public Buss getBuss() {
        return null;
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

