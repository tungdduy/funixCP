package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import lombok.Getter;

import static data.entities.abstracts.DataType.*;

@Getter
public class User extends AbstractEntity {
    Column email = of(EMAIL);
    Column phone = of(PHONE);
    Column password = of(REQUIRE_SHORT_STRING);
    Column fullName = of(REQUIRE_SHORT_STRING);
}
