import {Trip} from "./Trip";
import {User} from "./User";
import {XeTableData} from "../../framework/model/XeTableData";
import {Employee} from "./Employee";
import {XeEntity} from "./XeEntity";
import {ObjectUtil} from "../../framework/util/object.util";

export class TripUser extends XeEntity {
  tripId: number;
  userId: number;
  user: User;
  trip: Trip;

  static tripUserTable (option: {}): XeTableData {
    return ObjectUtil.assignEntityTable(option, TripUser._tripUserTable());
  }

  private static _tripUserTable = (): XeTableData => ({
    table: {
      basicColumns: [
        // 0
        {field: {name: 'startPoint.location.locationName'}, type: "boldString",
          subColumns: [
            {field: {name: 'startPoint.location.locationParentAddresses'}, type: "string"}
          ]
        },
        // 1
        {field: {name: 'endPoint.location.locationName'}, type: "string",
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
      entityIdentifier: {
        className: "TripUser",
        idFields: () => [
          {name: "user.userId", value: 0},
          {name: "trip.tripId", value: 0},
          {name: "tripUserId", value: 0},
        ]
      },
      share: {entity: new Employee()},
      header: {
        titleField: {name: 'user.fullName'},
        descField: {name: 'user.phoneNumber'},
      },
      fields: [
      ]
    }
  })
}
