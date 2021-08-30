// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {SeatGroup} from "./SeatGroup";
import {SelectItem} from '../../framework/model/SelectItem';
import {CommonUpdateService} from "../service/common-update.service";
import {Notifier} from "../../framework/notify/notify.service";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {TripUser} from "./TripUser";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussType extends XeEntity {
    static meta = EntityUtil.metas.BussType;
    static mapFields = EntityUtil.mapFields['BussType'];
    bussTypeId: number;
    seatGroups: SeatGroup[];
    totalBusses: number;
    bussTypeName: string;
    bussTypeDesc: string;
    profileImageUrl = this.initProfileImage();
// ____________________ ::BODY_SEPARATOR:: ____________________ //
  // START BODY >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  seats: number[];
  totalSeats: number;
  static bussTypes: BussType[] = [];

  static get bussTypeSelectItems$() {
    return CommonUpdateService.instance.findAll<BussType>(BussType.meta)
      .pipe(
        map(val => val.map(type => new SelectItem(type.bussTypeName, type.bussTypeId)))
      );
  }
 // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END BODY
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussType: BussType): EntityIdentifier<BussType> => ({
    entity: bussType,
    clazz: BussType,
    idFields: [
      {name: "bussTypeId"},
    ]
  })

  static new(option = {}) {
    return new BussType();
  }

  static tableData = (option: XeTableData<BussType> = {}, bussType: BussType = BussType.new()): XeTableData<BussType> => {
    const table = BussType._bussTypeTable(bussType);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussTypeTable = (bussType: BussType): XeTableData<BussType> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      table: {
        basicColumns: [
          // 0
          {field: {name: 'profileImageUrl'}, type: "avatar"},
          // 1
          {
            field: {name: 'bussTypeName'}, type: "boldStringRole",
            subColumns: [
              {field: {name: 'bussTypeDesc'}, type: "string", display: {row: {css: 'd-block'}}}

            ]
          },
          // 2
          {field: {name: 'totalSeats'}, type: "iconOption", display: {row: {icon: {iconAfter: "couch"}}}},
          // 3
          {field: {name: 'totalBusses'}, type: "iconOption", display: {row: {icon: {iconAfter: "bus"}}}},
        ],
      },
      formData: {
        entityIdentifier: BussType.entityIdentifier(bussType),
        share: {entity: BussType.new()},
        header: {
          profileImage: {name: 'profileImageUrl'},
          titleField: {name: 'bussTypeName'},
          descField: {name: 'bussTypeDesc'},
        },
        fields: [
          {name: "bussTypeName", required: true},
          {name: "bussTypeDesc", required: true},
        ]
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

