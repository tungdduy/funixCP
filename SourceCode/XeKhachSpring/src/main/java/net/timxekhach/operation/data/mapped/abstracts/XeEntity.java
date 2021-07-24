package net.timxekhach.operation.data.mapped.abstracts;

import net.timxekhach.utility.Xe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@MappedSuperclass
public abstract class XeEntity implements Serializable {
    @Transient
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
