import {Component} from '@angular/core';
import {Url} from "../../../framework/url/url.declare";
import {XeLabel} from "../../i18n";

@Component({
  selector: 'xe-check-in',
  styleUrls: ['./check-in.component.scss'],
  templateUrl: './check-in.component.html'
})
export class CheckInComponent {
  LOGIN = Url.app.CHECK_IN.LOGIN.short;
  REGISTER = Url.app.CHECK_IN.REGISTER.short;
  FORGOT_PASSWORD = Url.app.CHECK_IN.FORGOT_PASSWORD.short;
  label = XeLabel;
  constructor() {}
}

