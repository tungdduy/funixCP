import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {NbMenuService, NbSidebarService, NbThemeService} from '@nebular/theme';

import {LayoutService} from '../../../@core/utils';
import {Observable, Subject} from 'rxjs';
import {AuthUtil} from "../../../framework/auth/auth.util";
import {XeLabel} from "../../../business/i18n";
import {NbMenuItem} from "@nebular/theme/components/menu/menu.service";
import {Url} from "../../../framework/url/url.declare";
import {AbstractXe} from "../../../framework/model/AbstractXe";
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {environment} from "../../../../environments/environment";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {XeDatePipe} from "../../../framework/components/pipes/date.pipe";
import {XeTimePipe} from "../../../framework/components/pipes/time.pipe";
import {PhonePipe} from "../../../framework/components/pipes/phone.pipe";
import {EntityUtil} from "../../../framework/util/EntityUtil";
import {TripUser} from "../../../business/entities/TripUser";
import {MoneyPipe} from "../../../framework/components/pipes/money.pipe";

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent extends AbstractXe implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  public readonly materialTheme$: Observable<boolean>;
  userPictureOnly: boolean = false;
  currentUser = () => AuthUtil.instance.user;

  userMenu: NbMenuItem[] = [
    {title: XeLabel.MY_TRIPS, target: Url.app.ADMIN.MY_TRIP.noHost, hidden: AuthUtil.instance.isStaff()},
    {title: XeLabel.FIND_TRIPS, url: Url.app.ADMIN.FIND_TRIP.full, hidden: AuthUtil.instance.isStaff()},
    {title: XeLabel.LOG_OUT, hidden: !AuthUtil.instance.isUserLoggedIn, data: () => AuthUtil.instance.logout()},
  ];

  pendingTripUsers: NbMenuItem[] = [{title: 'Không có vé nào chờ duyệt'}];

  updatePendingTripUsers() {
    CommonUpdateService.instance.findPendingTripUsers(AuthUtil.instance.user?.userId).subscribe(tripUsers => {
      EntityUtil.cacheThenFill(tripUsers, TripUser.meta);
      if (tripUsers && tripUsers.length > 0) {
        this.pendingTripUsers = tripUsers.map(tripUser => this.tripUserToMenu(tripUser));
      } else {
        this.initPendingTripUser();
      }
    });
  }

  initPendingTripUser() {
    this.pendingTripUsers = [{title: 'Không có vé nào chờ duyệt'}];
  }

  get numberOfPendingTripUsers(): number {
    return this.pendingTripUsers.filter(tripUserMenu => tripUserMenu.data).length;
  }

  tripUserToMenu(tripUser: TripUser): NbMenuItem {
    return {
      title: `${tripUser.fullName} -
          ${PhonePipe.instance.singleToInline(tripUser.phoneNumber)} -
          ${XeDatePipe.instance.singleToInline(tripUser.trip.launchDate)} -
          ${XeTimePipe.instance.singleToInline(tripUser.trip.launchTime)} -
          ${tripUser.seats} -
          ${MoneyPipe.instance.singleToInline(tripUser.totalPrice)}
           `,
      url: Url.app.ADMIN.TICKET.param([{name: "tripUserId", value: tripUser.tripUserId}, {
        name: "tripId",
        value: tripUser.tripId
      }]).full,
      data: {tripUserId: tripUser.tripUserId}
    };
  }

  addPendingTripUser(tripUser: TripUser) {
    if (this.numberOfPendingTripUsers === 0) {
      this.pendingTripUsers = [];
    }
    this.pendingTripUsers.unshift(this.tripUserToMenu(tripUser));
  }

  removePendingTripUser(tripUser: TripUser) {
    const removeIdx = this.pendingTripUsers.findIndex((value) => value.data.tripUserId === tripUser.tripUserId);
    this.pendingTripUsers.splice(removeIdx, 1);
    if (this.pendingTripUsers.length === 0) {
      this.initPendingTripUser();
    }
  }

  static instance: HeaderComponent;

  public constructor(
    private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private themeService: NbThemeService,
    private layoutService: LayoutService,
    private _snackBar: MatSnackBar,
  ) {
    super();
    menuService.onItemClick().subscribe((menu) => {
      if (!!menu.item.data) {
        menu.item.data();
      }
    });
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.userPictureOnly = event.target.innerWidth < 500;
  }

  ngOnInit() {
    this.userPictureOnly = window.innerWidth < 500;
    if (AuthUtil.instance.hasBussAdmin || AuthUtil.instance.hasCaller) {
      this.listenToNewTripUser();
    }
    this.updatePendingTripUsers();
    HeaderComponent.instance = this;
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();

    return false;
  }

  notLogin() {
    return !AuthUtil.instance.isUserLoggedIn;
  }

  private stompClient = null;
  private subscription;
  disabled = true;
  get isStaff() {
    return this.auth.isStaff();
  }

  setConnected(connected: boolean) {
    this.disabled = !connected;
  }

  listenToNewTripUser() {
    const socket = new SockJS(environment.apiHost + "/socket");
    this.stompClient = Stomp.over(socket);

    const _this = this;

    this.stompClient.connect({
      Authorization: `${AuthUtil.instance.token}`
    }, (frame) => {
      _this.setConnected(true);
      _this.subscription = _this.stompClient.subscribe('/topic/' + AuthUtil.instance.companyId, (message) => {
        const json = JSON.parse(message.body);
        const tripUserId = json.tripUserId;
        const tripId = json.tripId;
        const snackBarRef = this._snackBar.open(json.message, 'Xem', {
          horizontalPosition: "end",
          verticalPosition: "bottom",
          panelClass: "trip-user-inform-wrapper"
        });

        snackBarRef._dismissAfter(20000);
        snackBarRef.onAction().subscribe(() => {
          window.location.href = Url.app.ADMIN.TICKET.param([
            {name: "tripUserId", value: tripUserId},
            {name: "tripId", value: tripId}]
          ).full;
        });
      }, {Authorization: `${AuthUtil.instance.token}`});
    });
  }


  @HostListener('window:unload', ['$event'])
  unloadHandler(event) {
    this.subscription.unsubscribe();
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeUnloadHandler(event) {
    this.subscription.unsubscribe();
  }
}
