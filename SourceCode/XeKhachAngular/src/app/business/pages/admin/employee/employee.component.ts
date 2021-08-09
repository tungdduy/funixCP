import {Component} from '@angular/core';
import {Employee} from "../../../entities/Employee";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {XeTableData} from "../../../../framework/model/XeTableData";

@Component({
  selector: 'xe-employee',
  styles: [],
  templateUrl: 'employee.component.html',
})
export class EmployeeComponent {
  user = AuthUtil.instance.user;
  company = this.user.employee.company;
  employeeTable: XeTableData = Employee.employeeTable({
    formData: {
      entityIdentifier: {
        idFields: () => [
          {name: "company.companyId", value: this.company.companyId}
        ]
      }
    }
  });
}
