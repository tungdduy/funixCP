package data.models;

import data.entities.abstracts.AbstractEntity;

public class Pk<E extends AbstractEntity> extends Column<E> {
    public  Pk(E e, Class<E> dataType) {
        super(e, dataType);
        this.isPk = true;
        this.isNullable = false;
    }

    public Pk(E e) {
        super(e, Long.class);
    }
}
