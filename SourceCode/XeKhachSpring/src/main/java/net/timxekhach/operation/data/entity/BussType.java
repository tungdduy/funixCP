package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.BussType_MAPPED;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    @Transient
    private Integer totalSeats;
    public Integer getTotalSeats() {
        if(this.totalSeats == null) {
            this.totalSeats = this.seatGroups.stream().mapToInt(SeatGroup::getTotalSeats).sum();
        }
        return this.totalSeats;
    }
    public List<Integer> getSeats() {
        return IntStream.range(1, this.getTotalSeats() + 1).boxed().collect(Collectors.toList());
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

