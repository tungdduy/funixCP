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
import {EntityUtil} from "../../framework/util/EntityUtil";
import {InputTemplate} from "../../framework/model/EnumStatus";
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
    totalTripUsers: number;
    lockedSeatsString: string;
    tripUnitPrice: number;
    launchTime;
    launchDate;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
  preparedTripUser: TripUser;
  preparedAvailableSeats: number[];
  preparedBookedSeats: number[];
  totalPreparedAvailableSeats: number;

  totalBookedSeats: number;
  lockedSeats: number[];
  lockedBussSeats: number[];
  totalSeats: number;
  full: boolean;
  launched: boolean;
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
    return {
      table: {
        basicColumns: [
          {
            field: {name: 'launchDate', template: InputTemplate.date}, display: {header: {title: 'Ngày, giờ, giá'}},
            subColumns: [
              {field: {name: 'launchTime', template: InputTemplate.time}, display: {header: {silence: true}}},
              {field: {name: 'tripUnitPrice', template: InputTemplate.money}, display: {header: {silence: true}}},
            ]
          },
          {
            field: {name: 'totalTripUsers', attachInlines: ['totalBookedSeats']},
            type: "iconOption",
            display: {header: {title: 'Vé / Ghế đã đặt'}, row: {icon: {iconAfter: 'ticket-alt'}}},
            subColumns: [
              {
                field: {name: 'totalSeats'},
                type: "iconOption",
                display: {header: {title: 'Tổng ghế'}, row: {icon: {iconAfter: 'couch'}}},
              }
            ]
          },
          {
            field: {name: 'bussSchedule.startPoint', template: InputTemplate.pathPoint}, display: {header: {title: 'Điểm đầu'}},
            subColumns: [
              {field: {name: 'bussSchedule.startPoint.location', template: InputTemplate.location}, display: {header: {silence: true}}}
            ]
          },
          {
            field: {name: 'bussSchedule.endPoint', template: InputTemplate.pathPoint}, display: {header: {title: 'Điểm cuối'}},
            subColumns: [
              {field: {name: 'bussSchedule.startPoint.location', template: InputTemplate.location}, display: {header: {silence: true}}}
            ]
          },
        ],
      },
      formData: {
        entityIdentifier: Trip.entityIdentifier(trip),
        mode: {
          uncheckChanged: true
        },
        share: {
          entity: trip,
        },
        fields: []
      }
    } as XeTableData<Trip>;
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

