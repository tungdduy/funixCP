package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.CountMethod;
import lombok.Getter;

import static data.entities.abstracts.DataType.CODE;
import static data.entities.abstracts.DataType.DESCRIPTION;

public class Buss extends AbstractEntity {
    {
        pk(Company.class);
        pk(BussType.class);
    }
    CountMethod totalBussEmployees = count(BussEmployee.class);
    CountMethod totalSchedules = count(BussSchedule.class);
    Column bussLicense = of(CODE);
    Column bussDesc = of(DESCRIPTION);

}
