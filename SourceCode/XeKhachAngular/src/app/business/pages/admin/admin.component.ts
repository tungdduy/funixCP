import {Component} from '@angular/core';
import {NbIconLibraries} from "@nebular/theme";
import {Url} from "../../../framework/url/url.declare";

@Component({
  selector: 'xe-admin',
  styles: [''],
  templateUrl: './admin.component.html'
})
export class AdminComponent {
  menu = [
    {title: 'Tài khoản của tôi', icon: {icon: 'user', pack: 'fa'}, link: Url.app.ADMIN.MY_ACCOUNT.noHost, home: true, hidden: Url.app.ADMIN.MY_ACCOUNT.forbidden()},
    {title: 'Chuyến đi của tôi', icon: {icon: 'suitcase-rolling', pack: 'fa'}, link: Url.app.ADMIN.MY_TRIP.noHost, hidden: Url.app.ADMIN.MY_TRIP.forbidden()},
    {title: 'Tất cả người dùng', icon: {icon: 'users', pack: 'fa'}, link: Url.app.ADMIN.ALL_USER.noHost, home: true, hidden: Url.app.ADMIN.ALL_USER.forbidden()},
    {title: 'Quản lý Nhà xe', icon: {icon: 'building', pack: 'fa'}, link: Url.app.ADMIN.COMPANY_MANAGER.noHost, hidden: Url.app.ADMIN.COMPANY_MANAGER.forbidden()},
    {title: 'Loại Xe', icon: {icon: 'pencil-ruler', pack: 'fa'}, link: Url.app.ADMIN.BUSS_TYPE.noHost, hidden: Url.app.ADMIN.BUSS_TYPE.forbidden()},
    {title: 'Xe', icon: {icon: 'bus', pack: 'fa'}, link: Url.app.ADMIN.BUSS.noHost, hidden: Url.app.ADMIN.BUSS.forbidden()},
    {title: 'Nhân viên', icon: {icon: 'users-cog', pack: 'fa'}, link: Url.app.ADMIN.EMPLOYEE.noHost, hidden: Url.app.ADMIN.EMPLOYEE.forbidden()},
    {title: 'Điểm dừng', icon: {icon: 'flag', pack: 'fa'}, link: Url.app.ADMIN.BUSS_STOP.noHost, hidden: Url.app.ADMIN.BUSS_STOP.forbidden()},
    {title: 'Vé', icon: {icon: 'ticket-alt', pack: 'fa'}, link: Url.app.ADMIN.TICKET.noHost, hidden: Url.app.ADMIN.TICKET.forbidden()},
  ];
  constructor(iconsLibrary: NbIconLibraries) {
    iconsLibrary.registerFontPack('fa', { packClass: 'fa', iconClassPrefix: 'fa' });
    iconsLibrary.registerFontPack('far', { packClass: 'far', iconClassPrefix: 'fa' });
  }
}

