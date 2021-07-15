package net.timxekhach.operation.data.entity;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.Seat_MAPPED;

import javax.persistence.Entity;

@Entity @Getter @Setter
public class Seat extends Seat_MAPPED {
    public Seat(Long bussId) {
        super(bussId);
    }

    public Seat() {
        super();
    }
}
