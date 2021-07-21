package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.entity.Location;


@MappedSuperclass @Getter @Setter
@IdClass(Location_MAPPED.Pk.class)
@SuppressWarnings("unused")
public abstract class Location_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long locationId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long locationId;
    }
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "parentLocationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    protected Location parent;

    public void setParent(Location parent) {
        this.parent = parent;
        this.parentLocationId = parent.getLocationId();
    }

    @Setter(AccessLevel.PRIVATE) //map join
    protected Long parentLocationId;

    @Size(max = 255)
    protected String locationName;

}
