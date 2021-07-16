package data.models;

import data.entities.abstracts.AbstractEntity;
import generator.models.sub.Join;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @Getter @Setter
public class MapColumn {

    @Getter @Setter
    public static class Core {
        String fieldName, simpleClassName, initialString, mappedBy;
        Boolean unique;
        List<Join> joins = new ArrayList<>();
        Class<? extends AbstractEntity> entityClass;
        Pk pk;
    }

    Core core = new Core();

    //OneToMany
    public MapColumn(Pk pk) {
        core.pk = pk;
        core.simpleClassName = "Long";
    }

    //OneToOne
    public MapColumn unique() {
        core.unique = true;
        return this;
    }

    //ManyToOne
    public <E extends AbstractEntity> MapColumn(Class<E> entityClass) {
        core.entityClass = entityClass;
        core.simpleClassName = entityClass.getSimpleName();
    }



}
