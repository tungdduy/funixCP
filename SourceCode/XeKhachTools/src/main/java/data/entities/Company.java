package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.CountMethod;

import static data.entities.abstracts.DataType.DESCRIPTION;
import static data.entities.abstracts.DataType.FALSE;

@SuppressWarnings("all")
public class Company extends AbstractEntity {
    Column companyDesc = of(DESCRIPTION);
    Column companyName = of(DESCRIPTION);
    Column isLock = of(FALSE);

    CountMethod totalEmployees = count(Employee.class);
    CountMethod totalBusses = count(Buss.class);
    CountMethod totalBussEmployees = count(BussEmployee.class);
    CountMethod totalCallers = count(Caller.class);
    CountMethod totalTrips = count(Trip.class);
    CountMethod totalBussTrips = count(BussTrip.class);

    @Override
    public boolean hasProfileImage() {
        return true;
    }
}
