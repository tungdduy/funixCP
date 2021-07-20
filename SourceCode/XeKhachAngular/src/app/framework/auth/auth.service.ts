import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../business/model/user";
import {JwtHelperService} from "@auth0/angular-jwt";
import {StorageUtil} from "../util/storage.util";
import {AuthConfig} from "./auth.config";
import {RegisterModel} from "../../business/model/register.model";
import {StringUtil} from "../util/string.util";
import {Url} from "../url/url.declare";
import {configConstant} from "../config.constant";
import {XeRole} from "../../business/constant/xe.role";
import {XeRouter} from "../../business/service/xe-router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  public login(user: User): Observable<HttpResponse<User>> | any {
    return this.http.post<User>(Url.api.USER.LOGIN.full, user, {observe: 'response'});
  }

  public register(user: RegisterModel): Observable<User> {
    return this.http.post<User>(Url.api.USER.REGISTER.full, user);
  }

  private static get instance() {
    const token = StorageUtil.getString(configConstant.TOKEN);
    if (StringUtil.blankOrNotEqual(AuthService._token, token)) {
      AuthService.decodeAndSaveToken(token);
    }
    return AuthService;
  }

  private static _jwtHelper = new JwtHelperService();
  private static _token: string | null | undefined;
  private static _roles: XeRole[] = [];
  private static _user: User;

  private static isExpired(): boolean {
    if (AuthService._jwtHelper.isTokenExpired(AuthService._token)) {
      AuthService.logout();
      return true;
    }
    return false;
  }

  private static decodeAndSaveToken(token: string) {

    if (StringUtil.isBlank(token)) return false;

    const tokenContent = AuthService._jwtHelper.decodeToken(token);
    if (StringUtil.isNotBlank(tokenContent?.sub)) {
      if (!AuthService._jwtHelper.isTokenExpired(token)) {
        AuthService.setRepoToken(token);
        AuthService.setRoles(AuthService.convertToAppRoles(tokenContent.authorities));
        return;
      }
    }
    AuthService.logout();
  }

  private static convertToAppRoles(apiAuthorities: string[]): XeRole[] {
    const roles: XeRole[] = [];
    apiAuthorities.forEach(authority => {
      if (authority.startsWith("ROLE_")) {
        roles.push(XeRole[authority]);
      }
    });
    return roles;
  }


  private static setRoles = (xeRoles: XeRole[]) => {
    AuthService._roles = xeRoles;
  }

  // ===========================================================
  // ===========================================================
  // ============ PUBLIC STATIC METHOD BELOW HERE

  static get token(): string {
    return AuthService.instance._token;
  }

  static saveResponse(response: HttpResponse<User>) {
    const token = response.headers.get(AuthConfig.tokenHeader);
    AuthService.decodeAndSaveToken(token);
    AuthService.setRepoUser(response.body);
  }

  private static setRepoUser(user: User) {
    AuthService._user = user;
    StorageUtil.setItem(configConstant.USER, user);
  }

  private static setRepoToken(token: any) {
    AuthService._token = token;
    StorageUtil.setItem(configConstant.TOKEN, token);
  }

  static isAllow(userRoles: XeRole[]): boolean {
    return userRoles.every(r => AuthService.instance._roles.includes(r));
  }

  static logout() {
    AuthService._roles = [];
    AuthService.setRepoToken(null);
    AuthService.setRepoUser(null);
    XeRouter.navigate(Url.app.CHECK_IN.LOGIN.noHost);
  }

  static isUserLoggedIn(): boolean {
    return !AuthService.instance.isExpired();
  }

  static get roles(): XeRole[] {
    return AuthService.instance._roles;
  }
}
