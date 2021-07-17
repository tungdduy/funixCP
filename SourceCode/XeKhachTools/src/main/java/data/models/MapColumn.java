package data.models;

import data.entities.abstracts.AbstractEntity;
import generator.models.sub.Join;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.ReflectionUtil.newInstance;

@AllArgsConstructor @Getter @Setter
public class MapColumn {

    @Getter @Setter
    public static class Core {
        String fieldName, mappedBy;
        Boolean isUnique = false;
        List<Join> joins = new ArrayList<>();
        MapTo mapTo;
        PrimaryKey primaryKey;
    }

    Core core;

    public MapColumn unique() {
        core.isUnique = true;
        return this;
    }

    public <E extends AbstractEntity> MapColumn(Class<E> entityClass) {
        core = new Core();
        core.setMapTo(new MapTo(entityClass));
    }

    @Getter @Setter
    public static class MapTo {
        MapTo(Class<?> entityClass) {
            this.entityClass = entityClass;
            this.simpleClassName = entityClass.getSimpleName();
        }
        AbstractEntity entity;
        Class<?> entityClass;
        String simpleClassName;

        public AbstractEntity getEntity() {
            if(this.entity == null) {
                this.entity = (AbstractEntity) newInstance(entityClass);
            }
            return this.entity;
        }
    }

}
