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
import {XeScreen} from '../../../../framework/components/xe-nav/xe-nav.component';

@Component({
  selector: 'xe-company-manager',
  styleUrls: ['company-manager.component.scss'],
  templateUrl: 'company-manager.component.html',
})
export class CompanyManagerComponent extends FormAbstract {
  screens = {
    company: 'company',
    employees: 'employees',
    userSelection: 'userSelection'
  };
  screen = new XeScreen({
    home: this.screens.company,
    homeIcon: 'building',
    homeTitle: () => this.companyTable?.formData?.share?.entity?.companyName
  });


  companyTable: XeTableData<Company> = Company.tableData({
    xeScreen: this.screen,
    external: {
      updateCriteriaTableOnSelect: () => [this.employeeTable]
    },
    table: {
      basicColumns: [
        'companyName', 'companyDesc', 'hotLine', 'totalTrips', 'totalSchedules', 'totalBusses',
        {field: {name: 'totalEmployees'}, action: {screen: this.screens.employees}}
      ]
    },
  });
  employeeTable: XeTableData<Employee> = Employee.tableData({
    external: {
      lookUpScreen: this.screens.userSelection
    },
    xeScreen: this.screen,
  });

  userTable: XeTableData<User> = User.tableData({
    external: {
      parent: {tableData: this.employeeTable}
    },
    xeScreen: this.screen,
    table: {
      mode: {
        readonly: true
      },
      action: {
        filters: {
          filterSingle: (user: User) => user.employee == null,
        }
      }
    },
  });
}
