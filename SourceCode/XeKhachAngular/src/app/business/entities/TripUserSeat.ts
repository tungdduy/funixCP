import {User} from "./User";
import {Trip} from "./Trip";

export class TripUserSeat {
  tripUserSeatId: number;
  tripId: number;
  userId: number;
  seatTypeId: number;
  user: User;
  trip: Trip;

}
