// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {BussType} from "./BussType";
import {ServiceResult} from "../service/service-result";
import {XeLabel} from "../i18n";
import {EntityUtil} from "../../framework/util/EntityUtil";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class SeatGroup extends XeEntity {
    static meta = EntityUtil.metas.SeatGroup;
    static mapFields = EntityUtil.mapFields['SeatGroup'];
    seatGroupId: number;
    bussTypeId: number;
    bussType: BussType;
    seatGroupOrder: number;
    seatGroupName: string;
    seatGroupDesc: string;
    totalSeats: number;
    seatFrom: number;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
  seats: number[];
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (seatGroup: SeatGroup): EntityIdentifier<SeatGroup> => ({
    entity: seatGroup,
    clazz: SeatGroup,
    idFields: [
      {name: "seatGroupId"},
      {name: "bussType.bussTypeId"}
    ]
  })

  static new(option = {}) {
    const seatGroup = new SeatGroup();
    seatGroup.bussType = new BussType();
    EntityUtil.assignEntity(option, seatGroup);
    return seatGroup;
  }

  static tableData = (option: XeTableData<SeatGroup> = {}, seatGroup: SeatGroup = SeatGroup.new()): XeTableData<SeatGroup> => {
    const table = SeatGroup._seatGroupTable(seatGroup);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _seatGroupTable = (seatGroup: SeatGroup): XeTableData<SeatGroup> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      formData: {
        action: {
          preSubmit: (sg: SeatGroup): ServiceResult => {
            const result = new ServiceResult();
            if (sg.totalSeats <= 0) {
              return result.error(XeLabel.INVALID_SEAT_RANGE);
            }
            return result.success({});
          }
        },
        entityIdentifier: SeatGroup.entityIdentifier(seatGroup),
        fields: [
          {name: 'seatGroupName', required: true},
          {name: 'seatGroupDesc', required: true},
          {name: 'totalSeats', required: true},
        ]
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

