package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import javax.validation.constraints.*;


@MappedSuperclass @Getter @Setter
@IdClass(BussType_MAPPED.Pk.class)
public abstract class BussType_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long bussTypeId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussTypeId;
    }
    @Size(max = 255)
    @Column
    protected String bussTypeName;

    @Size(max = 255)
    @Column
    protected String bussTypeDesc;

}
