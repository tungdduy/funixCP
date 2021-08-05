import {SeatType} from "./seat-type";
import {User} from "./user";
import {Trip} from "./trip";

export class TripUserSeat {
  tripUserSeatId: number;
  tripId: number;
  userId: number;
  seatTypeId: number;
  user: User;
  seatType: SeatType;
  trip: Trip;

}
