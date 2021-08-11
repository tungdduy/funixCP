package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.Employee_MAPPED;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Employee extends Employee_MAPPED {

    public Employee() {}
    public Employee(Company company, User user) {
        super(company, user);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

