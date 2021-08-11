import {SelectItem} from '../../framework/model/SelectItem';
import {XeEntity} from "./XeEntity";
import {CommonUpdateService} from "../service/common-update.service";
import {Notifier} from "../../framework/notify/notify.service";
import {EntityIdentifier} from "../../framework/model/XeFormData";

export enum BussTypeEnum {
  BUSS_48 = "BUSS_48",
  BUSS_27 = "BUSS_27"
}

export interface TypeOfBuss {
  name: BussTypeEnum;
  seatGroups: SeatGroup[];
}

export interface SeatGroup {
  groupName: string;
  note: string;
  seatRange: { start: number, end: number };
  seats?: number[];
}

export const BussTypeDefiner: TypeOfBuss[] = [
  {
    name: BussTypeEnum.BUSS_48,
    seatGroups: [
      {groupName: 'BUSS_48_N1', note: 'header_is_1', seatRange: {start: 1, end: 12}},
      {groupName: 'BUSS_48_T1', note: 'header_is_13', seatRange: {start: 13, end: 24}},
      {groupName: 'BUSS_48_N2', note: 'header_is_25', seatRange: {start: 25, end: 36}},
      {groupName: 'BUSS_48_T2', note: 'header_is_37', seatRange: {start: 37, end: 48}},
    ]
  },
  {
    name: BussTypeEnum.BUSS_27,
    seatGroups: [
      {groupName: 'BUSS_27_N', note: 'header_is_1', seatRange: {start: 1, end: 14}},
      {groupName: 'BUSS_27_T', note: 'header_is_15', seatRange: {start: 15, end: 27}},
    ]
  }
];

export class BussTypeUtil {
  static bussTypes: BussType[];
  static catchBussTypes() {
    CommonUpdateService.instance.getAll<BussType>(BussType.identifier.idForSearchAll, "BussType").subscribe(
      bussTypes => {
        this.bussTypes = bussTypes;
      },
      error => Notifier.httpErrorResponse(error)
    );
  }

  static _bussTypeCodeSelectItems: SelectItem<BussTypeEnum>[];
  static get bussTypeCodeSelectItems(): SelectItem<BussTypeEnum>[] {
    if (!BussTypeUtil._bussTypeCodeSelectItems) {
      const allBussTypes: SelectItem<BussTypeEnum>[] = [];
      Object.keys(BussTypeEnum).forEach(type => {
        allBussTypes.push(new SelectItem(type, type));
      });
      BussTypeUtil._bussTypeCodeSelectItems = allBussTypes;
    }
    return BussTypeUtil._bussTypeCodeSelectItems;
  }

  static _bussTypeIdSelectItems: SelectItem<number>[];
  static get bussTypeIdSelectItems(): SelectItem<number>[] {
    if (!BussTypeUtil._bussTypeIdSelectItems) {
      const allBussTypes: SelectItem<number>[] = [];
      this.bussTypes.forEach(type => {
        allBussTypes.push(new SelectItem(type.bussTypeCode, type.bussTypeId));
      });
      BussTypeUtil._bussTypeIdSelectItems = allBussTypes;
    }
    return BussTypeUtil._bussTypeIdSelectItems;
  }
}

export class BussType extends XeEntity {

  bussTypeId: number;

  bussTypeName: string;
  bussTypeDesc: string;
  bussTypeCode: string;

  totalSeats: number;
  totalBusses: number;

  profileImageUrl = this.initProfileImage();

  static identifier: EntityIdentifier = {
    className: "BussType",
    idFields: () => [
      {name: "bussTypeId", value: 0}
    ],
    idForSearchAll: {bussTypeId: 0}
  };

}
