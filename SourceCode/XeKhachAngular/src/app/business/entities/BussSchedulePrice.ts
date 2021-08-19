// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {BussSchedule} from "./BussSchedule";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {BussSchedulePoint} from "./BussSchedulePoint";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussSchedulePrice extends XeEntity {
    static className = 'BussSchedulePrice';
    static camelName = 'bussSchedulePrice';
    static otherMainIdNames = ['bussScheduleId'];
    static mainIdName = 'bussSchedulePriceId';
    static pkMapFieldNames = ['bussSchedule'];
    bussScheduleId: number;
    bussSchedulePriceId: number;
    bussTypeId: number;
    bussId: number;
    companyId: number;
    bussSchedule: BussSchedule;
    pointFrom: BussSchedulePoint ;
    pointTo: BussSchedulePoint ;
    pointFromCompanyId: number;
    pointFromBussTypeId: number;
    pointFromBussSchedulePointId: number;
    pointFromBussId: number;
    pointFromBussPointId: number;
    pointFromLocationId: number;
    pointFromBussScheduleId: number;
    pointToLocationId: number;
    pointToBussId: number;
    pointToBussSchedulePointId: number;
    pointToBussScheduleId: number;
    pointToBussTypeId: number;
    pointToBussPointId: number;
    pointToCompanyId: number;
    price: number;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussSchedulePrice: BussSchedulePrice): EntityIdentifier<BussSchedulePrice> => ({
    entity: bussSchedulePrice,
    clazz: BussSchedulePrice,
    idFields: () => [
      {name: "bussSchedulePriceId", value: bussSchedulePrice.bussSchedulePriceId},
      {name: "bussSchedule.bussScheduleId", value: bussSchedulePrice.bussSchedule?.bussScheduleId},
      {name: "bussSchedule.buss.bussId", value: bussSchedulePrice.bussSchedule?.buss?.bussId},
      {name: "bussSchedule.buss.bussType.bussTypeId", value: bussSchedulePrice.bussSchedule?.buss?.bussType?.bussTypeId},
      {name: "bussSchedule.buss.company.companyId", value: bussSchedulePrice.bussSchedule?.buss?.company?.companyId}
    ]
  })

  static new(option = {}) {
    const bussSchedulePrice = new BussSchedulePrice();
    bussSchedulePrice.bussSchedule = new BussSchedule();
    bussSchedulePrice.bussSchedule.buss = new Buss();
    bussSchedulePrice.bussSchedule.buss.bussType = new BussType();
    bussSchedulePrice.bussSchedule.buss.company = new Company();
    ObjectUtil.assignEntity(option, bussSchedulePrice);
    return bussSchedulePrice;
  }

  static tableData = (option: XeTableData<BussSchedulePrice> = {}, bussSchedulePrice: BussSchedulePrice = BussSchedulePrice.new()): XeTableData<BussSchedulePrice> => {
    const table = BussSchedulePrice._bussSchedulePriceTable(bussSchedulePrice);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussSchedulePriceTable = (bussSchedulePrice: BussSchedulePrice): XeTableData<BussSchedulePrice> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}

