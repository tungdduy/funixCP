import {Component} from '@angular/core';
import {Company} from "../../../entities/Company";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {XeTableData} from '../../../../framework/model/XeTableData';
import {Employee} from '../../../entities/Employee';
import {User} from '../../../entities/User';
import {CommonUpdateService} from "../../../service/common-update.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Notifier} from "../../../../framework/notify/notify.service";
import {XeLabel} from "../../../i18n";
import { XeScreen } from '../../../../framework/components/xe-nav/xe-nav.component';
import {EntityUtil} from "../../../../framework/util/entity.util";

@Component({
  selector: 'xe-company-manager',
  styleUrls: ['company-manager.component.scss'],
  templateUrl: 'company-manager.component.html',
})
export class CompanyManagerComponent extends FormAbstract {

  screen = new XeScreen('company', 'building', () => this.currentCompany?.companyName);
  screens = {
    company: 'company',
    employees: 'employees',
    userSelection: 'userSelection'
  };
  currentCompany: Company = new Company();
  openEmployees = (company) => {
    this.currentCompany = company;
    this.screen.go(this.screens.employees);
  }

  employeeTable: XeTableData = Employee.employeeTable({
    xeScreen: this.screen,
    formData: {
      entityIdentifier: {
        idFields: () => [
          {name: "company.companyId", value: this.currentCompany.companyId}
        ]
      }
    }
  });
  companyTable: XeTableData = Company.companyTable({
    xeScreen: this.screen,
    table: {
      basicColumns: {
        6: {action: this.openEmployees}
      }
    },
    formData: {
      entityIdentifier: {
        idFields: () => [
          {name: "companyId", value: this.currentCompany.companyId}
        ]
      }
    }
  });

  userTable: XeTableData = User.userTable({
    xeScreen: this.screen,
    table: {
      filterCondition: (user: User) => user.employee == null,
    },
    formData: {readonly: true}
  });

  addSelectedUsersToCompany() {
    const selectedUsers = this.userTable.formData.share.selection.selected;
    const employees = selectedUsers.map(user => {
      const employee = {};
      employee['companyId'] = this.currentCompany.companyId;
      employee['userId'] = user.userId;
      return employee;
    });
    this.subscriptions.push(CommonUpdateService.instance.insertMulti<Employee>(employees, "Employee").subscribe(
      returnedEmployees => {
        console.log(this.userTable.formData.share.tableSource);
        const selectedUserIds = returnedEmployees.map(e => e.user.userId);
        this.userTable.formData.share.selection.clear();
        this.userTable.formData.share.tableSource.data = this.userTable.formData.share.tableSource.data.filter(e => !selectedUserIds.includes(e.userId));
        Notifier.success(XeLabel.SAVED_SUCCESSFULLY);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        Notifier.httpErrorResponse(error);
      }
    ));
  }
}
