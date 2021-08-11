package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import java.util.stream.Collectors;
import net.timxekhach.operation.data.mapped.TripUserSeat_MAPPED;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Trip extends Trip_MAPPED {

    public Trip() {}
    public Trip(BussSchedule bussSchedule) {
        super(bussSchedule);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

