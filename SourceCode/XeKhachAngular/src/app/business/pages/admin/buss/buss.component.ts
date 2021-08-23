import {AfterViewInit, Component} from '@angular/core';
import {XeTableData} from "../../../../framework/model/XeTableData";
import {Buss} from "../../../entities/Buss";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {Company} from "../../../entities/Company";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {Employee} from "../../../entities/Employee";
import {BussEmployee} from "../../../entities/BussEmployee";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {BussSchedule} from "../../../entities/BussSchedule";
import {BussType} from "../../../entities/BussType";
import {InputTemplate} from "../../../../framework/model/EnumStatus";
import {Path} from "../../../entities/Path";
import {PathPoint} from "../../../entities/PathPoint";
import {BussTypeComponent} from "../buss-type/buss-type.component";

@Component({
  selector: 'xe-buss',
  styles: [],
  templateUrl: 'buss.component.html',
})
export class BussComponent extends FormAbstract implements AfterViewInit {
  myCompany: Company = AuthUtil.instance.user?.employee?.company;

  ngAfterViewInit(): void {
    BussType.catchBussTypes();
  }

  screens = {
    busses: 'busses',
    bussScheme: 'bussScheme',
    bussEmployees: 'bussEmployees',
    employeeSelection: 'employeeSelection',
    scheduleMiddlePoints: 'scheduleMiddlePoints',
    pathPointSelection: 'pathPointSelection',
    schedules: 'schedules'
  };
  screen = new XeScreen({
      home: this.screens.busses,
      homeIcon: 'bus',
      homeTitle: () => `${this.bussTable.formData.share.entity.bussLicense} (${this.bussTable.formData.share.entity.bussDesc})`
  });

  bussTable = Buss.tableData({
    external: {
      updateCriteriaTableOnSelect: () => [this.bussEmployeeTable, this.bussScheduleTable]
    },
    xeScreen: this.screen,
    table: {
      basicColumns: [{}, {}, undefined, {},
        {action: {screen: this.screens.bussScheme}},
        {action: {screen: this.screens.bussEmployees}},
        {action: {screen: this.screens.schedules}}
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
      parent: {tableData: this.bussEmployeeTable}
    },
    xeScreen: this.screen,
    table: {
      mode: {
        readonly: true
      },
      action: {
        filters: {
          filterSingle: (employee: Employee) => !employee.countBusses || employee.countBusses === 0
        }
      }
    },
  }, Employee.new({company: this.myCompany}));

  pathTable = Path.tableData({
    external: {
      updateCriteriaTableOnSelect: () => [this.pathPointTable]
    }
  });

  pathPointTable = PathPoint.tableData({
    xeScreen: this.screen,
    table: {
      mode: {
        hideSelectColumn: true
      },
      customData: () => this.bussScheduleTable.formData.share.entity.middlePoints,
      basicColumns: [undefined]
    }
  });
  pathPointSelectionTable = PathPoint.tableData({
    table: {
      customData: () => this.bussScheduleTable.formData.share.entity.path.pathPoints,
      basicColumns: [undefined]
    }
  });
  pathPointInput = InputTemplate.pathPointSearch._tableData(this.pathPointSelectionTable);

  bussScheduleTable = BussSchedule.tableData({
    xeScreen: this.screen,
    table: {
      basicColumns: [{}, {}, {}, {
        action: {
          screen: this.screens.scheduleMiddlePoints
        }
      }]
    },
    external: {
      updateCriteriaTableOnSelect: () => [this.pathPointTable],
    },
    formData: {
      fields: [
        {}, {}, {}, {},
        {name: 'path', template: InputTemplate.pathSearch._tableData(this.pathTable)},
        {name: "startPoint", template: this.pathPointInput, required: true},
        {name: "endPoint", template: this.pathPointInput, required: true},
      ]
    }
  });

}

