import {Component} from '@angular/core';
import {Employee} from "../../../entities/Employee";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {XeTableData} from "../../../../framework/model/XeTableData";
import {User} from "../../../entities/User";
import {FormAbstract} from "../../../../framework/model/form.abstract";

@Component({
  selector: 'xe-employee',
  styles: [],
  templateUrl: 'employee.component.html',
})
export class EmployeeComponent extends FormAbstract {
  user: User = AuthUtil.instance.user;
  employeeTable: XeTableData<Employee> = Employee.tableData({}, Employee.new({
    company: this.user?.employee?.company
  }));
}
