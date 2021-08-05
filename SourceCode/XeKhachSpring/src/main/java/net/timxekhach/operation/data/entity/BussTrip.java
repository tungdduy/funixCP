package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.BussTrip_MAPPED;

import javax.persistence.Entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@Entity
@Getter
@Setter
public class BussTrip extends BussTrip_MAPPED {

    public BussTrip() {}
    public BussTrip(Buss buss, Company company) {
        super(buss, company);
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //


}

