import {Buss} from "./buss";
import {BussPoint} from "./buss-point";

export class Trip {
  tripId: number;

  bussId: number;
  bussTypeId: number;
  companyId: number;
  buss: Buss;

  price: number;
  status: string;
  startTime: Date;

  startPointBussPointId: number;
  startPointLocationId: number;
  startPoint: BussPoint;

  endPointBussPointId: number;
  endPointLocationId: number;
  endPoint: BussPoint;

  constructor() {
    this.buss = new Buss();
  }
}