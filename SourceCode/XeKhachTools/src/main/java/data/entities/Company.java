package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.CountMethod;

import static data.entities.abstracts.DataType.*;

@SuppressWarnings("all")
public class Company extends AbstractEntity {

    Column companyName = of(DESCRIPTION);
    Column companyDesc = of(DESCRIPTION);
    Column hotLine = of(PHONE);
    Column isLock = of(FALSE);

    CountMethod totalEmployees = count(Employee.class);
    CountMethod totalBusses = count(Buss.class);
    CountMethod totalBussEmployees = count(BussEmployee.class);
    CountMethod totalTrips = count(Trip.class);
    CountMethod totalSchedules = count(BussSchedule.class);

    @Override
    public boolean hasProfileImage() {
        return true;
    }
}
