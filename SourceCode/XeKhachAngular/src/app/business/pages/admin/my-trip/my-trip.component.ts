import {AfterViewInit, Component} from '@angular/core';
import {XeTableData} from "../../../../framework/model/XeTableData";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {TripUser} from "../../../entities/TripUser";
import {XeSubscriber} from "../../../../framework/model/XeSubscriber";

@Component({
  selector: 'xe-my-trip',
  styles: [],
  templateUrl: 'my-trip.component.html',
})
export class MyTripComponent extends XeSubscriber implements AfterViewInit {
  user = AuthUtil.instance.user;

  tripUserTable: XeTableData = TripUser.tripUserTable({
    formData: {
      entityIdentifier: {
        idFields: () => [{name: "user.userId", value: 0}]
      }
    }
  });

  ngAfterViewInit(): void {
    this.refresh(this.user, "User");
  }


}
