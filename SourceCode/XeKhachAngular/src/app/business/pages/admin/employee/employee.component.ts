import {Component} from '@angular/core';
import {Employee} from "../../../entities/Employee";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {XeTableData} from "../../../../framework/model/XeTableData";
import {User} from "../../../entities/User";

@Component({
  selector: 'xe-employee',
  styles: [],
  templateUrl: 'employee.component.html',
})
export class EmployeeComponent {
  user: User = AuthUtil.instance.user;
  employeeTable: XeTableData<Employee> = Employee.tableData({}, Employee.new({
    company: this.user?.employee?.company
  }));
}
