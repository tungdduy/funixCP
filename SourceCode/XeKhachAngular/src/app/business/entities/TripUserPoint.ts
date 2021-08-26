// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {PathPoint} from "./PathPoint";
import {TripUser} from "./TripUser";
import {Location} from "./Location";
import {Path} from "./Path";
import {Company} from "./Company";
import {Trip} from "./Trip";
import {User} from "./User";
import {BussSchedule} from "./BussSchedule";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class TripUserPoint extends XeEntity {
    static meta = EntityUtil.metas.TripUserPoint;
    pathPointId: number;
    pathId: number;
    bussScheduleId: number;
    tripId: number;
    locationId: number;
    tripUserPointId: number;
    bussTypeId: number;
    tripUserId: number;
    bussId: number;
    companyId: number;
    userId: number;
    pathPoint: PathPoint;
    tripUser: TripUser;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (tripUserPoint: TripUserPoint): EntityIdentifier<TripUserPoint> => ({
    entity: tripUserPoint,
    clazz: TripUserPoint,
    idFields: [
      {name: "tripUserPointId"},
      {name: "pathPoint.pathPointId"},
      {name: "pathPoint.location.locationId"},
      {name: "pathPoint.path.pathId"},
      {name: "pathPoint.path.company.companyId"},
      {name: "tripUser.tripUserId"},
      {name: "tripUser.trip.tripId"},
      {name: "tripUser.trip.bussSchedule.bussScheduleId"},
      {name: "tripUser.trip.bussSchedule.buss.bussId"},
      {name: "tripUser.trip.bussSchedule.buss.bussType.bussTypeId"},
      {name: "tripUser.trip.bussSchedule.buss.company.companyId"},
      {name: "tripUser.user.userId"}
    ]
  })

  static new(option = {}) {
    const tripUserPoint = new TripUserPoint();
    tripUserPoint.pathPoint = new PathPoint();
    tripUserPoint.pathPoint.location = new Location();
    tripUserPoint.pathPoint.path = new Path();
    tripUserPoint.pathPoint.path.company = new Company();
    tripUserPoint.tripUser = new TripUser();
    tripUserPoint.tripUser.trip = new Trip();
    tripUserPoint.tripUser.trip.bussSchedule = new BussSchedule();
    tripUserPoint.tripUser.trip.bussSchedule.buss = new Buss();
    tripUserPoint.tripUser.trip.bussSchedule.buss.bussType = new BussType();
    tripUserPoint.tripUser.trip.bussSchedule.buss.company = new Company();
    tripUserPoint.tripUser.user = new User();
    EntityUtil.assignEntity(option, tripUserPoint);
    return tripUserPoint;
  }

  static tableData = (option: XeTableData<TripUserPoint> = {}, tripUserPoint: TripUserPoint = TripUserPoint.new()): XeTableData<TripUserPoint> => {
    const table = TripUserPoint._tripUserPointTable(tripUserPoint);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _tripUserPointTable = (tripUserPoint: TripUserPoint): XeTableData<TripUserPoint> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

