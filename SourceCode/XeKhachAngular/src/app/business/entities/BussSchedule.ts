import {Buss} from "./Buss";
import {BussPoint} from "./BussPoint";
import {Company} from "./Company";
import {BussSchedulePoint} from "./BussSchedulePoint";

export class BussSchedule {

  bussId: number;
  buss: Buss;

  companyId: number;
  company: Company;

  price: number;
  launchTime: Date;
  effectiveDateFrom: Date;

  sunday: boolean;
  monday: boolean;
  tuesday: boolean;
  wednesday: boolean;
  thursday: boolean;
  friday: boolean;
  saturday: boolean;

  startPointBussPointId: number;
  startPoint: BussPoint;

  endPointBussPointId: number;
  endPoint: BussPoint;

  middlePoints: BussSchedulePoint;

}
