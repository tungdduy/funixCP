package data.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrimaryKey {
    private boolean isAutoIncrement;
    private String fieldName;
}
