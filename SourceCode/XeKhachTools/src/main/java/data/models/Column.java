package data.models;


import data.entities.abstracts.AbstractEntity;
import net.timxekhach.operation.response.ErrorCode;

@SuppressWarnings("all")
public class Column<E extends AbstractEntity> {
    int __orderNo;
    E entity;

    public E getEntity() {
        return entity;
    }

    Class<?> dataType;
    Object defaultValue;
    Number min, max;
    Integer maxLength, minLength;
    Boolean isEmail, isPhone, isList, isNullable, isPk, isUnique;
    String regex;


    public Column(E entity) {
        this.entity = entity;
        this.__orderNo = entity.getNextOrderNo();
    }

    public Column(E entity, Class<?> dataType) {
        this(entity);
        this.dataType = dataType;
    }

    Column defaultValue(Object value) {
        this.defaultValue = value;
        return this;
    }

    public Column min(Number min) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.min != null);
        this.min = min;
        return this;
    }

    public Column max(Number max) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.max != null);
        this.max = max;
        return this;
    }

    public Column notNull() {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.isNullable != null);
        this.isNullable = false;
        return this;
    }

    public Column unique() {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.isNullable != null);
        this.isUnique = true;
        return this;
    }

    public Column minLen(int minLength) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.minLength != null);
        this.minLength = minLength;
        return this;
    }

    public Column email() {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.isEmail != null);
        this.isEmail = true;
        return this;
    }

    public Column phone() {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.isPhone != null);
        this.isPhone = true;
        return this;
    }

    public Column regex(String regex) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.regex != null);
        this.regex = regex;
        return this;
    }

    public Column maxLen(int maxLength) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.maxLength != null);
        this.maxLength = maxLength;
        return this;
    }

    public Column list(){
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(this.isList != null);
        this.isList = true;
        return this;
    }



}
