package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.BussEmployee_MAPPED;

import javax.persistence.Entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class BussEmployee extends BussEmployee_MAPPED {

    public BussEmployee() {}
    public BussEmployee(Buss buss, Employee employee) {
        super(buss, employee);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

