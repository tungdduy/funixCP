package data.entities.abstracts;

import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.ReflectionUtil.newInstance;

@NoArgsConstructor
@SuppressWarnings("all")
@Getter
@Setter
public abstract class AbstractEntity {

    List<Class<?>> primaryKeyClasses = new ArrayList<>();
    List<AbstractEntity> primaryKeyEntities;

    public String getFullOperationClassName() {
        return "net.timxekhach.operation.data.entity." + this.getClass().getSimpleName();
    }

    public List<AbstractEntity> getPrimaryKeyEntities() {
        if (this.primaryKeyEntities == null) {
            primaryKeyEntities = new ArrayList<>();
            primaryKeyClasses.forEach(clazz -> {
                primaryKeyEntities.add((AbstractEntity) newInstance(clazz));
            });
        }
        return primaryKeyEntities;
    }

    protected <E extends AbstractEntity> void pk(Class<E> pkClass) {
        primaryKeyClasses.add(pkClass);
    }

    protected Column of(DataType dataType) {
        return dataType.column.get();
    }

    protected <E extends Enum> Column status(Class<E> enumClass) {
        return new Column(enumClass);
    }


    protected <E extends AbstractEntity> MapColumn map(Class<E> e) {
        return new MapColumn(e);
    }

}
