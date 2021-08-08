package generator.java.data.repository;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

@Getter
@Setter
public class CapCamel {
    String capName, camelName;

    public CapCamel(String name) {
        this.capName = StringUtil.toCapitalizeEachWord(name);
        this.camelName = StringUtil.toCamel(name);
    }
}
