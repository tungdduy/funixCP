import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "./auth.service";
import {AppUrl} from "../static/url";
import {AppMessages} from "../static/app-messages";
import {XeRouter} from "../service/xe-router";
import {XeNotifierService} from "../service/xe-notifier.service";

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
    this.router.navigateNow(AppUrl.LOGIN);
    this.notifier.error(AppMessages.NEED_LOGIN_TO_ACCESS);
    return false;
  }

  constructor(private authService: AuthService,
              private router: XeRouter,
              private notifier: XeNotifierService) {
  }

}
