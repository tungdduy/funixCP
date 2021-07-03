import {Component} from '@angular/core';
import {XeLabel} from "../../i18n/xe-label";
import {Url} from "../../url.declare";

@Component({
  selector: 'xe-auth',
  styleUrls: ['./check-in.component.scss'],
  templateUrl: './check-in.component.html'
})
export class CheckInComponent {
  LOGIN = Url.app.AUTH.LOGIN.__short;
  REGISTER = Url.app.AUTH.REGISTER.__short;
  FORGOT_PASSWORD = Url.app.AUTH.FORGOT_PASSWORD.__short;
  label = XeLabel;
  constructor() {
  }
}

