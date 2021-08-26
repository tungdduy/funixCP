package generator.java.data.mapped;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class PkMap {
    Set<String> joins = new HashSet<>();
    String simpleClassName, fieldName;
    public String getFieldCapName() {
        return StringUtil.toCapitalizeEachWord(this.fieldName);
    }
}
