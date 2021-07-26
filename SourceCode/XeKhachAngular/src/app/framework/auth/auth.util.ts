import {JwtHelperService} from "@auth0/angular-jwt";
import {XeRole} from "../../business/constant/xe.role";
import {User} from "../../business/model/user";
import {StringUtil} from "../util/string.util";
import {StorageUtil} from "../util/storage.util";
import {configConstant} from "../config.constant";
import {HttpResponse} from "@angular/common/http";
import {AuthConfig} from "./auth.config";
import {XeRouter} from "../../business/service/xe-router";

export class AuthUtil {
  private static _jwtHelper = new JwtHelperService();
  private static _token: string | null | undefined;
  private static _roles: XeRole[] = [];
  private static _user: User;

  private static isExpired(): boolean {
    return AuthUtil._jwtHelper.isTokenExpired(AuthUtil.token);
  }

  private static decodeAndSaveToken(token: string) {

    if (StringUtil.isBlank(token)) return false;

    const tokenContent = AuthUtil._jwtHelper.decodeToken(token);
    if (StringUtil.isNotBlank(tokenContent?.sub)) {
      if (!AuthUtil._jwtHelper.isTokenExpired(token)) {
        AuthUtil.setRepoToken(token);
        AuthUtil.setRoles(AuthUtil.convertToAppRoles(tokenContent.authorities));
        return;
      }
    }
    AuthUtil.logout();
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
    AuthUtil._roles = xeRoles;
  }


  private static get instance() {
    const token = StorageUtil.getString(configConstant.TOKEN);
    if (StringUtil.blankOrNotEqual(AuthUtil._token, token)) {
      AuthUtil.decodeAndSaveToken(token);
      AuthUtil._user = StorageUtil.getFromJson(configConstant.USER);
    }
    return AuthUtil;
  }
  // ===========================================================
  // ===========================================================
  // ============ PUBLIC STATIC METHOD BELOW HERE

  static get token(): string {
    return AuthUtil.instance._token;
  }

  static saveResponse(response: HttpResponse<User>) {
    console.log(response);
    const token = response.headers.get(AuthConfig.tokenHeader);
    AuthUtil.decodeAndSaveToken(token);
    AuthUtil.setRepoUser(response.body);
  }

  static get user() {
    return Object.assign({}, AuthUtil.instance._user);
  }

  public static setRepoUser(user: User) {
    AuthUtil._user = Object.assign({}, user);
    StorageUtil.setItem(configConstant.USER, user);
  }

  private static setRepoToken(token: any) {
    AuthUtil._token = token;
    StorageUtil.setItem(configConstant.TOKEN, token);
  }

  static isAllow(userRoles: XeRole[]): boolean {
    return userRoles.every(r => AuthUtil.instance._roles.includes(r));
  }

  static logout(url: any = '/check-in/login') {
    AuthUtil._roles = [];
    AuthUtil.setRepoToken(null);
    AuthUtil.setRepoUser(null);
    XeRouter.navigate(url);
  }

  static isUserLoggedIn(): boolean {
    return !AuthUtil.instance.isExpired();
  }

  static get roles(): XeRole[] {
    return AuthUtil.instance._roles;
  }
}
