package net.timxekhach.operation.data.mapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.timxekhach.operation.data.entity.Seat;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@IdClass(Buss_MAPPED.Pk.class)
@MappedSuperclass
@Getter @Setter
public abstract class Buss_MAPPED extends XeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    Long bussId;

    @Column
    @Size(max = 255)
    protected String bussDesc;


    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pk extends XePk {
        protected Long bussId;
    }

    @OneToMany(
            mappedBy = "buss",
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("seatId")
    private final List<Seat> seats = new ArrayList<>();
}
