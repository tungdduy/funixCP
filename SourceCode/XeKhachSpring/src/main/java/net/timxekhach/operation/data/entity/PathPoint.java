package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.PathPoint_MAPPED;
import javax.persistence.Transient;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class PathPoint extends PathPoint_MAPPED {

    public PathPoint() {}
    public PathPoint(Location location, Path path) {
        super(location, path);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    @Override
    protected void prePersist() {
        int currentMax = this.getPath().getPathPoints().stream().mapToInt(p -> p.pointOrder).max().orElse(0);
        this.pointOrder = currentMax + 1;
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

