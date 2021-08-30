package generator.ts.xe.enums;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.*;

@Getter @Setter
public class TsEnum {

    String capName;
    public String getCamelName() {
        return StringUtil.toCamel(this.capName);
    }
    List<Option> options = new ArrayList<>();
    Boolean hasBuildSelectMenu;

    public Collection<Property> getPropertyIdentifiers() {
        Map<String, Property> propertiesMap = new HashMap<>();
        this.options.forEach(option -> {
            option.getProperties().forEach(prop -> {
                if (!propertiesMap.containsKey(prop.camelName)) {
                    propertiesMap.put(prop.camelName, prop);
                }
                propertiesMap.get(prop.camelName).addPropertyChoice(prop);
            });
        });
        this.options.forEach(option -> {
            if(propertiesMap.get(option.camelName) == null) {
                option.setIsUnique(true);
            }
        });
        return propertiesMap.values();
    }

    public Collection<Property> getManualPropertyIdentifiers() {
        Map<String, Property> propertiesMap = new HashMap<>();
        this.options.forEach(option -> {
            option.manualProperties.forEach(prop -> {
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
    public TsEnum setOptions(Option... options) {
        this.options = Arrays.asList(options);
        this.options.forEach(option -> option.setTsEnum(this));
        return this;
    }

    public TsEnum buildSelectMenu() {
        this.hasBuildSelectMenu = true;
        return this;
    }
}
