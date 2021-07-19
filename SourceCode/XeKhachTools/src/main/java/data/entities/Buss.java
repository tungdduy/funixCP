package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import lombok.Getter;

import static data.entities.abstracts.DataType.DESCRIPTION;

@Getter
public class Buss extends AbstractEntity {
    {
        pk(Company.class);
        pk(BussType.class);
    }
    Column bussDesc = of(DESCRIPTION);
}
