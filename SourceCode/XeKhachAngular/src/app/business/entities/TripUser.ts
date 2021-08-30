// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {XeTableData} from "../../framework/model/XeTableData";
import {Trip} from "./Trip";
import {BussSchedule} from "./BussSchedule";
import {Buss} from "./Buss";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {User} from "./User";
import {Employee} from "./Employee";
import {PathPoint} from "./PathPoint";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {InputMode, InputTemplate} from "../../framework/model/EnumStatus";
import {XeDatePipe} from "../../framework/components/pipes/date.pipe";
import {BussSchedulePoint} from './BussSchedulePoint';
import {Xe} from "../../framework/model/Xe";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class TripUser extends XeEntity {
    static meta = EntityUtil.metas.TripUser;
    static mapFields = EntityUtil.mapFields['TripUser'];
    bussScheduleId: number;
    tripId: number;
    bussTypeId: number;
    tripUserId: number;
    bussId: number;
    companyId: number;
    trip: Trip;
    user: User ;
    confirmedBy: Employee ;
    userUserId: number;
    confirmedByUserId: number;
    confirmedByEmployeeId: number;
    confirmedByCompanyId: number;
    phoneNumber: string;
    fullName: string;
    email: string;
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

  get bussScheduleStartPoint() {
    EntityUtil.fill(this, TripUser.meta);
    return this.trip.bussSchedule.sortedBussSchedulePoints.filter(point =>
      point.pathPointId === this.startPoint.pathPointId
    )[0];
  }
  set bussScheduleStartPoint(point: BussSchedulePoint) {
    this.startPoint = point.pathPoint;
  }

  get bussScheduleEndPoint() {
    EntityUtil.fill(this, TripUser.meta);
    return this.trip.bussSchedule.sortedBussSchedulePoints.filter(point =>
      point.pathPointId === this.endPoint.pathPointId
    )[0];
  }
  set bussScheduleEndPoint(point: BussSchedulePoint) {
    this.endPoint = point.pathPoint;
  }

  static removeSeat(tripUser: TripUser, seatNo: number, update = false) {
    if (!tripUser.seats) tripUser.seats = [];
    tripUser.seats.splice(tripUser.seats.indexOf(seatNo), 1);
    TripUser.recalculatePrice(tripUser);
    if (update) {
      Xe.updateFields(tripUser, ['seatsString', 'totalPrice'], TripUser.meta);
    } else {
      return ['seatsString', 'totalPrice'];
    }
  }

  static clearSeat(tripUser: TripUser) {
    tripUser.totalPrice = 0;
    tripUser.seats = [];
    tripUser.seatsString = "";
  }

  static addSeat(tripUser: TripUser, seatNo: number, update = false) {
    if (!tripUser.seats) tripUser.seats = [];
    tripUser.seats.push(seatNo);
    TripUser.recalculatePrice(tripUser);
    if (update) {
      Xe.updateFields(tripUser, ['seatsString', 'totalPrice'], TripUser.meta);
    } else {
      return ['seatsString', 'totalPrice'];
    }
  }

  static recalculatePrice(tripUser: TripUser) {
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

  static setConfirmedBy(tripUser: TripUser, employee: Employee) {
    if (!employee) this.removeConfirmedBy(tripUser);
    tripUser.confirmedDateTime = XeDatePipe.instance.singleToFullDateTime(new Date());
    tripUser.confirmedBy = employee;
    tripUser.confirmedByUserId = employee.userId;
    tripUser.confirmedByCompanyId = employee.companyId;
    tripUser.confirmedByEmployeeId = employee.employeeId;
    Xe.updateFields(tripUser, ['confirmedBy'], TripUser.meta);
  }

  static removeConfirmedBy(tripUser: TripUser) {
    tripUser.confirmedBy = null;
    tripUser.confirmedByUserId = null;
    tripUser.confirmedByEmployeeId = null;
    tripUser.confirmedByCompanyId = null;
    tripUser.confirmedDateTime = null;
    Xe.updateFields(tripUser, ['confirmedBy'], TripUser.meta);
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
      {name: "trip.bussSchedule.buss.company.companyId"}
    ]
  })

  static new(option = {}) {
    const tripUser = new TripUser();
    tripUser.trip = new Trip();
    tripUser.trip.bussSchedule = new BussSchedule();
    tripUser.trip.bussSchedule.buss = new Buss();
    tripUser.trip.bussSchedule.buss.bussType = new BussType();
    tripUser.trip.bussSchedule.buss.company = new Company();
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
    const identifier = TripUser.entityIdentifier(tripUser);
    identifier.idFields.forEach((field) => {
      if (field.name === "trip.tripId") field['newIfNull'] = true;
    });
    return {
      table: {
        basicColumns: [
          {
            field: {name: 'fullName'}, subColumns: [
              {field: {name: 'phoneNumber', template: InputTemplate.phone}},
              {field: {name: 'email'}}
            ]
          },
          {
            field: {name: 'totalPrice', template: InputTemplate.money},
            subColumns: [
              {field: {name: 'seatsString', template: InputTemplate.seats}}
            ]
          },
          {
            field: {name: 'status', template: InputTemplate.tripUserStatus, mode: InputMode.input},
          },
          // 0
          {
            field: {name: 'startPoint', template: InputTemplate.pathPoint},
            subColumns: [
              {field: {name: 'startPoint.location', template: InputTemplate.location}}
            ]
          },
          // 1
          {
            field: {name: 'endPoint', template: InputTemplate.pathPoint},
            subColumns: [
              {field: {name: 'endPoint.location', template: InputTemplate.location}}
            ]
          },
          {
            field: {name: 'seatsString', template: InputTemplate.seats},
            subColumns: [
              {field: {name: 'totalPrice', template: InputTemplate.money}, display: {header: {silence: true}}}
            ]

          },
          // 2
          {
            field: {name: 'trip.launchDate', template: InputTemplate.date},
            subColumns: [
              {field: {name: 'trip.launchTime', template: InputTemplate.time}},
            ]
          },
          {
            field: {name: 'trip.bussSchedule.buss.company.companyName'},
            subColumns: [
              {field: {name: 'trip.bussSchedule.buss.bussType.bussTypeName'}},
              {field: {name: 'trip.bussSchedule.buss.bussLicense'}},
            ]
          },
        ],
      },
      formData: {
        entityIdentifier: identifier,
        mode: {
          uncheckChanged: true
        },
        share: {
          entity: tripUser,
        },
        display: {
          bare: true,
          noButton: true,
        },
        fields: [
          {name: 'fullName', required: true},
          {name: 'phoneNumber', required: true, template: InputTemplate.phone},
          {name: 'email', lblKey: 'email_optional'},
          {name: 'unitPrice', hidden: true},
          {name: 'seatsString', required: true, hidden: true},
          {name: 'totalPrice', hidden: true},
          {name: 'tripUserPointsString', hidden: true},
          {name: 'trip.launchTime', template: InputTemplate.time, hidden: true},
          {name: 'trip.launchDate', template: InputTemplate.date, hidden: true},
          {name: 'trip.tripUnitPrice', hidden: true},
        ]
      }
    } as XeTableData<TripUser>;
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

