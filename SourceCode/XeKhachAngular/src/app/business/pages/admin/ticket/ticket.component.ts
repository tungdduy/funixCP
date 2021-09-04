import {Component} from '@angular/core';
import {Trip} from "../../../entities/Trip";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {TripUser} from "../../../entities/TripUser";
import {EntityUtil} from "../../../../framework/util/EntityUtil";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {CommonUpdateService} from "../../../service/common-update.service";

@Component({
  selector: 'xe-ticket',
  styles: [],
  templateUrl: 'ticket.component.html',
})
export class TicketComponent extends FormAbstract {

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
      mode: {
        customObservable: CommonUpdateService.instance.findTripByCompanyId(this.auth.companyId)
      },
      action: {
        filters: {
          filterSingle: (trip: Trip) => this.auth.hasBussAdmin ? true : trip.bussSchedule.buss.bussEmployees.filter(be => be.employeeId === this.auth.employeeId).length > 0
        },
        postSelect: () => this.screen.go(this.screens.tripDetails),
      },
      basicColumns: [
        {
          field: {name: 'totalTripUsers'}, action: {screen: this.screens.tripUsers},
          subColumns: [
            {field: {name: 'totalSeats'}, action: {screen: this.screens.tripDetails}}
          ]
        },
      ]
    }
  }, Trip.new({company: this.auth.company}));

  tripUserTable = TripUser.tableData({
    table: {
      mode: {
        readonly: true
      }
    },
    xeScreen: this.screen,
  });

  get selectedTrip() {
    return this.tripTable.formData.share.entity;
  }
  get bussType() {
    return EntityUtil.getEntityWithField(this.selectedTrip, Trip.meta, {name: 'bussSchedule.buss.bussType.bussTypeId'}).entity;
  }
}
