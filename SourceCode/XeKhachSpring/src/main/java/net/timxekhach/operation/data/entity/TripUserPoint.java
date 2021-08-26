package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.TripUserPoint_MAPPED;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class TripUserPoint extends TripUserPoint_MAPPED {

    public TripUserPoint() {}
    public TripUserPoint(PathPoint pathPoint, TripUser tripUser) {
        super(pathPoint, tripUser);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    public TripUserPoint(PathPoint point) {
        this.pathPoint = point;
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

