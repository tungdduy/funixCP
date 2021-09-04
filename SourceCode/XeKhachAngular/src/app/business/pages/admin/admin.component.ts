import {Component} from '@angular/core';
import {NbIconLibraries} from "@nebular/theme";
import {Url} from "../../../framework/url/url.declare";
import {XeTableData} from "../../../framework/model/XeTableData";
import {XeScreen} from "../../../framework/components/xe-nav/xe-nav.component";
import {XeLabel} from "../../i18n";
import {AuthUtil} from "../../../framework/auth/auth.util";

@Component({
  selector: 'xe-admin',
  styles: [''],
  templateUrl: './admin.component.html'
})
export class AdminComponent {
  static instance = () => AdminComponent._instance;
  private static _instance: AdminComponent;
  menu = [
    {title: XeLabel.MY_ACCOUNT, icon: {icon: 'user', pack: 'fa'}, link: Url.app.ADMIN.MY_ACCOUNT.noHost, home: true, hidden: Url.app.ADMIN.MY_ACCOUNT.forbidden()},
    {title: XeLabel.FIND_TRIPS, icon: {icon: 'luggage-cart', pack: 'fa'}, link: Url.app.ADMIN.FIND_TRIP.noHost, hidden: Url.app.ADMIN.FIND_TRIP.forbidden()},
    {title: XeLabel.MY_TRIPS, icon: {icon: 'suitcase-rolling', pack: 'fa'}, link: Url.app.ADMIN.MY_TRIP.noHost, hidden: Url.app.ADMIN.MY_TRIP.forbidden()},
    {title: XeLabel.ALL_USERS, icon: {icon: 'users', pack: 'fa'}, link: Url.app.ADMIN.ALL_USER.noHost, home: true, hidden: Url.app.ADMIN.ALL_USER.forbidden()},
    {title: XeLabel.COMPANY_MANAGER, icon: {icon: 'building', pack: 'fa'}, link: Url.app.ADMIN.COMPANY_MANAGER.noHost, hidden: Url.app.ADMIN.COMPANY_MANAGER.forbidden()},
    {title: XeLabel.BUSS_TYPE, icon: {icon: 'pencil-ruler', pack: 'fa'}, link: Url.app.ADMIN.BUSS_TYPE.noHost, hidden: Url.app.ADMIN.BUSS_TYPE.forbidden()},
    {title: XeLabel.BUSS_SCHEDULES, icon: {icon: 'bus', pack: 'fa'}, link: Url.app.ADMIN.BUSS.noHost, hidden: Url.app.ADMIN.BUSS.forbidden() || AuthUtil.instance.isNonEmployee()},
    {title: XeLabel.EMPLOYEE, icon: {icon: 'users-cog', pack: 'fa'}, link: Url.app.ADMIN.EMPLOYEE.noHost, hidden: Url.app.ADMIN.EMPLOYEE.forbidden() || AuthUtil.instance.isNonEmployee()},
    {title: XeLabel.PATH, icon: {icon: 'road', pack: 'fa'}, link: Url.app.ADMIN.PATH.noHost, hidden: Url.app.ADMIN.PATH.forbidden()},
    {title: XeLabel.TICKET, icon: {icon: 'ticket-alt', pack: 'fa'}, link: Url.app.ADMIN.TICKET.noHost, hidden: Url.app.ADMIN.TICKET.forbidden()},
  ];
  screens = {
    home: 'home',
    table: 'table'
  };
  screen = new XeScreen({home: 'home'});
  tableData: XeTableData<any>;
  constructor(iconsLibrary: NbIconLibraries) {
    AdminComponent._instance = this;
    iconsLibrary.registerFontPack('fa', { packClass: 'fa', iconClassPrefix: 'fa' });
    iconsLibrary.registerFontPack('far', { packClass: 'far', iconClassPrefix: 'fa' });
  }

}

