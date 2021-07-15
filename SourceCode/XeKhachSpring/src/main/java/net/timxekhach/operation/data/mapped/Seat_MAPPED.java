package net.timxekhach.operation.data.mapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;

import javax.persistence.*;

@IdClass(Seat_MAPPED.Pk.class)
@MappedSuperclass @Getter @Setter
public abstract class Seat_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    protected Long bussId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    protected Long seatId;

    public Seat_MAPPED(Long bussId) {
        this.bussId = bussId;
    }
    public Seat_MAPPED(){

    }

    @Column
    protected String description;

    @ManyToOne
    @JoinColumns({@JoinColumn(
            name = "bussId",
            referencedColumnName = "bussId",
            insertable = false,
            updatable = false
    )})
    protected Buss buss;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long seatId;
        protected Long bussId;
    }

}