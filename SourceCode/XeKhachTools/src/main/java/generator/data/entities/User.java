package generator.data.entities;

import generator.data.entities.abstracts.AbstractEntity;
import generator.data.models.Column;
import generator.data.models.Pk;

@SuppressWarnings("all")
public class User extends AbstractEntity {
    Pk userId = pk(String.class);
    Column email = of(String.class).email();
    Column phone = of(String.class).phone();
    Column password = of(String.class).maxLen(25).minLen(3);
    Column fullName = of(String.class).maxLen(25).minLen(3);
}
