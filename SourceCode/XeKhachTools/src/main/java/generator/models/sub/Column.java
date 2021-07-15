package generator.models.sub;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Column {
    String className;
    String name;
    String initialString = "";

    boolean hasOneToMany;
    String mappedBy;

    boolean hasManyToOne;
    List<Join> joins = new ArrayList<>();


    boolean hasOrderBy;
    String orderBy;

    boolean hasSize;
    Integer maxSize;
    Integer minSize;

    boolean isNotBlank;
    boolean isEmail;
    String regex;
}
