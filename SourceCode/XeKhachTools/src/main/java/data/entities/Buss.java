package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.Pk;

@SuppressWarnings("all")
public class Buss extends AbstractEntity {
    Pk bussId = pk();
    Column bussDesc = of(String.class);
    Pk company = pk(Company.class);
}
