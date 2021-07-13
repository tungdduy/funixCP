package net.timxekhach.operation.data.entity;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeStringUtils;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Seat {

    @EmbeddedId
    private Seat.Pk pk;
    @Column(insertable = false, updatable = false)
    protected String seat_id = null;

    @Column
    protected String description = null;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumns({@JoinColumn(
            name = "buss_id",
            referencedColumnName = "buss_id",
            insertable = false,
            updatable = false
    )})
    private Buss buss;

    @Column(nullable = false)
    private String buss_id;


    public Seat() {}

    protected Seat(String seat_id) {
        this.checkFKConsistency(seat_id);
        this.pk = new Seat.Pk(seat_id);
        setSeat_id(seat_id);
    }

    private void checkFKConsistency(String seat_id) {
        ErrorCode.UNDEFINED_ERROR.throwIf(seat_id == null);
    }

    public Seat.Pk getPk() {
        return pk;
    }

    @Embeddable
    public static class Pk extends XePk {
        @Column
        protected String seat_id = null;

        public Pk() {}

        public Pk(String seat_id) {
            this.seat_id = XeStringUtils.idTrim(seat_id);
        }

        public String getSeat_id() {
            return this.seat_id;
        }
    }

}