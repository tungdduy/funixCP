package data.entities.abstracts;

import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;
import generator.java.data.repository.CapCamel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.StringUtil;

import java.util.*;

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

    public void getAllPkEntity(List<AbstractEntity> result) {
        result.addAll(this.primaryKeyEntities);
        this.primaryKeyEntities.forEach(pk -> pk.getAllPkEntity(result));
    }

    public void getAllPkIdsChain(List<AbstractEntity> pkEntities, String prepend, List<String> result, String joiner) {
        pkEntities.forEach(pk -> {
            String pre = prepend == null ? String.format("%s%s", pk.camelName, joiner) : String.format("%s%s%s", prepend, pk.camelName, joiner);
            result.add(String.format("%s%sId", pre, pk.camelName));
            getAllPkIdsChain(pk.primaryKeyEntities, pre, result, joiner);
            pre = prepend;
        });
    }

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

    public static Map<String, List<CapCamel>> whoCountMe = new HashMap<>();
    protected <E extends AbstractEntity>CountMethod count(Class<E> countEntity) {
        List<CapCamel> countMe = whoCountMe.getOrDefault(countEntity.getSimpleName(), new ArrayList<>());
        CapCamel counter = new CapCamel(this.capName);
        if(countMe.stream().noneMatch(me -> me.getCapName().equalsIgnoreCase(counter.getCapName()))){
            countMe.add(counter);
            whoCountMe.put(countEntity.getSimpleName(), countMe);
        }
        return new CountMethod(this.getClass().getSimpleName(), countEntity.getSimpleName());
    }

}
