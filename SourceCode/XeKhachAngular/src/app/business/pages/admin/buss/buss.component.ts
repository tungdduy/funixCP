import {AfterViewInit, Component} from '@angular/core';
import {XeTableData} from "../../../../framework/model/XeTableData";
import {Buss} from "../../../entities/Buss";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {Company} from "../../../entities/Company";
import {BussTypeUtil} from "../../../entities/BussType";
import {BasicBussScheme} from "../../../../framework/components/basic-buss-scheme/basic-buss-scheme.component";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {Employee} from "../../../entities/Employee";
import {BussEmployee} from "../../../entities/BussEmployee";
import {CommonUpdateService} from "../../../service/common-update.service";
import {Notifier} from "../../../../framework/notify/notify.service";
import {XeLabel} from "../../../i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {FormAbstract} from "../../../../framework/model/form.abstract";

@Component({
  selector: 'xe-buss',
  styles: [],
  templateUrl: 'buss.component.html',
})
export class BussComponent extends FormAbstract implements AfterViewInit {
  ngAfterViewInit(): void {
    BussTypeUtil.catchBussTypes();
  }

  screens = {
    busses: 'busses',
    bussScheme: 'bussScheme',
    bussEmployees: 'bussEmployees',
    employeeSelection: 'employeeSelection'
  };
  screen = new XeScreen(this.screens.busses, 'bus', () => `${this.currentBuss.bussLicense} (${this.currentBuss.bussDesc})`);

  _currentBuss: Buss = new Buss();
  get currentBuss() {
    return this._currentBuss;
  }
  set currentBuss(buss: Buss) {
    Object.assign(this.currentBuss, buss);
  }
  myCompany: Company = AuthUtil.instance.user?.employee?.company;
  bussTable = Buss.bussTableData({
    xeScreen: this.screen,
    table: {
      basicColumns: {
        3: {
          action: (buss) => this.viewBussScheme(buss)
        },
        4: {
          action: (buss) => this.viewBussEmployees(buss)
        }
      }
    }
  });

  bussScheme: BasicBussScheme = {
    bussType: () => this.currentBuss.bussType,
    turnBackAction: this.screen.back
  };

  viewBussScheme(buss: Buss) {
    this.currentBuss = buss;
    this.screen.go(this.screens.bussScheme);
  }

  bussEmployeeTable: XeTableData = BussEmployee.bussEmployeeTable({
    xeScreen: this.screen,
    formData: {
      entityIdentifier: {
        idFields: () => [
          {name: "buss.bussId", value: this.currentBuss.bussId},
          {name: "buss.bussType.bussTypeId", value: this.currentBuss.bussTypeId},

          {name: "employee.employeeId", value: 0, newIfNull: 'Employee'},
          {name: "employee.companyId", value: this.currentBuss.companyId},

          {name: "employee.user.userId", value: 0, newIfNull: 'User'},
        ]
      },
      share: {
        custom: {
          buss: () => this.currentBuss
        }
      }
    }
  });

  private viewBussEmployees(buss: Buss) {
    Object.assign(this.currentBuss, buss);
    console.log(buss);
    this.screen.go(this.screens.bussEmployees);
  }

  employeeTable: XeTableData = Employee.employeeTable({
    xeScreen: this.screen,
    table: {
      filterCondition: (employee: Employee) => !employee.countBusses || employee.countBusses === 0
    },
    formData: {
      readonly: true,
      entityIdentifier: {
        idFields: () => [
          {name: "company.companyId", value: this.myCompany.companyId},
        ]
      }
    }
  });

  addSelectedEmployeesToBuss() {
    const selectedEmployees = this.employeeTable.formData.share.selection.selected;
    const bussEmployees = selectedEmployees.map(employee => {
      const bussEmployee = {};
      bussEmployee['bussEmployeeId'] = 0;
      bussEmployee['bussTypeId'] = this.currentBuss.bussTypeId;
      bussEmployee['employeeId'] = employee.employeeId;
      bussEmployee['bussId'] = this.currentBuss.bussId;
      bussEmployee['companyId'] = this.myCompany.companyId;
      bussEmployee['userId'] = employee.userId;
      return bussEmployee;
    });
    this.subscriptions.push(CommonUpdateService.instance.insertMulti<BussEmployee>(bussEmployees, "BussEmployee").subscribe(
      returnedBussEmployees => {
        console.log(returnedBussEmployees);
        const selectedEmployeeIds = returnedBussEmployees.map(be => be.employeeId);
        const share = this.employeeTable.formData.share;
        share.selection.clear();
        share.tableSource.data = share.tableSource.data.filter(e => !selectedEmployeeIds.includes(e.employeeId));
        Notifier.success(XeLabel.SAVED_SUCCESSFULLY);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        Notifier.httpErrorResponse(error);
      }
    ));
  }
}

