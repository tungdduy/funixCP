import {NbMenuItem} from '@nebular/theme';
import { Url } from '../../../framework/url/url.declare';

export const ADMIN_MENUrr: NbMenuItem[] = [
  {title: 'My Trip', icon: 'shopping-cart-outline', link: Url.app.ADMIN.MY_TRIP.noHost, home: true},
  {title: 'company-manager', icon: 'shopping-cart-outline', link: '/admin/company-manager'},
  {title: 'buss', icon: 'shopping-cart-outline', link: '/admin/buss'},

];


export const ADMIN_MENU: NbMenuItem[] = [
  {title: 'Tài khoản của tôi', icon: {icon: 'user', pack: 'fa'}, link: Url.app.ADMIN.MY_ACCOUNT.noHost, home: true},

  {title: 'Chuyến đi của tôi', icon: {icon: 'suitcase-rolling', pack: 'fa'}, link: Url.app.ADMIN.MY_TRIP.noHost},
  {title: 'Quản lý nhà xe', icon: {icon: 'building', pack: 'fa'}, link: Url.app.ADMIN.COMPANY_MANAGER.noHost},
  {title: 'Nhân viên tổng đài', icon: {icon: 'headset', pack: 'fa'}, link: Url.app.ADMIN.CALLER_EMPLOYEE.noHost},
  {title: 'Loại Xe', icon: {icon: 'pencil-ruler', pack: 'fa'}, link: Url.app.ADMIN.BUSS_TYPE.noHost},
  {title: 'Quản lý Xe', icon: {icon: 'bus', pack: 'fa'}, link: Url.app.ADMIN.BUSS.noHost},
  {title: 'Nhân viên xe', icon: {icon: 'users-cog', pack: 'fa'}, link: Url.app.ADMIN.BUSS_EMPLOYEE.noHost},
  {title: 'Điểm dừng', icon: {icon: 'flag', pack: 'fa'}, link: Url.app.ADMIN.BUSS_STOP.noHost},
  {title: 'Vé', icon: {icon: 'ticket-alt', pack: 'fa'}, link: Url.app.ADMIN.TICKET.noHost},
];
