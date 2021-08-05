import {Component, OnInit, ViewChild} from '@angular/core';
import {Company} from "../../../entities/company";
import {FormAbstract} from "../../../abstract/form.abstract";
import { XeTableData } from '../../../abstract/XeTableData';
import { Employee } from '../../../entities/employee';
import { User } from '../../../entities/user';
import {CommonUpdateService} from "../../../service/common-update.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Notifier} from "../../../../framework/notify/notify.service";
import {XeLabel} from "../../../i18n";

class Screen {
  screen: 'company' | 'employees' | 'user-selection' = 'company';
  company() {
    this.screen = 'company';
  }
  isCompany() {
    return this.screen === 'company';
  }
  employees() {
    this.screen = 'employees';
  }
  isEmployees() {
    return this.screen === 'employees';
  }
  userSelection() {
    this.screen = 'user-selection';
  }
  isUserSelection() {
    return this.screen === 'user-selection';
  }

}

@Component({
  selector: 'xe-company-manager',
  styleUrls: ['company-manager.component.scss'],
  templateUrl: 'company-manager.component.html',
})
export class CompanyManagerComponent extends FormAbstract {

  @ViewChild("totalEmployees") totalEmployees;

  companyTable: XeTableData = {
    table: {
      basicColumns: [
        {field: {name: 'companyName'}, type: "avatarString"},
        {field: {name: 'companyDesc'}, type: "string"},
      ],
      manualColumns: [
        {field: {name: 'totalEmployees'}, template: () => this.totalEmployees}
      ]
    },
    formData: {
      entityIdentifier: {
        className: "Company",
        idFields: () => [{name: "companyId", value: 0}],
      },
      share: {entity: new Company()},
      header: {
        profileImage: {name: 'profileImageUrl'},
        titleField: {name: 'companyName'},
        descField: {name: 'companyDesc'},
      },
      fields: [
        {name: "companyName", required: false},
        {name: "companyDesc", required: false},
      ]
    }
  };

  employeeTable: XeTableData = {
    table: {
      basicColumns: [
        {field: {name: 'user.fullName'}, type: "avatarString"},
        {field: {name: 'user.username'}, type: "boldStringRole"},
        {field: {name: 'user.email'}, type: "string", iconAfter: 'mobile-alt',
          subColumns: [{
          field: {name: 'user.phoneNumber', css: 'd-block text-info'}, type: 'string'
          }]},
      ],
    },
    formData: {
      entityIdentifier: {
        className: "Employee",
        idFields: () => [
          {name: "company.companyId", value: this.currentCompany.companyId},
          {name: "user.userId", value: 0},
          {name: "employeeId", value: 0},
        ]
      },
      share: {entity: new Employee()},
      header: {
        profileImage: {name: 'user.profileImageUrl'},
        titleField: {name: 'user.fullName'},
        descField: {name: 'user.phoneNumber'},
      },
      fields: [
        {name: "user.username", required: true},
        {name: "user.phoneNumber", required: true},
        {name: "user.fullName", required: true},
        {name: "user.email", required: true},
        {name: "user.role", hidden: true},
        {name: "user.password", required: false, clearOnSuccess: true},
      ]
    }
  };
  user = () => this.employeeTable.formData.share.entity.user;

  userTable: XeTableData = {
    table: {
      filterCondition: (user: User) => {
        return user.employee == null;
      },
      basicColumns: [
        {field: {name: 'username'}, type: "avatarString"},
        {field: {name: 'fullName'}, type: "string"},
        {field: {name: 'phoneNumber'}, type: "string"},
        {field: {name: 'email'}, type: "string"},
      ],
    },
    formData: {
      entityIdentifier: {
        className: "User",
        idFields: () => [{name: "userId", value: 0}]
      },
      readonly: true,
      share: {entity: new User()},
      header: {
        titleField: {name: 'fullName'},
        descField: {name: 'phoneNumber'},
      },
      fields: []
    }
  };


  currentCompany: Company;
  openEmployees(company: Company) {
    this.currentCompany = company;
    this.screen.employees();
  }

  screen = new Screen();

  constructor(private commonService: CommonUpdateService) {
    super();
  }
  addSelectedUsersToCompany() {
    const selectedUsers = this.userTable.formData.share.selection.selected;
    const employees =  selectedUsers.map(user => {
      const employee = {};
      employee['companyId'] = this.currentCompany.companyId;
      employee['userId'] = user.userId;
      return employee;
    });
    this.subscriptions.push(this.commonService.insertMulti<Employee>(employees, "Employee").subscribe(
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
