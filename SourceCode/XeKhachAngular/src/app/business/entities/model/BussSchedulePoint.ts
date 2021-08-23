import {BussSchedule} from "../BussSchedule";
import {EntityIdentifier} from "../../../framework/model/XeFormData";
import {EntityUtil} from "../../../framework/util/EntityUtil";
import {XeTableData} from "../../../framework/model/XeTableData";
import {InputMode, InputTemplate} from "../../../framework/model/EnumStatus";
import {XeEntity} from "../XeEntity";
import {PathPoint} from "../PathPoint";

export class BussSchedulePoint extends XeEntity {
  bussSchedule: BussSchedule;
  bussScheduleId: number;
  pathPoint: PathPoint;
  pathPointId: number;

  isDeductPriceFromPreviousPoint: boolean;
  pointOrder: number;
  price: number;


  static meta = EntityUtil.metas.BussSchedulePoint;
  static entityIdentifier = (bussSchedulePoint: BussSchedulePoint): EntityIdentifier<BussSchedulePoint> => ({
    entity: bussSchedulePoint,
    clazz: BussSchedulePoint,
    idFields: [
      {name: "pathPointId"},
      {name: "location.locationId"},
      {name: "path.pathId"},
      {name: "path.company.companyId"}
    ]
  })

  static new(option = {}) {
    const bussSchedulePoint = new BussSchedulePoint();
    bussSchedulePoint.pathPoint = new PathPoint();
    bussSchedulePoint.bussSchedule = new BussSchedule();
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
            field: {name: 'isDeductPriceFromPreviousPoint', template: InputTemplate.booleanToggle, mode: InputMode.inputNoTitle}
          },
          {/*0*/
            field: {name: 'pathPoint', template: InputTemplate.pathPointSearch},
          },
          {
            field: {name: 'pathPoint.location', template: InputTemplate.locationSearch}
          }
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
