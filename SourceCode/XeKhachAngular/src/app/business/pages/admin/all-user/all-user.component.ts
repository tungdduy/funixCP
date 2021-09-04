import {Component} from '@angular/core';
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {XeTableData} from "../../../../framework/model/XeTableData";
import {User} from "../../../entities/User";
import {Employee} from "../../../entities/Employee";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {CommonUpdateService} from "../../../service/common-update.service";

@Component({
  selector: 'xe-all-user',
  templateUrl: './all-user.component.html',
  styleUrls: ['./all-user.component.scss']
})
export class AllUserComponent extends FormAbstract {
  user = () => this.userTable?.formData?.share?.entity;
  userTable: XeTableData<User> = User.tableData({
    formData: {
      action: {
        postUpdate: (employee: Employee) => {
          if (employee.userId === AuthUtil.instance.user.userId) {
           CommonUpdateService.instance.refreshCurrentUser();
          }
        }
      },
      control: {
        allowDelete: true,
        allowAdd: true
      },
      display: {
        cancelBtn: "cancel"
      }
    }
  });
}
