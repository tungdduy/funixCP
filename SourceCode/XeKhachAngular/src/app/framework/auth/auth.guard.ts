import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "./auth.service";
import {AppMessages} from "../../business/i18n/app-messages";
import {XeRouter} from "../../business/service/xe-router";
import {XeNotifierService} from "../notify/xe.notifier.service";
import {Url} from "../../business/url.declare";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    if (this.authService.isUserLoggedIn()) {
      return true;
    }
    this.router.navigateNow(Url.app.AUTH.LOGIN.full);
    this.notifier.error(AppMessages.NEED_LOGIN_TO_ACCESS);
    return false;
  }

  constructor(private authService: AuthService,
              private router: XeRouter,
              private notifier: XeNotifierService) {
  }

}
