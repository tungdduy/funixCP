import {JwtHelperService} from "@auth0/angular-jwt";
import {User} from "../../business/entities/User";
import {StringUtil} from "../util/string.util";
import {StorageUtil} from "../util/storage.util";
import {configConstant} from "../config.constant";
import {HttpResponse} from "@angular/common/http";
import {AuthConfig} from "./auth.config";
import {Role} from "../../business/xe.role";
import {RoleUtil} from "../util/role.util";
import {ObjectUtil} from "../util/object.util";

export class AuthUtil {
  private _jwtHelper = new JwtHelperService();
  private _token: string | null | undefined;
  private _roles: Role[] = [];
  private _flatRoles: Role[] = [];
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

  private convertToAppRoles(apiAuthorities: string[]): Role[] {
    const roles: Role[] = [];
    apiAuthorities.forEach(authority => {
      if (authority.startsWith("ROLE_")) {
        roles.push(Role[authority]);
      }
    });
    return roles;
  }

  private setRoles = (roles: Role[]) => {
    this._roles = roles;
    this._flatRoles = RoleUtil.flatRoles(roles);
  }

  private static _instance: AuthUtil;
  public static get instance() {
    if (this._instance === undefined) {
      this._instance = new AuthUtil();
      const token = StorageUtil.getString(configConstant.TOKEN);
      this._instance.decodeAndSaveToken(token);
      this._instance.setRepoUser(StorageUtil.getFromJson(configConstant.USER));
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
    if (!ObjectUtil.isObject(this._user)) {
      this._user = new User();
    }
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

  isAllow(userRoles: Role[]): boolean {
    if (!userRoles || userRoles.length === 0) return true;
    for (const role of userRoles) {
      if (this.flatRoles.includes(role)) {
        return true;
      }
    }
    return false;
  }

  logout(url: any = '/admin/find-trips') {
    this.setRoles([]);
    this.setRepoToken(null);
    this.setRepoUser(null);
    AuthUtil._instance = undefined;
    location.reload();
  }

  get isUserLoggedIn(): boolean {
    return !this.isExpired();
  }

  get roles(): Role[] {
    return this._roles;
  }
  get flatRoles(): Role[] {
    return this._flatRoles;
  }
}
