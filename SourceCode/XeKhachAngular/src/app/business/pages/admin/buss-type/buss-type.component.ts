import {AfterViewInit, Component} from '@angular/core';
import {XeTableData} from "../../../../framework/model/XeTableData";
import {BussType} from "../../../entities/BussType";
import {Buss} from "../../../entities/Buss";
import {XeSubscriber} from "../../../../framework/model/XeSubscriber";
import {Company} from "../../../entities/Company";
import {CommonUpdateService} from "../../../service/common-update.service";
import {Notifier} from "../../../../framework/notify/notify.service";
import {SelectItem} from "../../../../framework/model/SelectItem";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {BussSchedule} from "../../../entities/BussSchedule";
import {BussEmployee} from "../../../entities/BussEmployee";
import {Employee} from "../../../entities/Employee";
import {InputTemplate} from "../../../../framework/model/EnumStatus";

@Component({
  selector: 'xe-buss-type',
  styleUrls: ['buss-type.component.scss'],
  templateUrl: 'buss-type.component.html',
})
export class BussTypeComponent extends XeSubscriber implements AfterViewInit {

  screens = {
    bussTypeList: 'bussTypeList',
    bussScheme: 'bussScheme',
    bussList: 'bussList',
    schedules: 'schedules',
    employeeSelection: 'employeeSelection',
    bussEmployees: 'bussEmployees'
  };
  screen = new XeScreen({
    home: this.screens.bussTypeList,
    homeIcon: 'pencil-ruler',
    homeTitle: () => `${this.bussTypeTable.formData.share.entity?.bussTypeName} (${this.bussTypeTable.formData.share.entity?.bussTypeCode})`
  });

  bussTypeTable: XeTableData<BussType> = BussType.tableData({
    xeScreen: this.screen,
    external: {
      updateCriteriaTableOnSelect: () => [this.bussTable]
    },
    table: {
      basicColumns: [{}, {}, {},
        {action: {screen: this.screens.bussScheme}},
        {action: {screen: this.screens.bussList}}
      ]
    }
  });

  companySelectItems: SelectItem<Company>[];
  bussTable: XeTableData<Buss> = Buss.tableData({
    external: {
      updateCriteriaTableOnSelect: () => [this.bussEmployeeTable, this.employeeTable, this.bussScheduleTable]
    },
    xeScreen: this.screen,
    table: {
      basicColumns: [undefined, undefined, {}, {}, undefined,
        {action: {screen: this.screens.bussEmployees}},
        {action: {screen: this.screens.schedules}}
      ],
    },
    formData: {
      action: {
        postCancel: (entity) => {
          console.log("cancelled");
        }
      },
      fields: [
        {}, {}, {},
        {
          name: "companyId",
          required: true,
          template: InputTemplate.selectOneMenu,
          selectOneMenu: () => this.companySelectItems
        },
      ]
    }
  });

  bussEmployeeTable: XeTableData<BussEmployee> = BussEmployee.tableData({
    external: {
      lookUpScreen: this.screens.employeeSelection
    },
    xeScreen: this.screen,
  }, BussEmployee.new());


  employeeTable: XeTableData<Employee> = Employee.tableData({
    external: {
      parent: this.bussEmployeeTable
    },
    xeScreen: this.screen,
    table: {
      action: {
        filterCondition: (employee: Employee) => !employee.countBusses || employee.countBusses === 0
      }
    },
    formData: {
      control: {
        readMode: true
      }
    }
  }, Employee.new());

  bussScheduleTable = BussSchedule.tableData({
    xeScreen: this.screen,
    formData: {
      action: {
        postCancel: (entity) => {
          console.log("cancelled");
        }
      }
    }
  });

  ngAfterViewInit(): void {
    this.subscriptions.push(
      CommonUpdateService.instance.findAll<Company>(Company).subscribe(
        companies => this.companySelectItems = companies.map(c => new SelectItem<Company>(c.companyName, c.companyId)),
        error => Notifier.httpErrorResponse(error)
      )
    );
  }

}
