package data.models;


import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.response.ErrorCode;

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
                isUnique = false;
        String regex, simpleClassName, fieldName, initialString = "";

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
    }

    public Column defaultValue(Object value) {
        core.setDefaultValue(value);
        return this;
    }

    public Column min(Number min) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getMin() != null);
        core.setMin(min);
        return this;
    }

    public Column max(Number max) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getMax() != null);
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
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getMinSize() != null);
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

    public Column regex(String regex) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getRegex() != null);
        core.setRegex(regex);
        return this;
    }

    public Column maxLen(int maxSize) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getMaxSize() != null);
        core.setMaxSize(maxSize);
        return this;
    }


}
