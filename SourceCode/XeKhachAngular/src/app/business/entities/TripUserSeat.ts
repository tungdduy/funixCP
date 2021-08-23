// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {XeTableData} from "../../framework/model/XeTableData";
import {TripUser} from "./TripUser";
import {Trip} from "./Trip";
import {User} from "./User";
import {BussSchedule} from "./BussSchedule";
import {Buss} from "./Buss";
import {Path} from "./Path";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {EntityUtil} from "../../framework/util/EntityUtil";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class TripUserSeat extends XeEntity {
    static meta = EntityUtil.metas.TripUserSeat;
    bussScheduleId: number;
    pathId: number;
    tripId: number;
    bussTypeId: number;
    tripUserId: number;
    bussId: number;
    companyId: number;
    userId: number;
    tripUserSeatId: number;
    tripUser: TripUser;
    seatNo: number;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (tripUserSeat: TripUserSeat): EntityIdentifier<TripUserSeat> => ({
    entity: tripUserSeat,
    clazz: TripUserSeat,
    idFields: [
      {name: "tripUserSeatId"},
      {name: "tripUser.tripUserId"},
      {name: "tripUser.trip.tripId"},
      {name: "tripUser.trip.bussSchedule.bussScheduleId"},
      {name: "tripUser.trip.bussSchedule.buss.bussId"},
      {name: "tripUser.trip.bussSchedule.buss.bussType.bussTypeId"},
      {name: "tripUser.trip.bussSchedule.buss.company.companyId"},
      {name: "tripUser.trip.bussSchedule.path.pathId"},
      {name: "tripUser.trip.bussSchedule.path.company.companyId"},
      {name: "tripUser.user.userId"}
    ]
  })

  static new(option = {}) {
    const tripUserSeat = new TripUserSeat();
    tripUserSeat.tripUser = new TripUser();
    tripUserSeat.tripUser.trip = new Trip();
    tripUserSeat.tripUser.trip.bussSchedule = new BussSchedule();
    tripUserSeat.tripUser.trip.bussSchedule.buss = new Buss();
    tripUserSeat.tripUser.trip.bussSchedule.buss.bussType = new BussType();
    tripUserSeat.tripUser.trip.bussSchedule.buss.company = new Company();
    tripUserSeat.tripUser.trip.bussSchedule.path = new Path();
    tripUserSeat.tripUser.trip.bussSchedule.path.company = new Company();
    tripUserSeat.tripUser.user = new User();
    EntityUtil.assignEntity(option, tripUserSeat);
    return tripUserSeat;
  }

  static tableData = (option: XeTableData<TripUserSeat> = {}, tripUserSeat: TripUserSeat = TripUserSeat.new()): XeTableData<TripUserSeat> => {
    const table = TripUserSeat._tripUserSeatTable(tripUserSeat);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _tripUserSeatTable = (tripUserSeat: TripUserSeat): XeTableData<TripUserSeat> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {};
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

