package generator.java.data.mapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Join {
    String thisName, referencedName;
}
