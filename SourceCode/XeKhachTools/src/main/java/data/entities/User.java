package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;

import static data.entities.abstracts.DataType.*;

@Getter
public class User extends AbstractEntity {

    Column email = of(EMAIL).unique();
    Column phoneNumber = of(PHONE);
    Column password = of(SECRET_TOKEN);
    Column username = of(DESCRIPTION).unique();
    Column fullName = of(REQUIRE_SHORT_STRING);
    Column role = of(DESCRIPTION).defaultValue("ROLE_USER");
    Column nonLocked = of(FALSE);
    Column secretPasswordKey = of(SECRET_TOKEN);

    MapColumn allMyTrips = map(TripUser.class);
    MapColumn employee = map (Employee.class).unique();

    @Override
    public boolean hasProfileImage() {
        return true;
    }
}