package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import lombok.Getter;
import lombok.Setter;

import static data.entities.abstracts.DataType.DESCRIPTION;

@Getter @Setter
public class BussPoint extends AbstractEntity {
    {
        pk(Location.class);
    }
    Column bussPointDesc = of(DESCRIPTION);

}
