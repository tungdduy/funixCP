import {Component} from '@angular/core';
import {Employee} from "../../../entities/Employee";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {XeTableData} from "../../../../framework/model/XeTableData";
import {User} from "../../../entities/User";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {CommonUpdateService} from "../../../service/common-update.service";

@Component({
  selector: 'xe-employee',
  styles: [],
  templateUrl: 'employee.component.html',
})
export class EmployeeComponent extends FormAbstract {
  user: User = AuthUtil.instance.user;
  employeeTable: XeTableData<Employee> = Employee.tableData({
    formData: {
      action: {
        postUpdate: (employee: Employee) => {
          if (employee.userId === this.user.userId) {
            CommonUpdateService.instance.refreshCurrentUser();
          }
        }
      }
    }
  }, Employee.new({
    company: this.user?.employee?.company
  }));
}
