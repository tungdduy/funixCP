package generator.models.sub;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
public class PkMap {
    Set<String> joins = new HashSet<>();
    String simpleClassName, fieldName;
}
