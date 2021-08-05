import {Component} from '@angular/core';
import {XeTableData} from "../../../abstract/XeTableData";
import {Employee} from "../../../entities/employee";
import {AuthUtil} from "../../../../framework/auth/auth.util";

@Component({
  selector: 'xe-my-trip',
  styles: [],
  templateUrl: 'my-trip.component.html',
})
export class MyTripComponent  {
  user = AuthUtil.instance.user;

  tripUserTable: XeTableData = {
    table: {
      basicColumns: [
        {field: {name: 'startPoint.location.locationName'}, type: "boldString",
          subColumns: [
            {field: {name: 'startPoint.location.locationParentAddresses'}, type: "string"}
          ]
        },
        {field: {name: 'endPoint.location.locationName'}, type: "string",
          subColumns: [
            {field: {name: 'endPoint.location.locationParentAddresses'}, type: "string"}
          ]
        },
        {field: {name: 'user.phoneNumber'}, type: "string"},
        {field: {name: 'user.email'}, type: "string"},
      ],
    },
    formData: {
      entityIdentifier: {
        className: "TripUser",
        idFields: () => [
          {name: "user.userId", value: this.user.userId},
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
  };
}
