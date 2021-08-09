package data.models;


import lombok.Getter;
import lombok.Setter;

@Getter
public class Column {
    @Getter @Setter
    public static class Core {
        Class<?> dataType;
        Object defaultValue;
        Number min, max;
        Integer maxSize, minSize;
        Boolean isEmail = false,
                isPhone = false,
                isNotNull = false,
                isUnique = false,
                isEnumerated = false,
                isManualUpdatable = true,
                jsonIgnore = false,
                upperOnly = false;
        String regex, simpleClassName, fieldName, initialString;

        public String getSimpleClassName(){
            return dataType.getSimpleName();
        }
    }

    Core core = new Core();

    public Column() {
        this.core.dataType = String.class;
    };

    public Column(Class<?> dataType) {
        core.setDataType(dataType);
        if (Enum.class.isAssignableFrom(dataType)){
            core.isEnumerated = true;
        }
    }

    public Column defaultValue(Object value) {
        core.setDefaultValue(value);
        return this;
    }

    public Column min(Number min) {
        core.setMin(min);
        return this;
    }

    public Column max(Number max) {
        core.setMax(max);
        return this;
    }

    public Column notNull() {
        core.setIsNotNull(true);
        return this;
    }

    public Column unique() {
        core.setIsUnique(true);
        return this;
    }

    public Column minLen(int minLength) {
        core.setMinSize(minLength);
        return this;
    }

    public Column email() {
        core.setIsEmail(true);
        return this;
    }

    public Column phone() {
        core.setIsPhone(true);
        return this;
    }

    public Column upperOnly() {
        core.setUpperOnly(true);
        return this;
    }

    public Column regex(String regex) {
        core.setRegex(regex);
        return this;
    }

    public Column maxLen(int maxSize) {
        core.setMaxSize(maxSize);
        return this;
    }

    public Column jsonIgnore() {
        core.jsonIgnore = true;
        return this;
    }

    public Column ignoreManualUpdate() {
        core.isManualUpdatable = false;
        return this;
    }

}
