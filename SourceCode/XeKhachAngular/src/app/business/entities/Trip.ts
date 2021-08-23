// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {BussSchedule} from "./BussSchedule";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {TripUser} from "./TripUser";
import {Path} from "./Path";
import {EntityUtil} from "../../framework/util/EntityUtil";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class Trip extends XeEntity {
    static meta = EntityUtil.metas.Trip;
    bussScheduleId: number;
    tripId: number;
    bussTypeId: number;
    bussId: number;
    companyId: number;
    bussSchedule: BussSchedule;
    tripUsers: TripUser[];
    price: number;
    status;
    startTime;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (trip: Trip): EntityIdentifier<Trip> => ({
    entity: trip,
    clazz: Trip,
    idFields: [
      {name: "tripId"},
      {name: "bussSchedule.bussScheduleId"},
      {name: "bussSchedule.buss.bussId"},
      {name: "bussSchedule.buss.bussType.bussTypeId"},
      {name: "bussSchedule.buss.company.companyId"}
    ]
  })

  static new(option = {}) {
    const trip = new Trip();
    trip.bussSchedule = new BussSchedule();
    trip.bussSchedule.buss = new Buss();
    trip.bussSchedule.buss.bussType = new BussType();
    trip.bussSchedule.buss.company = new Company();
    EntityUtil.assignEntity(option, trip);
    return trip;
  }

  static tableData = (option: XeTableData<Trip> = {}, trip: Trip = Trip.new()): XeTableData<Trip> => {
    const table = Trip._tripTable(trip);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _tripTable = (trip: Trip): XeTableData<Trip> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {};
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

