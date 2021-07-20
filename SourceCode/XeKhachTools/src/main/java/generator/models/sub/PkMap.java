package generator.models.sub;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class PkMap {
    Set<String> joins = new HashSet<>();
    String simpleClassName, fieldName;
}
