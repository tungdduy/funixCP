import {AfterViewInit, Component} from '@angular/core';
import {XeTableData} from "../../../../framework/model/XeTableData";
import {Buss} from "../../../entities/Buss";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {Company} from "../../../entities/Company";
import {BussTypeUtil} from "../../../entities/BussType";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {Employee} from "../../../entities/Employee";
import {BussEmployee} from "../../../entities/BussEmployee";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {BussSchedule} from "../../../entities/BussSchedule";

@Component({
  selector: 'xe-buss',
  styles: [],
  templateUrl: 'buss.component.html',
})
export class BussComponent extends FormAbstract implements AfterViewInit {
  myCompany: Company = AuthUtil.instance.user?.employee?.company;

  ngAfterViewInit(): void {
    BussTypeUtil.catchBussTypes();
  }

  screens = {
    busses: 'busses',
    bussScheme: 'bussScheme',
    bussEmployees: 'bussEmployees',
    employeeSelection: 'employeeSelection',
    bussSchedules: "bussSchedules"
  };
  screen = new XeScreen({
      home: this.screens.busses,
      homeIcon: 'bus',
      homeTitle: () => `${this.bussTable.formData.share.entity.bussLicense} (${this.bussTable.formData.share.entity.bussDesc})`
  });

  bussTable = Buss.tableData({
    external: {
      updateCriteriaTableOnSelect: () => [this.bussEmployeeTable]
    },
    xeScreen: this.screen,
    table: {
      basicColumns: [{}, {}, undefined, {},
        {action: {screen: this.screens.bussScheme}},
        {action: {screen: this.screens.bussEmployees}},
        {action: {screen: this.screens.bussSchedules}}
      ]
    }
  }, Buss.new({company: this.myCompany}));

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
  }, Employee.new({company: this.myCompany}));

  bussScheduleTable: XeTableData<BussSchedule> = BussSchedule.tableData({
    xeScreen: this.screen,
    table: {
      action: {
        filterCondition: (employee: Employee) => !employee.countBusses || employee.countBusses === 0
      }
    },
    formData: {
      control: {readMode: true}
    }
  });

  gotoSelectEmployee() {
    console.log(this.bussEmployeeTable.formData.entityIdentifier.entity);
  }
}

