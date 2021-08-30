package generator.ts.xe.enums;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Option {
    String camelName;
    String camelValue;
    Boolean isUnique;
    List<Property> properties = new ArrayList<>();
    List<Property> manualProperties = new ArrayList<>();
    public List<Property> getProperties() {
        return properties.size() > 0 ? properties : Collections.singletonList(Property.name(this.camelName).stringValue(""));
    }

    public String getCapName() {
        return StringUtil.upperFirstLetter(camelName);
    }

    private Option(String camelName) {
        this.camelName = camelName;
    }
    public String getCamelValue() {
       return this.camelValue == null ? camelName : camelValue;
    }

    public static Option name(String camelName) {
        return new Option(camelName);
    }

    Option setProperties(Property... properties) {
        this.properties = Arrays.asList(properties);
        this.properties.forEach(prop -> prop.setOption(this));
        return this;
    }

    List<Property> fullChoiceProperties = new ArrayList<>();
    public List<Property> getFullChoiceProperties() {
        return tsEnum.getPropertyIdentifiers().stream().map(property -> {
            return this.getProperties().stream().filter(property::isSameName)
                    .findFirst()
                    .orElse(Property.name(property.camelName).value("null"));
        }).collect(Collectors.toList());
    }

    private TsEnum tsEnum;

    public void setTsEnum(TsEnum tsEnum) {
        this.tsEnum = tsEnum;
    }

    public Option setManualProperties(Property... properties) {
        this.manualProperties = Arrays.asList(properties);
        return this;
    }
}
