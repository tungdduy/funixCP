import {AfterViewInit, Component} from '@angular/core';
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {TripUser} from "../../../entities/TripUser";
import {XeSubscriber} from "../../../../framework/model/XeSubscriber";
import {User} from "../../../entities/User";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {Location} from "../../../entities/Location";
import {XeTableComponent} from "../../../../framework/components/xe-table/xe-table.component";
import {Xe} from "../../../../framework/model/Xe";

export interface BussScheduleCriteria {
  locationFrom: Location;
  locationTo: Location;
  launchDate: Date;
  inputName?: string;
  inputText?: string;
  validator?: (criteria: BussScheduleCriteria) => any;
  tableComponent?: () => XeTableComponent<any>;
}

@Component({
  selector: 'xe-my-trip',
  styles: [],
  templateUrl: 'my-trip.component.html',
})
export class MyTripComponent extends XeSubscriber implements AfterViewInit {
  user = AuthUtil.instance.user;
  screens = {
    trips: 'trips',
    newTrip: 'newTrips',
    viewSelectedSchedulePoints: 'viewSelectedSchedulePoints',
    orderBussScheme: 'orderBussScheme'
  };
  screen = new XeScreen({home: this.screens.trips});

  tripUserTable = TripUser.tableData({
    xeScreen: this.screen,
    display: {
      fullScreenForm: true
    }
  }, TripUser.new({
    user: this.user
  }));

  ngAfterViewInit(): void {
    if (AuthUtil.instance.isUserLoggedIn) {
      Xe.refresh(this.user, User.meta);
    }
  }



}
