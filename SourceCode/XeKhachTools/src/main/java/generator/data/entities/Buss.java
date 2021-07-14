package generator.data.entities;

import generator.data.entities.abstracts.AbstractEntity;
import generator.data.models.Pk;

@SuppressWarnings("all")
public class Buss extends AbstractEntity {
    Pk company = pk(Company.class);
    Pk seqNo = pk(Integer.class);
}
