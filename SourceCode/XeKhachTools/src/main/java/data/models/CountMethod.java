package data.models;

import lombok.Getter;
import lombok.Setter;

import static util.StringUtil.toCamel;

@Getter
@Setter
public class CountMethod {

    public CountMethod(String thisCapName, String countCapName) {
        this.thisCapName = thisCapName;
        this.countCapName = countCapName;
        this.thisCamelName = toCamel(thisCapName);
        this.countCamelName = toCamel(countCapName);
    }
    String fieldCapName, fieldCamelName, countCamelName, countCapName, thisCapName, thisCamelName;
}
