package net.timxekhach.operation.data.mapped;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeStringUtils;

import javax.persistence.*;

@MappedSuperclass @Getter @Setter
public abstract class Seat_MAPPED extends XeEntity {

    @EmbeddedId
    private Seat_MAPPED.Pk pk;
    @Column(insertable = false, updatable = false)
    protected String seatId;

    @Column(insertable = false, updatable = false)
    protected String bussId;

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

    public Seat_MAPPED() {}

    protected Seat_MAPPED(String bussId, String seatId) {
        this.checkFKConsistency(seatId);
        this.pk = new Seat_MAPPED.Pk(bussId, seatId);
        setSeatId(seatId);
    }

    private void checkFKConsistency(String seatId) {
        ErrorCode.UNDEFINED_ERROR.throwIf(seatId == null);
    }

    @Embeddable @Getter
    public static class Pk extends XePk {
        @Column
        protected String seatId;
        
        @Column
        protected String bussId;

        public Pk() {}

        public Pk(String bussId, String seatId) {
            this.seatId = XeStringUtils.idTrim(seatId);
            this.bussId = bussId;
        }
    }

}