import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {XeRouter} from "../../business/service/xe-router";
import {Notifier} from "../notify/notify.service";
import {Url} from "../url/url.declare";
import {AppMessages} from "../../business/i18n";
import {AuthUtil} from "./auth.util";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    if (AuthUtil.isUserLoggedIn()) {
      return true;
    }
    XeRouter.navigate(Url.app.CHECK_IN.LOGIN);
    Notifier.error(AppMessages.NEED_LOGIN_TO_ACCESS);
    return false;
  }

}
