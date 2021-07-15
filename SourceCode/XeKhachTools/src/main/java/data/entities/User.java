package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.Pk;

@SuppressWarnings("all")
public class User extends AbstractEntity {
    Pk userId = pk();
    Column email = email();
    Column phone = phone();
    Column password = of(String.class).maxLen(25).minLen(3);
    Column fullName = of(String.class).maxLen(25).minLen(3);
}
