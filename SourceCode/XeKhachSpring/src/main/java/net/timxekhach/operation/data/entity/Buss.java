package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.Buss_MAPPED;
import net.timxekhach.utility.XeStringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Buss extends Buss_MAPPED {

    public Buss() {}
    public Buss(BussType bussType, Company company) {
        super(bussType, company);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    public List<Integer> getLockedSeats() {
        return XeStringUtils.commaGt0ToStream(this.lockedSeatsString).collect(Collectors.toList());
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

