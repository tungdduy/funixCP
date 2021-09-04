import {AfterViewInit, Component} from '@angular/core';
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {TripUser} from "../../../entities/TripUser";
import {User} from "../../../entities/User";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {Location} from "../../../entities/Location";
import {XeTableComponent} from "../../../../framework/components/xe-table/xe-table.component";
import {Xe} from "../../../../framework/model/Xe";
import {StorageUtil} from "../../../../framework/util/storage.util";
import {configConstant} from "../../../../framework/config.constant";
import {TicketInfo} from "../../../../framework/model/XeFormData";
import {XeTableData} from "../../../../framework/model/XeTableData";
import {CommonUpdateService} from "../../../service/common-update.service";
import {FormAbstract} from "../../../../framework/model/form.abstract";

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
export class MyTripComponent extends FormAbstract implements AfterViewInit {
  user = AuthUtil.instance.user;
  screens = {
    trips: 'trips',
    newTrip: 'newTrips',
    viewSelectedSchedulePoints: 'viewSelectedSchedulePoints',
    orderBussScheme: 'orderBussScheme'
  };
  screen = new XeScreen({home: this.screens.trips});

  tripUserTable: XeTableData<TripUser>;

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.init();
    }, 0);
  }
  init() {
    if (AuthUtil.instance.isUserLoggedIn) {
      Xe.refresh(this.user, User.meta);
    }
    const tickets = StorageUtil.getFromJson(configConstant.TICKET_INFOS) as TicketInfo || {phone: "", email: ""};
    const tripUserFinder = CommonUpdateService.instance.findTripUsers(AuthUtil.instance.user?.userId, tickets.phone, tickets.email);
    this.tripUserTable = TripUser.tableData({
      xeScreen: this.screen,
      table: {
        action: {
          postSelect: () => {},
        },
        mode: {
          denyNew: true,
          customObservable: tripUserFinder
        },
        selectBasicColumns: ['trip.bussSchedule.path', 'totalPrice', 'startPoint', 'endPoint', 'trip.launchDate', 'trip.bussSchedule.buss.company.companyName']
      },
      display: {
        fullScreenForm: true
      }
    });
  }
}
