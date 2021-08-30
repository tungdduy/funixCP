package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;

import static data.entities.abstracts.DataType.*;

public class User extends AbstractEntity {

    Column email = of(EMAIL).unique();
    Column phoneNumber = of(PHONE).unique();
    Column password = of(DESCRIPTION);
    Column username = of(DESCRIPTION).unique();
    Column fullName = of(REQUIRE_SHORT_STRING);
    Column role = of(DESCRIPTION).defaultValue("ROLE_USER");
    Column nonLocked = of(FALSE);
    Column secretPasswordKey = of(SECRET_TOKEN);

    MapColumn employee = map (Employee.class).unique();

    @Override
    public boolean hasProfileImage() {
        return true;
    }
}
