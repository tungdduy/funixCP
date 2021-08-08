import {BussType} from "./buss-type";
import {Company} from "./company";

export class Buss {
  bussId: number;
  bussTypeId: number;
  companyId: number;
  bussType: BussType;
  company: Company;
  constructor() {
    this.bussType = new BussType();
    this.company = new Company();
  }
}
