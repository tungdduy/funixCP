import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "./auth.service";
import {XeRouter} from "../../business/service/xe-router";
import {XeNotifierService} from "../notify/xe.notifier.service";
import {Url} from "../url/url.declare";
import {AppMessages} from "../../business/i18n";

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
    this.router.navigate(Url.app.CHECK_IN.LOGIN);
    this.notifier.error(AppMessages.NEED_LOGIN_TO_ACCESS);
    return false;
  }

  constructor(private authService: AuthService,
              private router: XeRouter,
              private notifier: XeNotifierService) {
  }

}
