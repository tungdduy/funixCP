import {Employee} from "./employee";
import {Buss} from "./buss";

export class BussEmployee {
  bussEmployeeId: number;
  employeeId: number;
  bussEmployee: number;
  companyId: number;
  employee: Employee;
  buss: Buss;
  isLock: boolean;

  constructor() {
    this.employee = new Employee();
    this.buss = new Buss();
  }
}
