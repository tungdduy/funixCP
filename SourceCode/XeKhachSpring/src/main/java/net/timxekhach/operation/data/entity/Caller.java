package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.Caller_MAPPED;

import javax.persistence.Entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Caller extends Caller_MAPPED {

    public Caller() {}
    public Caller(Company company) {
        super(company);
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //


}

