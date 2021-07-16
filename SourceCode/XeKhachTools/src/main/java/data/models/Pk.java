package data.models;

import data.entities.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Pk {
    private final Class<?> dataType;
    private final boolean isAutoIncrement;
    private final String simpleClassName;

    @Setter
    private String fieldName;

    public Pk() {
        this.dataType = Long.class;
        this.simpleClassName = Long.class.getSimpleName();
        this.isAutoIncrement = true;
    }
    public <E extends AbstractEntity> Pk(Class<E> dataType) {
        this.dataType = dataType;
        this.isAutoIncrement = false;
        this.simpleClassName = dataType.getSimpleName();
    }

}
