import {Component} from '@angular/core';
import {XeTableData} from "../../../../framework/model/XeTableData";
import {BussType} from "../../../entities/BussType";
import {Buss} from "../../../entities/Buss";
import {Company} from "../../../entities/Company";
import {CommonUpdateService} from "../../../service/common-update.service";
import {SelectItem} from "../../../../framework/model/SelectItem";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {BussSchedule} from "../../../entities/BussSchedule";
import {BussEmployee} from "../../../entities/BussEmployee";
import {Employee} from "../../../entities/Employee";
import {EditOnRow, InputTemplate} from "../../../../framework/model/EnumStatus";
import {PathPoint} from "../../../entities/PathPoint";
import {Path} from "../../../entities/Path";
import {BussSchedulePoint} from "../../../entities/BussSchedulePoint";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {BussComponent} from "../buss/buss.component";

@Component({
  selector: 'xe-buss-type',
  styleUrls: ['buss-type.component.scss'],
  templateUrl: 'buss-type.component.html',
})
export class BussTypeComponent extends FormAbstract {

  screens = {
    bussTypeList: 'bussTypeList',
    bussScheme: 'bussScheme',
    bussList: 'bussList',
    schedules: 'schedules',
    employeeSelection: 'employeeSelection',
    bussEmployees: 'bussEmployees',
    scheduleMiddlePoints: 'scheduleMiddlePoints',
    pathPointSelection: 'pathPointSelection'
  };
  screen = new XeScreen({
    home: this.screens.bussTypeList,
    homeIcon: 'pencil-ruler',
    homeTitle: () => `${this.bussTypeTable.formData.share.entity?.bussTypeName}`
  });

  bussTypeTable: XeTableData<BussType> = BussType.tableData({
    xeScreen: this.screen,
    external: {
      updateCriteriaTableOnSelect: () => [this.bussTable]
    },
    table: {
      selectBasicColumns: [
        'profileImageUrl', 'bussTypeName'
      ],
      basicColumns: [
        {field: {name: 'totalSeats'}, action: {screen: this.screens.bussScheme}},
        {field: {name: 'totalBusses'}, action: {screen: this.screens.bussList}}
      ]
    }
  });

  get companySelectItems$(): Observable<SelectItem<Company>[]> {
    return CommonUpdateService.instance.findAll<Company>(Company.meta).pipe(
      map(companies => companies.map(c => new SelectItem<Company>(c.companyName, c.companyId)))
    );
  }
  bussTable: XeTableData<Buss> = Buss.tableData({
    external: {
      updateCriteriaTableOnSelect: () => [this.bussEmployeeTable,
        this.employeeTable,
        this.bussScheduleTable,
      ]
    },
    xeScreen: this.screen,
    table: {
      selectBasicColumns: ['company.companyName', 'bussLicense'],
      basicColumns: [
        {field: {name: 'totalBussEmployees'}, action: {screen: this.screens.bussEmployees}},
        {field: {name: 'totalSchedules'}, action: {screen: this.screens.schedules}}
      ],
    },
    formData: {
      fields: [
        undefined, {}, {},
        {
          name: "companyId",
          required: true,
          template: InputTemplate.selectOneMenu._selectMenu$(this.companySelectItems$),
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
      parent: {
        tableData: this.bussEmployeeTable
      }
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
  }, Employee.new());

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
        preBack: () => this.bussSchedulePointTable.formData.share.tableComponent?.editOnRow?.toEditingNo(),
        postUpdate: (points) => this.bussScheduleTable.formData.share.entity.sortedBussSchedulePoints = points,
        editOnRow: EditOnRow.onClick
      }
    }
  });
  pathPointSelectionTable = PathPoint.tableData({
    table: {
      selectBasicColumns: ['path', 'pointName', 'location'],
      customData: () => this.bussScheduleTable.formData.share.entity.path.pathPoints,
    },
    formData: {}
  });
  pathPointInput = InputTemplate.pathPoint._tableData(this.pathPointSelectionTable);
  bussScheduleTable = BussSchedule.tableData({
    xeScreen: this.screen,
    table: {
      selectBasicColumns: ['scheduleUnitPrice', 'startPoint', 'endPoint', 'workingDays'],
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
              BussComponent.preChangeBussSchedule(currentPath, comingPath, this.bussScheduleTable);
            },
            postChange: (currentValue, oldValue) => {
              console.log(this.bussScheduleTable.formData.share.entity);
            }
          }
        },
        {name: "startPoint", template: this.pathPointInput, required: true},
        {name: "endPoint", template: this.pathPointInput, required: true},
      ]
    }
  });

}
