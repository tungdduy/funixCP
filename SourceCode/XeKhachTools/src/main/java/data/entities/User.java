package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import lombok.Getter;

import static data.entities.abstracts.DataType.*;

@Getter
public class User extends AbstractEntity {
    Column email = of(EMAIL);
    Column phoneNumber = of(PHONE);
    Column password = of(REQUIRE_SHORT_STRING);
    Column username = of(REQUIRE_SHORT_STRING);
    Column role = of(DESCRIPTION).defaultValue("ROLE_USER");
    Column nonLocked = of(FALSE);
}
