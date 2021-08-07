import {Trip} from "./trip";
import {User} from "./user";

export class TripUser {
  tripId: number;
  userId: number;
  user: User;
  trip: Trip;
  constructor() {
    this.trip = new Trip();
    this.user = new User();
  }
}
