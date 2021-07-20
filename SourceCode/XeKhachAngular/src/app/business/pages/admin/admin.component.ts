import {Component} from '@angular/core';
import {Url} from "../../../framework/url/url.declare";
import {XeLabel} from "../../i18n";
import {ADMIN_MENU} from "./admin-menu";
import {NbIconLibraries} from "@nebular/theme";

@Component({
  selector: 'xe-admin',
  styleUrls: ['./admin.component.scss'],
  templateUrl: './admin.component.html'
})
export class AdminComponent {
  MY_ACCOUNT = Url.app.ADMIN.MY_ACCOUNT.short;
  MY_TRIP = Url.app.ADMIN.MY_TRIP.short;
  COMPANY_MANAGER = Url.app.ADMIN.COMPANY_MANAGER.short;
  CALLER_EMPLOYEE = Url.app.ADMIN.CALLER_EMPLOYEE.short;
  BUSS_TYPE = Url.app.ADMIN.BUSS_TYPE.short;
  BUSS = Url.app.ADMIN.BUSS.short;
  BUSS_EMPLOYEE = Url.app.ADMIN.BUSS_EMPLOYEE.short;
  BUSS_STOP = Url.app.ADMIN.BUSS_STOP.short;
  TICKET = Url.app.ADMIN.TICKET.short;
  label = XeLabel;
  menu = ADMIN_MENU;


  evaIcons = [];
  constructor(iconsLibrary: NbIconLibraries) {
    iconsLibrary.registerFontPack('fa', { packClass: 'fa', iconClassPrefix: 'fa' });
    iconsLibrary.registerFontPack('far', { packClass: 'far', iconClassPrefix: 'fa' });
    iconsLibrary.registerFontPack('ion', { iconClassPrefix: 'ion' });
  }
}

