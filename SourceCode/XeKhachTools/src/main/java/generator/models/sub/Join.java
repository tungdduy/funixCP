package generator.models.sub;

import data.models.Pk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Join {
    String thisName, referenceName;

    public Join(Pk pk) {
        if(pk.getDataType() != Long.class) {
            throw new RuntimeException("Join constructor accept Long Pk only!");
        }
        this.thisName = pk.getFieldName();
        this.referenceName = pk.getFieldName();
    }
}
