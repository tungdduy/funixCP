package generator.data.entities.abstracts;

import generator.data.models.Column;
import generator.data.models.Pk;

public class AbstractEntity {
    protected Column of(Class<?> dataType) {
        return new Column(dataType);
    }

    protected Pk pk(Class<?> dataType) {
        return new Pk(dataType);
    }
}
