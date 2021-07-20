import {NbMenuItem} from '@nebular/theme';
import { Url } from '../../../framework/url/url.declare';
import {AuthService} from "../../../framework/auth/auth.service";


export const ADMIN_MENU: NbMenuItem[] =
  [
    {title: 'Tài khoản của tôi', icon: {icon: 'user', pack: 'fa'}, link: Url.app.ADMIN.MY_ACCOUNT.noHost, home: true, hidden: !AuthService.isAllow(Url.app.ADMIN.MY_ACCOUNT.roles)},
    {title: 'Chuyến đi của tôi', icon: {icon: 'suitcase-rolling', pack: 'fa'}, link: Url.app.ADMIN.MY_TRIP.noHost, hidden: !AuthService.isAllow(Url.app.ADMIN.MY_TRIP.roles)},
    {title: 'Quản lý nhà xe', icon: {icon: 'building', pack: 'fa'}, link: Url.app.ADMIN.COMPANY_MANAGER.noHost, hidden: !AuthService.isAllow(Url.app.ADMIN.COMPANY_MANAGER.roles)},
    {title: 'Nhân viên tổng đài', icon: {icon: 'headset', pack: 'fa'}, link: Url.app.ADMIN.CALLER_EMPLOYEE.noHost, hidden: !AuthService.isAllow(Url.app.ADMIN.CALLER_EMPLOYEE.roles)},
    {title: 'Loại Xe', icon: {icon: 'pencil-ruler', pack: 'fa'}, link: Url.app.ADMIN.BUSS_TYPE.noHost, hidden: !AuthService.isAllow(Url.app.ADMIN.BUSS_TYPE.roles)},
    {title: 'Quản lý Xe', icon: {icon: 'bus', pack: 'fa'}, link: Url.app.ADMIN.BUSS.noHost, hidden: !AuthService.isAllow(Url.app.ADMIN.BUSS.roles)},
    {title: 'Nhân viên xe', icon: {icon: 'users-cog', pack: 'fa'}, link: Url.app.ADMIN.BUSS_EMPLOYEE.noHost, hidden: !AuthService.isAllow(Url.app.ADMIN.BUSS_EMPLOYEE.roles)},
    {title: 'Điểm dừng', icon: {icon: 'flag', pack: 'fa'}, link: Url.app.ADMIN.BUSS_STOP.noHost, hidden: !AuthService.isAllow(Url.app.ADMIN.BUSS_STOP.roles)},
    {title: 'Vé', icon: {icon: 'ticket-alt', pack: 'fa'}, link: Url.app.ADMIN.TICKET.noHost, hidden: !AuthService.isAllow(Url.app.ADMIN.TICKET.roles)},
  ];
