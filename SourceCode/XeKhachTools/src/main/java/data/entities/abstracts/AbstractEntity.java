package data.entities.abstracts;

import data.models.Column;
import data.models.MapColumn;
import data.models.Pk;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@SuppressWarnings("all")
public abstract class AbstractEntity {
    int orderNo;
    public int getNextOrderNo(){
        return orderNo++;
    }

    protected Column of(Class<?> dataType) {
        return new Column(this, dataType);
    }
    protected Column email() {
        Column c = new Column(this, String.class);
        c.email();
        return c;
    }

    protected Column phone() {
        Column c = new Column(this, String.class);
        c.phone();
        return c;
    }

    protected <E extends AbstractEntity> Pk pk(Class<E> dataType) {
        return new Pk(this, dataType);
    }

    protected Pk pk() {
        return new Pk(this);
    }

    protected MapColumn map(Pk p) {
        return new MapColumn(p.getEntity(), p);
    }

}
