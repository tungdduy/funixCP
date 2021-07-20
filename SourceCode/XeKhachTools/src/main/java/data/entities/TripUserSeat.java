package data.entities;

import data.entities.abstracts.AbstractEntity;

public class TripUserSeat extends AbstractEntity {
    {
        pk(Trip.class);
        pk(User.class);
        pk(SeatType.class);
    }
}
