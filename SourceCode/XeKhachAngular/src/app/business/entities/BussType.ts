// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {SeatGroup} from "./SeatGroup";
import {SelectItem} from '../../framework/model/SelectItem';
import {CommonUpdateService} from "../service/common-update.service";
import {Notifier} from "../../framework/notify/notify.service";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
export class BussTypeUtil {
  static bussTypes: BussType[] = [];

  static _bussTypeSelectItem: SelectItem<number>[];

  static get bussTypeSelectItem(): SelectItem<number>[] {
    if (!BussTypeUtil._bussTypeSelectItem) {
      const allBussTypes: SelectItem<number>[] = [];
      this.bussTypes.forEach(type => {
        allBussTypes.push(new SelectItem(type.bussTypeName, type.bussTypeId));
      });
      BussTypeUtil._bussTypeSelectItem = allBussTypes;
    }
    return BussTypeUtil._bussTypeSelectItem;
  }

  static catchBussTypes() {
    CommonUpdateService.instance.findAll<BussType>(BussType).subscribe(
      bussTypes => {
        this.bussTypes = bussTypes;
      },
      error => Notifier.httpErrorResponse(error)
    );
  }
}
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussType extends XeEntity {
    static className = 'BussType';
    static camelName = 'bussType';
    static otherMainIdNames = [];
    static mainIdName = 'bussTypeId';
    static pkMapFieldNames = [];
    bussTypeId: number;
    seatGroups: SeatGroup[];
    totalBusses: number;
    bussTypeCode: string;
    bussTypeName: string;
    bussTypeDesc: string;
    profileImageUrl = this.initProfileImage();
// ____________________ ::BODY_SEPARATOR:: ____________________ //
  totalSeats: number;
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussType: BussType): EntityIdentifier<BussType> => ({
    entity: bussType,
    clazz: BussType,
    idFields: () => [
      {name: "bussTypeId", value: bussType.bussTypeId},
    ]
  })

  static new(option = {}) {
    return new BussType();
  }

  static tableData = (option: XeTableData<BussType> = {}, bussType: BussType = BussType.new()): XeTableData<BussType> => {
    const table = BussType._bussTypeTable(bussType);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussTypeTable = (bussType: BussType): XeTableData<BussType> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    table: {
      basicColumns: [
        // 0
        {field: {name: 'profileImageUrl'}, type: "avatar"},
        // 1
        {field: {name: 'bussTypeCode'}, type: "boldStringRole"},
        // 2
        {
          field: {name: 'bussTypeName'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'bussTypeDesc'}, type: "string", display: {row: {css: 'd-block'}}}

          ]
        },
        // 3
        {field: {name: 'totalSeats'}, type: "iconOption", display: {row: {icon: {iconAfter: "couch"}}}},
        // 4
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
        {name: "bussTypeCode", required: true},
        {name: "bussTypeName", required: true},
        {name: "bussTypeDesc", required: true},
        {name: "totalSeats", required: true},
      ]
    }
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}

