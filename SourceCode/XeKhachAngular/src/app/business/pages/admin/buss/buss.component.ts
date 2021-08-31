import {AfterViewInit, Component, Input} from '@angular/core';
import {XeTableData} from "../../../../framework/model/XeTableData";
import {Buss} from "../../../entities/Buss";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {Company} from "../../../entities/Company";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {Employee} from "../../../entities/Employee";
import {BussEmployee} from "../../../entities/BussEmployee";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {BussSchedule} from "../../../entities/BussSchedule";
import {EditOnRow, InputTemplate} from "../../../../framework/model/EnumStatus";
import {Path} from "../../../entities/Path";
import {PathPoint} from "../../../entities/PathPoint";
import {BussSchedulePoint} from "../../../entities/BussSchedulePoint";

@Component({
  selector: 'xe-buss',
  styles: [],
  templateUrl: 'buss.component.html',
})
export class BussComponent extends FormAbstract implements AfterViewInit {

  ngAfterViewInit() {
    super.ngAfterViewInit();
    console.log(AuthUtil.instance.flatRoles);
  }

  @Input()  myCompany: Company = AuthUtil.instance.user?.employee?.company;

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

  filterBussCondition(buss: Buss) {
    return this.auth.hasCaller ? true : buss.bussEmployees.filter(e => e.employeeId === this.auth.employeeId).length > 0;
  }

  bussTable = Buss.tableData({
    external: {
      updateCriteriaTableOnSelect: () => [this.bussEmployeeTable, this.bussScheduleTable]
    },
    xeScreen: this.screen,
    table: {
      mode: {
        readonly: !this.auth.hasBussAdmin
      },
      action: {
        filters: {
          filterSingle: (buss: Buss) => this.filterBussCondition(buss)
        }
      },
      selectBasicColumns: [
        'bussType.profileImageUrl', 'bussType.bussTypeName', 'bussLicense'
      ],
      basicColumns: [
        {field: {name: 'bussType.totalSeats'}, action: {screen: this.screens.bussScheme}},
        {field: {name: 'totalBussEmployees'}, action: {screen: this.screens.bussEmployees}},
        {field: {name: 'totalSchedules'}, action: {screen: this.screens.schedules}}
      ]
    }
  }, Buss.new({company: this.myCompany}));

  bussEmployeeTable: XeTableData<BussEmployee> = BussEmployee.tableData({
    external: {
      lookUpScreen: this.screens.employeeSelection
    },
    table: {
      mode: {
        readonly: !this.auth.hasBussAdmin
      }
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

  pathTable = Path.tableData({});
  bussSchedulePointTable = BussSchedulePoint.tableData({
    xeScreen: this.screen,
    table: {
      customData: () => this.bussScheduleTable.formData.share.entity.sortedBussSchedulePoints,
      mode: {
        readonly: true,
        hideSelectColumn: true
      },
      action: {
        editOnRow: EditOnRow.onClick
      }
    }
  });
  pathPointSelectionTable = PathPoint.tableData({
    table: {
      customData: () => this.bussScheduleTable.formData.share.entity.path.pathPoints,
    },
    formData: {}
  });
  pathPointInput = InputTemplate.pathPoint._tableData(this.pathPointSelectionTable);
  bussScheduleTable = BussSchedule.tableData({
    xeScreen: this.screen,
    table: {
      mode: {
        readonly: !this.auth.hasBussAdmin
      },
      selectBasicColumns: [
        'scheduleUnitPrice', 'startPoint', 'endPoint', 'workingDays'
      ],
      basicColumns: [
        {
          field: {name: 'totalBussSchedulePoints'},
          action: {screen: this.screens.scheduleMiddlePoints}
        }
      ]
    },
    formData: {
      fields: [
        {}, {}, {}, {},
        {
          name: 'path', template: InputTemplate.path._tableData(this.pathTable),
          colSpan: 2,
          action: {
            preChange: (currentPath: Path, comingPath: Path) => {
              const bussSchedule = this.bussScheduleTable.formData.share.entity;
              if (currentPath && currentPath.pathId !== comingPath.pathId) {
                bussSchedule.startPoint = undefined;
                bussSchedule.startPointCompanyId = 0;
                bussSchedule.startPointPathId = 0;
                bussSchedule.startPointPathPointId = 0;
                bussSchedule.startPointLocationId = 0;
                bussSchedule.endPoint = undefined;
                bussSchedule.endPointCompanyId = 0;
                bussSchedule.endPointPathId = 0;
                bussSchedule.endPointPathPointId = 0;
                bussSchedule.endPointLocationId = 0;
              }
            }
          }
        },
        {name: "startPoint", template: this.pathPointInput, required: true},
        {name: "endPoint", template: this.pathPointInput, required: true},
      ]
    }
  });

}

