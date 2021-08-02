package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.Company_MAPPED;
import javax.persistence.Entity;
import javax.persistence.PreRemove;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Company extends Company_MAPPED {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

@PreRemove
    public void preRemove() {
        this.deleteProfileImage();
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


}

