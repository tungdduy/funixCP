package generator.ts.xe.enums;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

@Getter @Setter
public class TsEnum {

    String capName;
    public String getCamelName() {
        return StringUtil.toCamel(this.capName);
    }
    List<Option> options = new ArrayList<>();

    public Collection<Property> getPropertyIdentifiers() {
        Map<String, Property> propertiesMap = new HashMap<>();
        this.options.forEach(option -> {
            option.properties.forEach(prop -> {
                if (!propertiesMap.containsKey(prop.camelName)) {
                    propertiesMap.put(prop.camelName, prop);
                }
                propertiesMap.get(prop.camelName).addPropertyChoice(prop);
            });
        });
        return propertiesMap.values();
    }

    public TsEnum param(Option param) {
        this.options.add(param);
        return this;
    }

    public static TsEnum name(String capName) {
        TsEnum tsEnum = new TsEnum();
        tsEnum.capName = capName;
        return tsEnum;
    }
    public TsEnum optionsWithoutProperty(String... optionNames) {
        this.options.addAll(Arrays.stream(optionNames)
                .map(Option::name)
                .collect(Collectors.toList()));
        this.options.forEach(option -> option.setTsEnum(this));
        return this;
    }
    public TsEnum setOptions(Option... options) {
        this.options = Arrays.asList(options);
        this.options.forEach(option -> option.setTsEnum(this));
        return this;
    }
}
