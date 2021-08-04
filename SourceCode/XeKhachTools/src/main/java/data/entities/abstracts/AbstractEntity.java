package data.entities.abstracts;

import data.models.Column;
import data.models.MapColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static util.ReflectionUtil.newInstance;
import static util.StringUtil.toCamel;
import static util.StringUtil.toIdName;

@SuppressWarnings("all")
@Getter
@Setter
public abstract class AbstractEntity {

    protected List<Class<?>> primaryKeyClasses = new ArrayList<>();
    protected List<AbstractEntity> primaryKeyEntities;
    protected Set<String> primaryKeyIdNames;
    protected String capName, camelName;

    public boolean hasProfileImage() {
        return false;
    }

    protected AbstractEntity() {
        this.capName = this.getClass().getSimpleName();
        this.camelName = toCamel(this.capName);
    }

    public String getFullOperationClassName() {
        return "net.timxekhach.operation.data.entity." + this.capName;
    }

    public String idName(){
        return StringUtil.toIdName(this);
    }

    public List<AbstractEntity> getPrimaryKeyEntities() {
        if (this.primaryKeyEntities == null) {
            primaryKeyEntities = new ArrayList<>();
            primaryKeyClasses.forEach(clazz -> {
                primaryKeyEntities.add((AbstractEntity) newInstance(clazz));
            });
            primaryKeyEntities.sort((pk1, pk2) -> pk1.camelName.compareTo(pk2.camelName));
        }
        return primaryKeyEntities;
    }

    public Set<String> getPrimaryKeyIdNames() {
        if (this.primaryKeyIdNames == null) {
            primaryKeyIdNames = new TreeSet<>();
            primaryKeyClasses.forEach(clazz -> {
                primaryKeyIdNames.add(toIdName(clazz));
            });
        }
        return primaryKeyIdNames;
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
