// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {BussSchedule} from "./BussSchedule";
import {PathPoint} from "./PathPoint";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {Location} from "./Location";
import {Path} from "./Path";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {InputMode, InputTemplate} from "../../framework/model/EnumStatus";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussSchedulePoint extends XeEntity {
    static meta = EntityUtil.metas.BussSchedulePoint;
    static mapFields = EntityUtil.mapFields['BussSchedulePoint'];
    pathPointId: number;
    bussScheduleId: number;
    pathId: number;
    bussSchedulePointId: number;
    locationId: number;
    bussTypeId: number;
    bussId: number;
    companyId: number;
    bussSchedule: BussSchedule;
    pathPoint: PathPoint;
    price: number;
    isDeductPrice: boolean;
    searchText: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussSchedulePoint: BussSchedulePoint): EntityIdentifier<BussSchedulePoint> => ({
    entity: bussSchedulePoint,
    clazz: BussSchedulePoint,
    idFields: [
      {name: "bussSchedulePointId"},
      {name: "bussSchedule.bussScheduleId"},
      {name: "bussSchedule.buss.bussId"},
      {name: "bussSchedule.buss.bussType.bussTypeId"},
      {name: "bussSchedule.buss.company.companyId"},
      {name: "pathPoint.pathPointId"},
      {name: "pathPoint.location.locationId"},
      {name: "pathPoint.path.pathId"},
      {name: "pathPoint.path.company.companyId"}
    ]
  })

  static new(option = {}) {
    const bussSchedulePoint = new BussSchedulePoint();
    bussSchedulePoint.bussSchedule = new BussSchedule();
    bussSchedulePoint.bussSchedule.buss = new Buss();
    bussSchedulePoint.bussSchedule.buss.bussType = new BussType();
    bussSchedulePoint.bussSchedule.buss.company = new Company();
    bussSchedulePoint.pathPoint = new PathPoint();
    bussSchedulePoint.pathPoint.location = new Location();
    bussSchedulePoint.pathPoint.path = new Path();
    bussSchedulePoint.pathPoint.path.company = new Company();
    EntityUtil.assignEntity(option, bussSchedulePoint);
    return bussSchedulePoint;
  }

  static tableData = (option: XeTableData<BussSchedulePoint> = {}, bussSchedulePoint: BussSchedulePoint = BussSchedulePoint.new()): XeTableData<BussSchedulePoint> => {
    const table = BussSchedulePoint._bussSchedulePointTable(bussSchedulePoint);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussSchedulePointTable = (bussSchedulePoint: BussSchedulePoint): XeTableData<BussSchedulePoint> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      table: {
        basicColumns: [
          {
            field: {name: 'price', template: InputTemplate.money, mode: InputMode.inputNoTitle}
          },
          {
            field: {name: 'isDeductPrice', template: InputTemplate.booleanToggle, mode: InputMode.inputNoTitle}
          },
          {/*0*/
            field: {name: 'pathPoint', template: InputTemplate.pathPoint},
          },
          {/*0*/
            field: {name: 'pathPoint.location', template: InputTemplate.location},
          },
        ]
      },
      formData: {
        entityIdentifier: BussSchedulePoint.entityIdentifier(bussSchedulePoint),
        share: {entity: BussSchedulePoint.new()},
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

