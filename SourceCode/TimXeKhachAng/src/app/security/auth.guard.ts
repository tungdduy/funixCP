import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from "./auth.service";
import {Notifier} from "../service/notifier";
import {AppUrl} from "../static/url";
import {AppMessages} from "../static/app-messages";
import {XeRouter} from "../service/xe-router";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    if(this.authService.isUserLoggedIn()){
      return true;
    }
    this.router.navigateNow(AppUrl.LOGIN);
    this.notifier.error(AppMessages.NEED_LOGIN_TO_ACCESS);
    return false;
  }

  constructor(private authService: AuthService,
              private router: XeRouter,
              private notifier: Notifier) {
  }

}
