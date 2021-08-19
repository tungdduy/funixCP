// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {BussSchedule} from "./BussSchedule";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {TripUserSeat} from "./TripUserSeat";
import {BussPoint} from "./BussPoint";
import {TripUser} from "./TripUser";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class Trip extends XeEntity {
    static className = 'Trip';
    static camelName = 'trip';
    static otherMainIdNames = ['bussScheduleId'];
    static mainIdName = 'tripId';
    static pkMapFieldNames = ['bussSchedule'];
    bussScheduleId: number;
    tripId: number;
    bussTypeId: number;
    bussId: number;
    companyId: number;
    bussSchedule: BussSchedule;
    allTripUserSeats: TripUserSeat ;
    startPoint: BussPoint ;
    endPoint: BussPoint ;
    tripUsers: TripUser[];
    allTripUserSeatsBussId: number;
    allTripUserSeatsUserId: number;
    allTripUserSeatsBussTypeId: number;
    allTripUserSeatsCompanyId: number;
    allTripUserSeatsTripId: number;
    allTripUserSeatsTripUserId: number;
    allTripUserSeatsTripUserSeatId: number;
    allTripUserSeatsBussScheduleId: number;
    startPointLocationId: number;
    startPointBussPointId: number;
    startPointCompanyId: number;
    endPointBussPointId: number;
    endPointLocationId: number;
    endPointCompanyId: number;
    price: number;
    status;
    startTime;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (trip: Trip): EntityIdentifier<Trip> => ({
    entity: trip,
    clazz: Trip,
    idFields: () => [
      {name: "tripId", value: trip.tripId},
      {name: "bussSchedule.bussScheduleId", value: trip.bussSchedule?.bussScheduleId},
      {name: "bussSchedule.buss.bussId", value: trip.bussSchedule?.buss?.bussId},
      {name: "bussSchedule.buss.bussType.bussTypeId", value: trip.bussSchedule?.buss?.bussType?.bussTypeId},
      {name: "bussSchedule.buss.company.companyId", value: trip.bussSchedule?.buss?.company?.companyId}
    ]
  })

  static new(option = {}) {
    const trip = new Trip();
    trip.bussSchedule = new BussSchedule();
    trip.bussSchedule.buss = new Buss();
    trip.bussSchedule.buss.bussType = new BussType();
    trip.bussSchedule.buss.company = new Company();
    ObjectUtil.assignEntity(option, trip);
    return trip;
  }

  static tableData = (option: XeTableData<Trip> = {}, trip: Trip = Trip.new()): XeTableData<Trip> => {
    const table = Trip._tripTable(trip);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _tripTable = (trip: Trip): XeTableData<Trip> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}

