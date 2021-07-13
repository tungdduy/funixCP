package net.timxekhach.operation.data.entity;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.Buss_MAPPED;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Buss extends Buss_MAPPED {
    public Buss(){}

    public Buss(String bussId) {
        super(bussId);
    }

}