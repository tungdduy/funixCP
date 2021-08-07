import {Buss} from "./buss";
import {BussPoint} from "./buss-point";

export class BussTrip {
  bussTripId: number;
  tripId: number;

  companyId: number;
  bussId: number;
  buss: Buss;


  startPointLocationId: number;
  startPointBussPointId: number;
  startPoint: BussPoint;

  endPointLocationId: number;
  endPointBusPointId: number;
  endPoint: BussPoint;

  launchTime: Date;
  effectiveDateFrom: Date;
  sunday: boolean;
  monday: boolean;
  tuesday: boolean;
  wednesday: boolean;
  thursday: boolean;
  friday: boolean;
  saturday: boolean;

  constructor() {
    this.endPoint = new BussPoint();
    this.startPoint = new BussPoint();
    this.buss = new Buss();
  }

}
