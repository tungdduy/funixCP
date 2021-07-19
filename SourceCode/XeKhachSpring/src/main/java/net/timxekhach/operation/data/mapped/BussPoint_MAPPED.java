package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.Location;


@MappedSuperclass @Getter @Setter
@IdClass(BussPoint_MAPPED.Pk.class)
public abstract class BussPoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long locationId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussPointId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long locationId;
        protected Long bussPointId;
    }
    protected BussPoint_MAPPED(){}
    protected BussPoint_MAPPED(Location location) {
        this.location = location;
    }
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "locationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    protected Location location;

    public void setLocation(Location location) {
        this.location = location;
        this.locationId = location.getLocationId();
    }

    @Size(max = 255)
    @Column
    protected String bussPointDesc;

}
