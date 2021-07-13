package net.timxekhach.operation.data.mapped.abstracts;

import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

@MappedSuperclass
@SequenceGenerator(name="id_seq", sequenceName = "ID_SEQ")
public abstract class XeEntity implements Serializable {

}
