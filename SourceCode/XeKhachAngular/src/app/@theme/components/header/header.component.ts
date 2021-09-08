import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {NbMenuService, NbSidebarService, NbThemeService, NbToastrService} from '@nebular/theme';

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

  public constructor(
    private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private themeService: NbThemeService,
    private layoutService: LayoutService,
    private toastrService: NbToastrService
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
      this.connect();
    }
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

  navigateHome() {
    this.menuService.navigateHome();
     return false;
  }

  notLogin() {
    return !AuthUtil.instance.isUserLoggedIn;
  }

  private stompClient = null;
  private subscription;
  disabled = true;
  setConnected(connected: boolean) {
    this.disabled = !connected;
  }

  connect() {
    const socket = new SockJS(environment.apiHost + "/socket");
    this.stompClient = Stomp.over(socket);

    const _this = this;

    this.stompClient.connect({
      Authorization: `${AuthUtil.instance.token}`
    }, (frame) => {
      _this.setConnected(true);
      // console.log('Connected: ' + frame);

      _this.subscription = _this.stompClient.subscribe('/topic/' + AuthUtil.instance.companyId, (message) => {
        // console.log(message);
        const json = JSON.parse(message.body);
        this.toastrService.show(
          json.message ,
          `Bạn có thông tin đặt vé mới`,
          { duration : 0, status : json.type === "cancel" ? "danger" : "primary" });
      }, {Authorization: `${AuthUtil.instance.token}`});
    });
  }

  @HostListener('window:unload', [ '$event' ])
  unloadHandler(event) {
    this.subscription.unsubscribe();
  }
  @HostListener('window:beforeunload', [ '$event' ])
  beforeUnloadHandler(event) {
    this.subscription.unsubscribe();
  }
}
