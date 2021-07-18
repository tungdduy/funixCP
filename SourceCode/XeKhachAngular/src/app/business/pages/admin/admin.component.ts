import {Component} from '@angular/core';
import {Url} from "../../../framework/url/url.declare";
import {XeLabel} from "../../i18n";

@Component({
  selector: 'xe-admin',
  styleUrls: ['./admin.component.scss'],
  templateUrl: './admin.component.html'
})
export class AdminComponent {
  BUSS_STAFF = Url.app.ADMIN.BUSS_STAFF._short;
  label = XeLabel;
  constructor() {}
}

