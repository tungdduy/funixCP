package generator.java.data.mapped;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static util.StringUtil.toCamel;

@Getter @Setter
public class PkMap {
    Set<String> joins = new HashSet<>();
    String simpleClassName, fieldName;
}
