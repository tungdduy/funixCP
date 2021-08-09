import {BussSchedule} from "./BussSchedule";
import {BussPoint} from "./BussPoint";

export class BussSchedulePoint {
  bussSchedulePointId: number;

  bussScheduleId: number;
  bussSchedule: BussSchedule;

  bussPointId: number;
  bussPoint: BussPoint;
}
