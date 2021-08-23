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
import {InputMode, InputTemplate} from "../../../../framework/model/EnumStatus";
import {PathPoint} from "../../../entities/PathPoint";
import {Path} from "../../../entities/Path";
import {BussSchedulePoint} from "../../../entities/model/BussSchedulePoint";
import {of} from "rxjs";

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
      basicColumns: [{}, {},
        {action: {screen: this.screens.bussScheme}},
        {action: {screen: this.screens.bussList}}
      ]
    }
  });

  companySelectItems: SelectItem<Company>[];
  bussTable: XeTableData<Buss> = Buss.tableData({
    external: {
      updateCriteriaTableOnSelect: () => [this.bussEmployeeTable,
        this.employeeTable,
        this.bussScheduleTable,
      ]
    },
    xeScreen: this.screen,
    table: {
      basicColumns: [undefined, undefined, {}, {}, undefined,
        {action: {screen: this.screens.bussEmployees}},
        {action: {screen: this.screens.schedules}}
      ],
    },
    formData: {
      fields: [
        undefined, {}, {},
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

  pathTable = Path.tableData({
  });

  bussSchedulePointTable = BussSchedulePoint.tableData({
    xeScreen: this.screen,
    table: {
      mode: {
        hideSelectColumn: true
      },
      action: {
        editOnRow: () => {
          const schedule = this.bussScheduleTable.formData.share.entity;
          const scheduleIds = this.entityUtil.getAllPossibleId(schedule, this.bussScheduleTable.formData.entityIdentifier);

          const schedulePoints = [];
          schedule.bussSchedulePoints.forEach((point: BussSchedulePoint) => {
            schedulePoints.push({
              bussScheduleId: point.bussScheduleId,
              pathPointId: point.pathPointId || point.pathPoint?.pathPointId,
              price: point.price,
              isDeductPriceFromPreviousPoint: point.isDeductPriceFromPreviousPoint,
              pointOrder: point.pointOrder
            });
          });
          scheduleIds['jsonBussSchedulePoints'] = JSON.stringify(schedulePoints);
          this.updateSingle$(scheduleIds, BussSchedule.meta).subscribe((bussSchedule: BussSchedule) => {
            this.entityUtil.cache(bussSchedule, BussSchedule.meta);
            this.bussSchedulePointTable.formData.share.tableSource.data = bussSchedule.bussSchedulePoints;
            Notifier.success(this.xeLabel.SAVED_SUCCESSFULLY);
          });
        }
      },
      customData: () => this.bussScheduleTable.formData.share.entity.bussSchedulePoints,
    }
  });
  pathPointSelectionTable = PathPoint.tableData({
    table: {
      customData: () => this.bussScheduleTable.formData.share.entity.path.pathPoints,
      basicColumns: [undefined]
    },
    formData: {}
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
    formData: {
      fields: [
        {}, {}, {}, {},
        {
          name: 'path', template: InputTemplate.pathSearch._tableData(this.pathTable),
          colSpan: 2,
          action: {
            preChange: (path: Path) => {
              const bussSchedule = this.bussScheduleTable.formData.share.entity;
              if (bussSchedule.path && bussSchedule.path.pathId !== path.pathId) {
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


  ngAfterViewInit(): void {
    this.subscriptions.push(
      CommonUpdateService.instance.findAll<Company>(Company.meta).subscribe(
        companies => this.companySelectItems = companies.map(c => new SelectItem<Company>(c.companyName, c.companyId)),
        error => Notifier.httpErrorResponse(error)
      )
    );
  }

}
