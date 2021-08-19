package generator.ts.xe.enums;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Option {
    String camelName;
    List<Property> properties = new ArrayList<>();

    public String getCapName() {
        return StringUtil.upperFirstLetter(camelName);
    }

    private Option(String camelName) {
        this.camelName = camelName;

    }

    public static Option name(String camelName) {
        return new Option(camelName);
    }

    Option setProperties(Property... params) {
        this.properties = Arrays.asList(params);
        return this;
    }

    List<Property> fullChoiceProperties = new ArrayList<>();
    public List<Property> getFullChoiceProperties() {
        return tsEnum.getPropertyIdentifiers().stream().map(property -> {
            return this.properties.stream().filter(property::isSameName)
                    .findFirst()
                    .orElse(Property.name(property.camelName).type("").value("null"));
        }).collect(Collectors.toList());
    }

    private TsEnum tsEnum;

    public void setTsEnum(TsEnum tsEnum) {
        this.tsEnum = tsEnum;
    }
}
