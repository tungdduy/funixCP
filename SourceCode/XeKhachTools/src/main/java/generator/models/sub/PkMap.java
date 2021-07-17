package generator.models.sub;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PkMap {
    List<String> joins = new ArrayList<>();
    String simpleClassName, fieldName;
}
