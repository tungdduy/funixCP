package net.timxekhach.operation.data.mapped;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.entity.Seat;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeStringUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Getter @Setter
public abstract class Buss_MAPPED extends XeEntity {

    @EmbeddedId
    protected Buss_MAPPED.Pk pk;

    @Column(
            insertable = false,
            updatable = false
    )
    protected String bussId;

    @Column
    @Size(max = 255)
    protected String bussDesc;

    public Buss_MAPPED(){}
    public Buss_MAPPED(String bussId) {
        ErrorCode.VALIDATOR_NOT_BLANK.throwIfBlank(bussId, "Buss ID");
        this.pk = new Buss_MAPPED.Pk(bussId);
        this.bussId = bussId;
    }

    @Embeddable @Getter
    public static class Pk extends XePk {
        @Column
        protected String bussId = null;
        public Pk() {}
        public Pk(String bussId) {
            this.bussId = XeStringUtils.idTrim(bussId);
        }
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