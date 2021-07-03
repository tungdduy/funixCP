import {Component} from '@angular/core';
import {XeLabel} from "../../i18n/xe-label";
import {Url} from "../../../framework/url/url.declare";

@Component({
  selector: 'xe-auth',
  styleUrls: ['./check-in.component.scss'],
  templateUrl: './check-in.component.html'
})
export class CheckInComponent {
  LOGIN = Url.app.CHECK_IN.LOGIN.__short;
  REGISTER = Url.app.CHECK_IN.REGISTER.__short;
  FORGOT_PASSWORD = Url.app.CHECK_IN.FORGOT_PASSWORD.__short;
  label = XeLabel;
  constructor() {
  }
}

