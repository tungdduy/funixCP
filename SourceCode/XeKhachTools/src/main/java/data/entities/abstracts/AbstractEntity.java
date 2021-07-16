package data.entities.abstracts;

import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@SuppressWarnings("all") @Getter @Setter
public abstract class  AbstractEntity {
    List<Class<?>> primaryKeys = new ArrayList<>();
    protected <E extends AbstractEntity> void pk(Class<E> pk) {
        primaryKeys.add(pk);
    }
    protected Column of(DataType dataType) {
        return dataType.column;
    }
    protected <E extends AbstractEntity> MapColumn map(Class<E> e) {
        return new MapColumn(e);
    }

}
