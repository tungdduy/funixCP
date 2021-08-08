import {XeEntity} from "./xe-entity";
import {User} from "./user";
import {Company} from "./company";

export class Employee extends XeEntity {
  companyId: number;
  employeeId: number;
  userId: number;
  isLock: boolean;
  user: User;
  company: Company;

  constructor() {
    super();
  }
}
