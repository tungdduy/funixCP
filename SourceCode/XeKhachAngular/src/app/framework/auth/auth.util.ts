import {JwtHelperService} from "@auth0/angular-jwt";
import {XeRole} from "../../business/constant/xe.role";
import {User} from "../../business/entities/user";
import {StringUtil} from "../util/string.util";
import {StorageUtil} from "../util/storage.util";
import {configConstant} from "../config.constant";
import {HttpResponse} from "@angular/common/http";
import {AuthConfig} from "./auth.config";
import {XeRouter} from "../../business/service/xe-router";

export class AuthUtil {
  private _jwtHelper = new JwtHelperService();
  private _token: string | null | undefined;
  private _roles: XeRole[] = [];
  private _user: User;

  private isExpired(): boolean {
    return this._jwtHelper.isTokenExpired(this.token);
  }

  private decodeAndSaveToken(token: string) {
    if (StringUtil.isBlank(token)) return false;
    const tokenContent = this._jwtHelper.decodeToken(token);
    if (StringUtil.isNotBlank(tokenContent?.sub)) {
      if (!this._jwtHelper.isTokenExpired(token)) {
        this.setRepoToken(token);
        this.setRoles(this.convertToAppRoles(tokenContent.authorities));
        return;
      }
    }
    this.logout();
  }

  private convertToAppRoles(apiAuthorities: string[]): XeRole[] {
    const roles: XeRole[] = [];
    apiAuthorities.forEach(authority => {
      if (authority.startsWith("ROLE_")) {
        roles.push(XeRole[authority]);
      }
    });
    return roles;
  }

  private setRoles = (xeRoles: XeRole[]) => {
    this._roles = xeRoles;
  }

  private static _instance: AuthUtil;
  public static get instance() {
    if (this._instance === undefined) {
      this._instance = new AuthUtil();
      const token = StorageUtil.getString(configConstant.TOKEN);
      this._instance.decodeAndSaveToken(token);
      this._instance._user = StorageUtil.getFromJson(configConstant.USER);
    }
    return this._instance;
  }
  // ===========================================================
  // ===========================================================
  // ============ PUBLIC STATIC METHOD BELOW HERE

  get token(): string {
    return this._token;
  }

  saveResponse(response: HttpResponse<User>) {
    const token = response.headers.get(AuthConfig.tokenHeader);
    this.decodeAndSaveToken(token);
    this.setRepoUser(response.body);
  }

  get user() {
    return this._user;
  }

  public setRepoUser(user: User) {
    this._user = user;
    StorageUtil.setItem(configConstant.USER, user);
  }

  private setRepoToken(token: any) {
    this._token = token;
    StorageUtil.setItem(configConstant.TOKEN, token);
  }

  isAllow(userRoles: XeRole[]): boolean {
    return userRoles.every(r => this._roles.includes(r));
  }

  logout(url: any = '/check-in/login') {
    this._roles = [];
    this.setRepoToken(null);
    this.setRepoUser(null);
    XeRouter.navigate(url);
  }

  get isUserLoggedIn(): boolean {
    return !this.isExpired();
  }

  get roles(): XeRole[] {
    return this._roles;
  }
}
