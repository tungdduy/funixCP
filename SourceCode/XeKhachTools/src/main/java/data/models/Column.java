package data.models;


import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.security.constant.AuthEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Column {
    @Getter @Setter
    public static class Core {
        Object entity;
        Class<?> dataType;
        Object defaultValue;
        Number min, max;
        Integer maxSize, minSize;
        Boolean isEmail, isPhone, isNullable, isUnique;
        String regex, simpleClassName, fieldName, initialString = "";
        Set<String> imports = new HashSet<>();
        Set<String> staticImports = new HashSet<>();

        public String getSimpleClassName(){
            return dataType.getSimpleName();
        }
    }

    Core core = new Core();

    public Column() {};

    public Column(Class<?> dataType) {
        core.setDataType(dataType);
    }

    Column defaultValue(Object value) {
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
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getIsNullable() != null);
        core.setIsNullable(false);
        return this;
    }

    public Column unique() {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getIsUnique() != null);
        core.setIsUnique(true);
        return this;
    }

    public Column minLen(int minLength) {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getMinSize() != null);
        core.setMinSize(minLength);
        return this;
    }

    public Column email() {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getIsEmail() != null);
        core.setIsEmail(true);
        return this;
    }

    public Column phone() {
        ErrorCode.ASSIGN_1_TIME_ONLY.throwIf(core.getIsPhone() != null);
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
