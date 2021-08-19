package generator.ts.xe.enums;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.*;

@Getter
@Setter
public class Property {
    String camelName, valueAsString, originClassName, valueCapName, valuePost;
    Set<Property> propertyChoices = new HashSet<>();
    public void addPropertyChoice(Property prop) {
        if (this.propertyChoices.stream()
                .noneMatch(prop::isSameValue)) {
            this.propertyChoices.add(prop);
        }
    }

    boolean isSameName(Property property) {
        return this.camelName.equals(property.camelName);
    }
    boolean isSameValue(Property property) {
        return this.valueAsString.equals(property.valueAsString);
    }

    public String getValueAsString() {
        if (this.originClassName.equals("string")) return StringUtil.wrapInQuote(this.valueAsString);
        return this.valueAsString;
    }
    public String getValueCapName() {
        return StringUtil.toCapitalizeEachWord(this.valueAsString);
    }
    public String getValuePost() {
        return this.valuePost == null ? "" : this.valuePost;
    }
    
    public String getCapName() {
        return StringUtil.upperFirstLetter(this.camelName);
    }

    public Boolean getIsString() {
        return this.originClassName.equals("string");
    }

    private Property(String name) {
        this.camelName = name;
    }

    public static Property name(String camelName) {
        return new Property(camelName);
    }

    public Property value(String value) {
        this.valueAsString = value;
        return this;
    }
    public Property postFix(String valuePost) {
        this.valuePost = valuePost;
        return this;
    }
    
    public Property stringValue(String value) {
        this.valueAsString = value;
        this.originClassName = "string";
        return this;
    }

    public Property type(String type) {
        this.originClassName = type;
        return this;
    }

}
