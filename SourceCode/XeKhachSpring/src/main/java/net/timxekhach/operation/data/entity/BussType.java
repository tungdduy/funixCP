package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.BussType_MAPPED;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class BussType extends BussType_MAPPED {

    @Override
    public String getProfileImageUrl() {
        return super.getProfileImageUrl();
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    @Override
    protected void prePersist() {
        super.prePersist();
    }

    private Integer totalSeats;
    public Integer getTotalSeats() {
        if(this.totalSeats == null) {
            this.totalSeats = this.seatGroups.stream().mapToInt(SeatGroup::getTotalSeats).sum();
        }
        return this.totalSeats;
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

