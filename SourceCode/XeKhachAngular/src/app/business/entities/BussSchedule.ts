// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {Path} from "./Path";
import {PathPoint} from "./PathPoint";
import {InputTemplate} from "../../framework/model/EnumStatus";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {BussSchedulePoint} from "./model/BussSchedulePoint";
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
    startPoint: PathPoint ;
    endPoint: PathPoint ;
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
    price: number;
    launchTime;
    effectiveDateFrom;
    jsonBussSchedulePoints: string;
    workingDays: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
   bussSchedulePoints: BussSchedulePoint[];
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
          /*0*/ {field: {name: 'price', template: InputTemplate.money}, type: "string"},
          /*1*/ {field: {name: 'startPoint', template: InputTemplate.pathPointSearch}},
          /*2*/ {field: {name: 'endPoint', template: InputTemplate.pathPointSearch}},
          /*3*/ {
            field: {name: 'totalMiddlePoints'},
            type: "iconOption",
            display: {row: {icon: {iconAfter: "map-marker-alt"}}}
          },
          /*4*/ {
            field: {name: 'effectiveDateFrom', template: InputTemplate.date}, type: "string",
            subColumns: [
              {
                field: {name: 'launchTime', template: InputTemplate.time},
                type: "string",
                display: {row: {inline: false}}
              },
            ]
          },
          /*5*/ {field: {name: 'workingDays', template: InputTemplate.weekDays}, type: "string"},
        ]
      },
      formData: {
        entityIdentifier: BussSchedule.entityIdentifier(bussSchedule),
        share: {entity: BussSchedule.new()},
        display: {
          columnNumber: 3
        },
        fields: [
          {name: "price", template: InputTemplate.money, required: true},
          {name: "launchTime", template: InputTemplate.time, required: true},
          {name: "effectiveDateFrom", template: InputTemplate.date, required: true},
          {name: "workingDays", template: InputTemplate.weekDays, required: true},
        ]
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

