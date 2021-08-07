import {BussType} from "./buss-type";

export class SeatType {
  seatTypeId: number;
  bussTypeId: number;
  bussType: BussType;
  name: string;

  constructor() {
    this.bussType = new BussType();
  }
}
