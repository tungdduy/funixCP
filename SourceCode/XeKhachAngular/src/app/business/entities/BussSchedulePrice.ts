import {BussSchedulePoint} from "./BussSchedulePoint";
import {BussSchedule} from "./BussSchedule";

export class BussSchedulePrice {

  bussSchedulePriceId: number;
  bussSchedule: BussSchedule;

  price: number;

  pointFromBussSchedulePointId: number;
  pointFrom: BussSchedulePoint;

  pointToBussSchedulePointId: number;
  pointTo: BussSchedulePoint;
}
