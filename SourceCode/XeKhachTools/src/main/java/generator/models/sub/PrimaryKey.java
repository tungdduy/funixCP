package generator.models.sub;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrimaryKey {
    boolean isAutoIncrement;
    String className, name;
}
