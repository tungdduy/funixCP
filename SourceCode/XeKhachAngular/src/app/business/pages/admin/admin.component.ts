import {Component} from '@angular/core';
import {Url} from "../../../framework/url/url.declare";
import {XeLabel} from "../../i18n";

@Component({
  selector: 'xe-admin',
  styleUrls: ['./admin.component.scss'],
  templateUrl: './admin.component.html'
})
export class AdminComponent {
  MY_ACCOUNT = Url.app.ADMIN.MY_ACCOUNT._short;
  MY_TRIP = Url.app.ADMIN.MY_TRIP._short;
  COMPANY_MANAGER = Url.app.ADMIN.COMPANY_MANAGER._short;
  CALLER_EMPLOYEE = Url.app.ADMIN.CALLER_EMPLOYEE._short;
  BUSS_TYPE = Url.app.ADMIN.BUSS_TYPE._short;
  BUSS = Url.app.ADMIN.BUSS._short;
  BUSS_EMPLOYEE = Url.app.ADMIN.BUSS_EMPLOYEE._short;
  BUSS_STOP = Url.app.ADMIN.BUSS_STOP._short;
  TICKET = Url.app.ADMIN.TICKET._short;
  label = XeLabel;
  constructor() {}
}

