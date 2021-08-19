// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {BussPoint} from "./BussPoint";
import {BussSchedulePoint} from "./BussSchedulePoint";
import {InputTemplate} from "../../framework/model/EnumStatus";
import {XeDatePipe} from "../../framework/components/pipes/date.pipe";
import {XeTimePipe} from "../../framework/components/pipes/time.pipe";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussSchedule extends XeEntity {
    static className = 'BussSchedule';
    static camelName = 'bussSchedule';
    static otherMainIdNames = ['bussId'];
    static mainIdName = 'bussScheduleId';
    static pkMapFieldNames = ['buss'];
    bussScheduleId: number;
    bussTypeId: number;
    bussId: number;
    companyId: number;
    buss: Buss;
    startPoint: BussPoint ;
    middlePoints: BussSchedulePoint[];
    endPoint: BussPoint ;
    startPointLocationId: number;
    startPointBussPointId: number;
    startPointCompanyId: number;
    endPointBussPointId: number;
    endPointLocationId: number;
    endPointCompanyId: number;
    price: number;
    launchTime;
    effectiveDateFrom;
    workingDays: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussSchedule: BussSchedule): EntityIdentifier<BussSchedule> => ({
    entity: bussSchedule,
    clazz: BussSchedule,
    idFields: () => [
      {name: "bussScheduleId", value: bussSchedule.bussScheduleId},
      {name: "buss.bussId", value: bussSchedule.buss?.bussId},
      {name: "buss.bussType.bussTypeId", value: bussSchedule.buss?.bussType?.bussTypeId},
      {name: "buss.company.companyId", value: bussSchedule.buss?.company?.companyId}
    ]
  })

  static new(option = {}) {
    const bussSchedule = new BussSchedule();
    bussSchedule.buss = new Buss();
    bussSchedule.buss.bussType = new BussType();
    bussSchedule.buss.company = new Company();
    ObjectUtil.assignEntity(option, bussSchedule);
    return bussSchedule;
  }

  static tableData = (option: XeTableData<BussSchedule> = {}, bussSchedule: BussSchedule = BussSchedule.new()): XeTableData<BussSchedule> => {
    const table = BussSchedule._bussScheduleTable(bussSchedule);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussScheduleTable = (bussSchedule: BussSchedule): XeTableData<BussSchedule> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    display: {
      fullScreenForm: true
    },
    table: {
      basicColumns: [
        /*0*/ {field: {name: 'price', template: InputTemplate.money}, type: "string"},
        /*1*/ {field: {name: 'startPoint.bussPointName'}, type: "string"},
        /*2*/ {field: {name: 'endPoint.bussPointName'}, type: "string"},
        /*3*/ {
          field: {name: 'effectiveDateFrom', template: InputTemplate.date}, type: "string",
          subColumns: [
            {
              field: {name: 'launchTime', template: InputTemplate.time},
              type: "string",
              display: {row: {inline: false}}
            },
          ]
        },
        /*4*/ {field: {name: 'sunday'}, type: "string"},
        /*5*/ {field: {name: 'monday'}, type: "string"},
        /*6*/ {field: {name: 'tuesday'}, type: "string"},
        /*6*/ {field: {name: 'wednesday'}, type: "string"},
        /*6*/ {field: {name: 'friday'}, type: "string"},
        /*6*/ {field: {name: 'saturday'}, type: "string"},
      ]
    },
    formData: {
      entityIdentifier: BussSchedule.entityIdentifier(bussSchedule),
      share: {entity: BussSchedule.new()},
      display: {
        columnNumber: 3
      },
      fields: [
        {name: "price",  template: InputTemplate.money, required: true},
        {name: "launchTime", template: InputTemplate.time, required: true},
        {name: "effectiveDateFrom", template: InputTemplate.date, required: false},
        {name: "workingDays", template: InputTemplate.weekDays, required: false},
      ]
    }
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}

