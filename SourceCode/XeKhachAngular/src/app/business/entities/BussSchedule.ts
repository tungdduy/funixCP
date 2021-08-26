// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {XeTableData} from "../../framework/model/XeTableData";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {Path} from "./Path";
import {BussSchedulePoint} from "./BussSchedulePoint";
import {PathPoint} from "./PathPoint";
import {InputMode, InputTemplate} from "../../framework/model/EnumStatus";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {XeTimePipe} from "../../framework/components/pipes/time.pipe";
import {Trip} from "./Trip";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussSchedule extends XeEntity {
    static meta = EntityUtil.metas.BussSchedule;
    bussScheduleId: number;
    bussTypeId: number;
    bussId: number;
    companyId: number;
    buss: Buss;
    path: Path ;
    bussSchedulePoints: BussSchedulePoint[];
    startPoint: PathPoint ;
    endPoint: PathPoint ;
    totalBussSchedulePoints: number;
    pathPathId: number;
    pathCompanyId: number;
    startPointLocationId: number;
    startPointPathId: number;
    startPointPathPointId: number;
    startPointCompanyId: number;
    endPointPathPointId: number;
    endPointLocationId: number;
    endPointPathId: number;
    endPointCompanyId: number;
    scheduleUnitPrice: number;
    launchTime;
    effectiveDateFrom;
    workingDays: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
  preparedTrip: Trip;
  sortedBussSchedulePoints: BussSchedulePoint[];
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussSchedule: BussSchedule): EntityIdentifier<BussSchedule> => ({
    entity: bussSchedule,
    clazz: BussSchedule,
    idFields: [
      {name: "bussScheduleId"},
      {name: "buss.bussId"},
      {name: "buss.bussType.bussTypeId"},
      {name: "buss.company.companyId"}
    ]
  })

  static new(option = {}) {
    const bussSchedule = new BussSchedule();
    bussSchedule.buss = new Buss();
    bussSchedule.buss.bussType = new BussType();
    bussSchedule.buss.company = new Company();
    EntityUtil.assignEntity(option, bussSchedule);
    return bussSchedule;
  }

  static tableData = (option: XeTableData<BussSchedule> = {}, bussSchedule: BussSchedule = BussSchedule.new()): XeTableData<BussSchedule> => {
    const table = BussSchedule._bussScheduleTable(bussSchedule);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussScheduleTable = (bussSchedule: BussSchedule): XeTableData<BussSchedule> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      display: {
        fullScreenForm: true
      },
      table: {
        basicColumns: [
          {field: {name: 'scheduleUnitPrice', template: InputTemplate.money}, type: "string"},

          {
            field: {name: 'startPoint', template: InputTemplate.pathPoint},
            subColumns: [
              {field: {name: 'startPoint.location', template: InputTemplate.location}}
            ]
          },
          {
            field: {name: 'endPoint', template: InputTemplate.pathPoint},
            subColumns: [
              {field: {name: 'startPoint.location', template: InputTemplate.location}}
            ]
          },
          {
            field: {name: 'totalBussSchedulePoints'},
            type: "iconOption",
            display: {row: {icon: {iconAfter: "map-marker-alt"}}}
          },
          {
            field: {name: 'effectiveDateFrom', template: InputTemplate.date}, type: "string",
            subColumns: [
              {
                field: {name: 'launchTime', template: InputTemplate.time},
                type: "string",
                display: {row: {inline: false}}
              },
            ]
          },
          {field: {name: 'workingDays', template: InputTemplate.weekDays}, type: "string"},
          {
            field: {name: 'buss.company.companyName', template: InputTemplate.shortInput},
            subColumns: [
              {
                field: {name: 'preparedTrip.preparedTripUser.unitPrice', template: InputTemplate.money},
                display: {row: {css: 'text-x-large text-danger d-block p-2'}}
              },
              {field: {name: 'launchTime', template: InputTemplate.time}}
            ]
          },
          {
            field: {name: 'preparedTrip.preparedTripUser.startPoint', template: InputTemplate.pathPoint},
            subColumns: [
              {
                field: {name: 'preparedTrip.preparedTripUser.startPoint.location', template: InputTemplate.location},
                display: {header: {silence: true}}
              }
            ]
          },
          {
            field: {name: 'preparedTrip.preparedTripUser.endPoint', template: InputTemplate.pathPoint},
            subColumns: [
              {
                field: {name: 'preparedTrip.preparedTripUser.endPoint.location', template: InputTemplate.location},
                display: {header: {silence: true}}
              }
            ]
          },
          {
            field: {name: 'preparedTrip.preparedTripUser.totalTripUserPoints'},
            type: "iconOption",
            display: {row: {icon: {iconAfter: "map-marker-alt"}}},
            subColumns: [{
              field: {name: 'preparedTrip.totalAvailableSeats', attachInlines: ['preparedTrip.totalSeats']},
              type: "iconOption",
              display: {row: {icon: {iconAfter: "couch"}}},
            }]
          },
        ],
      },
      formData: {
        entityIdentifier: BussSchedule.entityIdentifier(bussSchedule),
        share: {entity: BussSchedule.new()},
        display: {
          columnNumber: 3
        },
        fields: [
          {name: "scheduleUnitPrice", template: InputTemplate.money, required: true},
          {
            name: "launchTime",
            template: InputTemplate.time._observable(XeTimePipe.autoInputObservable),
            mode: InputMode.selectOnly,
            required: true
          },
          {name: "effectiveDateFrom", template: InputTemplate.date, required: true},
          {name: "workingDays", template: InputTemplate.weekDays, required: true},
        ]
      }
    } as XeTableData<BussSchedule>;
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

