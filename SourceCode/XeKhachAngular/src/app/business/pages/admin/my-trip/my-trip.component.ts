import {AfterViewInit, Component} from '@angular/core';
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {TripUser} from "../../../entities/TripUser";
import {XeSubscriber} from "../../../../framework/model/XeSubscriber";
import {User} from "../../../entities/User";

@Component({
  selector: 'xe-my-trip',
  styles: [],
  templateUrl: 'my-trip.component.html',
})
export class MyTripComponent extends XeSubscriber implements AfterViewInit {
  user = AuthUtil.instance.user;

  tripUserTable = TripUser.tableData({}, TripUser.new({
    user: this.user
  }));

  ngAfterViewInit(): void {
    this.refresh(this.user, User);
  }


}
