import {Component} from '@angular/core';
import {Trip} from "../../../entities/Trip";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {TripUser} from "../../../entities/TripUser";
import {EntityUtil} from "../../../../framework/util/EntityUtil";

@Component({
  selector: 'xe-ticket',
  styles: [],
  templateUrl: 'ticket.component.html',
})
export class TicketComponent {

  screens = {
    trips: 'trips',
    tripDetails: 'tripDetails',
    tripUsers: 'tripUsers'
  };
  screen = new XeScreen({home: this.screens.trips});
  tripTable = Trip.tableData({
    external: {
      updateCriteriaTableOnSelect: () => [this.tripUserTable]
    },
    xeScreen: this.screen,
    table: {
      basicColumns: [
        {
          field: {name: 'totalTripUsers'}, action: {screen: this.screens.tripUsers},
          subColumns: [
            {field: {name: 'totalSeats'}, action: {screen: this.screens.tripDetails}}
          ]
        },
      ]
    }
  }, Trip.new({company: AuthUtil.instance.user?.employee?.company}));

  tripUserTable = TripUser.tableData({
    xeScreen: this.screen,
  });

  get selectedTrip() {
    return this.tripTable.formData.share.entity;
  }
  get bussType() {
    return EntityUtil.getEntityWithField(this.selectedTrip, Trip.meta, {name: 'bussSchedule.buss.bussType.bussTypeId'}).entity;
  }
}
