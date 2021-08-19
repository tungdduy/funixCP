// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {BussPoint} from "./BussPoint";
import {BussSchedule} from "./BussSchedule";
import {Company} from "./Company";
import {Location} from "./Location";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussSchedulePoint extends XeEntity {
    static className = 'BussSchedulePoint';
    static camelName = 'bussSchedulePoint';
    static otherMainIdNames = ['bussPointId', 'bussScheduleId'];
    static mainIdName = 'bussSchedulePointId';
    static pkMapFieldNames = ['bussPoint', 'bussSchedule'];
    bussScheduleId: number;
    bussSchedulePointId: number;
    locationId: number;
    bussTypeId: number;
    bussId: number;
    companyId: number;
    bussPointId: number;
    bussPoint: BussPoint;
    bussSchedule: BussSchedule;
    priceToEndPoint: number;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussSchedulePoint: BussSchedulePoint): EntityIdentifier<BussSchedulePoint> => ({
    entity: bussSchedulePoint,
    clazz: BussSchedulePoint,
    idFields: () => [
      {name: "bussSchedulePointId", value: bussSchedulePoint.bussSchedulePointId},
      {name: "bussPoint.bussPointId", value: bussSchedulePoint.bussPoint?.bussPointId},
      {name: "bussPoint.company.companyId", value: bussSchedulePoint.bussPoint?.company?.companyId},
      {name: "bussPoint.location.locationId", value: bussSchedulePoint.bussPoint?.location?.locationId},
      {name: "bussSchedule.bussScheduleId", value: bussSchedulePoint.bussSchedule?.bussScheduleId},
      {name: "bussSchedule.buss.bussId", value: bussSchedulePoint.bussSchedule?.buss?.bussId},
      {name: "bussSchedule.buss.bussType.bussTypeId", value: bussSchedulePoint.bussSchedule?.buss?.bussType?.bussTypeId},
      {name: "bussSchedule.buss.company.companyId", value: bussSchedulePoint.bussSchedule?.buss?.company?.companyId}
    ]
  })

  static new(option = {}) {
    const bussSchedulePoint = new BussSchedulePoint();
    bussSchedulePoint.bussPoint = new BussPoint();
    bussSchedulePoint.bussPoint.company = new Company();
    bussSchedulePoint.bussPoint.location = new Location();
    bussSchedulePoint.bussSchedule = new BussSchedule();
    bussSchedulePoint.bussSchedule.buss = new Buss();
    bussSchedulePoint.bussSchedule.buss.bussType = new BussType();
    bussSchedulePoint.bussSchedule.buss.company = new Company();
    ObjectUtil.assignEntity(option, bussSchedulePoint);
    return bussSchedulePoint;
  }

  static tableData = (option: XeTableData<BussSchedulePoint> = {}, bussSchedulePoint: BussSchedulePoint = BussSchedulePoint.new()): XeTableData<BussSchedulePoint> => {
    const table = BussSchedulePoint._bussSchedulePointTable(bussSchedulePoint);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussSchedulePointTable = (bussSchedulePoint: BussSchedulePoint): XeTableData<BussSchedulePoint> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}

