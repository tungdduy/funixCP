package generator.java.data.mapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter @AllArgsConstructor
public class Join {
    String thisName, referencedName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Join join = (Join) o;
        return Objects.equals(thisName, join.thisName) && Objects.equals(referencedName, join.referencedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thisName, referencedName);
    }
}
