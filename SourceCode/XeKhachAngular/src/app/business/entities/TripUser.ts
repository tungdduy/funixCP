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
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class TripUser extends XeEntity {
    static className = 'TripUser';
    static camelName = 'tripUser';
    static otherMainIdNames = ['tripId', 'userId'];
    static mainIdName = 'tripUserId';
    static pkMapFieldNames = ['trip', 'user'];
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
    totalSeats: number;
    confirmedByUserId: number;
    confirmedByEmployeeId: number;
    confirmedByCompanyId: number;
    status;
    totalPrice: number;
    confirmedDateTime;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (tripUser: TripUser): EntityIdentifier<TripUser> => ({
    entity: tripUser,
    clazz: TripUser,
    idFields: () => [
      {name: "tripUserId", value: tripUser.tripUserId},
      {name: "trip.tripId", value: tripUser.trip?.tripId},
      {name: "trip.bussSchedule.bussScheduleId", value: tripUser.trip?.bussSchedule?.bussScheduleId},
      {name: "trip.bussSchedule.buss.bussId", value: tripUser.trip?.bussSchedule?.buss?.bussId},
      {name: "trip.bussSchedule.buss.bussType.bussTypeId", value: tripUser.trip?.bussSchedule?.buss?.bussType?.bussTypeId},
      {name: "trip.bussSchedule.buss.company.companyId", value: tripUser.trip?.bussSchedule?.buss?.company?.companyId},
      {name: "user.userId", value: tripUser.user?.userId}
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
    ObjectUtil.assignEntity(option, tripUser);
    return tripUser;
  }

  static tableData = (option: XeTableData<TripUser> = {}, tripUser: TripUser = TripUser.new()): XeTableData<TripUser> => {
    const table = TripUser._tripUserTable(tripUser);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _tripUserTable = (tripUser: TripUser): XeTableData<TripUser> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
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
      header: {
        titleField: {name: 'user.fullName'},
        descField: {name: 'user.phoneNumber'},
      },
      fields: []
    }
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}

