// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {Trip} from "./Trip";
import {User} from "./User";
import {BussSchedule} from "./BussSchedule";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {Employee} from "./Employee";
import {TripUserPoint} from "./TripUserPoint";
import {PathPoint} from "./PathPoint";
import {Path} from "./Path";
import {EntityUtil} from "../../framework/util/EntityUtil";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class TripUser extends XeEntity {
    static meta = EntityUtil.metas.TripUser;
    bussScheduleId: number;
    tripId: number;
    bussTypeId: number;
    tripUserId: number;
    bussId: number;
    companyId: number;
    userId: number;
    trip: Trip;
    user: User;
    confirmedBy: Employee ;
    confirmedByUserId: number;
    confirmedByEmployeeId: number;
    confirmedByCompanyId: number;
    phoneNumber: string;
    fullName: string;
    status;
    unitPrice: number;
    totalPrice: number;
    tripUserPointsString: string;
    seatsString: string;
    confirmedDateTime;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
    tripUserPoints: PathPoint[];
    startPoint: PathPoint;
    endPoint: PathPoint;
    totalSeats: number;
    seats: number[];

    static removeSeat(tripUser: TripUser, seatNo: number) {
      tripUser.seats.splice(tripUser.seats.indexOf(seatNo), 1);
      TripUser.updatePrice(tripUser);
    }
    static addSeat(tripUser: TripUser, seatNo: number) {
      tripUser.seats.push(seatNo);
      TripUser.updatePrice(tripUser);
    }
    static updatePrice(tripUser: TripUser) {
      tripUser.totalSeats = tripUser.seats.length;
      tripUser.totalPrice = tripUser.unitPrice * tripUser.totalSeats;
      tripUser.seatsString = tripUser.seats.join(",");
    }
    static clearOrderInfo(tripUser: TripUser) {
      tripUser.totalSeats = 0;
      tripUser.totalPrice = 0;
      tripUser.seats = [];
      tripUser.seatsString = "";
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (tripUser: TripUser): EntityIdentifier<TripUser> => ({
    entity: tripUser,
    clazz: TripUser,
    idFields: [
      {name: "tripUserId"},
      {name: "trip.tripId"},
      {name: "trip.bussSchedule.bussScheduleId"},
      {name: "trip.bussSchedule.buss.bussId"},
      {name: "trip.bussSchedule.buss.bussType.bussTypeId"},
      {name: "trip.bussSchedule.buss.company.companyId"},
      {name: "user.userId"}
    ]
  })

  static new(option = {}) {
    const tripUser = new TripUser();
    tripUser.trip = new Trip();
    tripUser.trip.bussSchedule = new BussSchedule();
    tripUser.trip.bussSchedule.buss = new Buss();
    tripUser.trip.bussSchedule.buss.bussType = new BussType();
    tripUser.trip.bussSchedule.buss.company = new Company();
    tripUser.user = new User();
    EntityUtil.assignEntity(option, tripUser);
    return tripUser;
  }

  static tableData = (option: XeTableData<TripUser> = {}, tripUser: TripUser = TripUser.new()): XeTableData<TripUser> => {
    const table = TripUser._tripUserTable(tripUser);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _tripUserTable = (tripUser: TripUser): XeTableData<TripUser> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      table: {
        basicColumns: [
          // 0
          {
            field: {name: 'startPoint.location.locationName'}, type: "boldString",
            subColumns: [
              {field: {name: 'startPoint.location.locationParentAddresses'}, type: "string"}
            ]
          },
          // 1
          {
            field: {name: 'endPoint.location.locationName'}, type: "string",
            subColumns: [
              {field: {name: 'endPoint.location.locationParentAddresses'}, type: "string"}
            ]
          },
          // 2
          {field: {name: 'user.phoneNumber'}, type: "string"},
          // 3
          {field: {name: 'user.email'}, type: "string"},
        ],
      },
      formData: {
        entityIdentifier: TripUser.entityIdentifier(tripUser),
        share: {
          custom: {
            employee: Employee.new()
          }
        },
        fields: []
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

