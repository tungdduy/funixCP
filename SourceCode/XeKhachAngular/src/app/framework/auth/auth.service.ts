import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../business/model/user";
import {JwtHelperService} from "@auth0/angular-jwt";
import {StorageUtil} from "../util/storage.util";
import {AuthConfig} from "./auth.config";
import {RegisterModel} from "../../business/model/register.model";
import {StringUtil} from "../util/string.util";
import {config} from "../config";
import {Role} from "./role.model";
import {XeRole} from "../../business/xe.role";
import {Authority} from "../../business/auth.enum";
import {Url} from "../../business/url.declare";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private static _jwtHelper = new JwtHelperService();
  private static _token: string | null | undefined;
  private static _authorities: Authority[];
  private static _roles: Role[];

  get authorities(): Authority[] {
    return AuthService._authorities;
  }

  get roles(): Role[] {
    return AuthService._roles;
  }

  constructor(private http: HttpClient) {
  }

  public login(user: User): Observable<HttpResponse<User>> | any {
    return this.http.post<User>(Url.api.USER.LOGIN.full, user, {observe: 'response'});
  }

  public register(user: RegisterModel): Observable<User> {
    return this.http.post<User>(Url.api.USER.REGISTER.full, user);
  }

  isUserLoggedIn(): boolean {
    const token = StorageUtil.getString(config.TOKEN);
    if (StringUtil.blankOrNotEqual(AuthService._token, token)) {
      return this.decodeAndSaveToken(token);
    }
    return !this.isExpired();
  }

  private isExpired(): boolean {
    if (AuthService._jwtHelper.isTokenExpired(AuthService._token)) {
      this.logOut();
      return true;
    }
    return false;
  }

  private decodeAndSaveToken(token: string): boolean {

    if (StringUtil.isBlank(token)) return false;

    const tokenContent = AuthService._jwtHelper.decodeToken(token);
    if (StringUtil.isNotBlank(tokenContent?.sub)) {
      if (!AuthService._jwtHelper.isTokenExpired(token)) {
        this.setRepoToken(token);
        this.setRoles(this.convertRoleFromApiToApp(tokenContent.roles));
        this.setAuthorities(this.convertAuthoritiesFromApiToApp(tokenContent.authorities));
        return true;
      }
    }
    this.logOut();
    return false;
  }

  private convertAuthoritiesFromApiToApp(apiAuthorities: string[]): Authority[] {
    const appAuthorities: Authority[] = [];
    apiAuthorities.forEach(authority => {
      appAuthorities.push(Authority[authority]);
    });
    return appAuthorities;
  }

  private convertRoleFromApiToApp(roles: string[]): Role[] {
    const appRoles = [];
    roles.forEach(role => {
      const converted = role.replace("ROLE_", "").toUpperCase();
      appRoles.push(XeRole[converted]);
    });
    return appRoles;
  }

  private setRoles = (xeRoles: Role[]) => {
    AuthService._roles = xeRoles;
  }
  private setAuthorities = (authorities: Authority[]) => {
    AuthService._authorities = authorities;
  }

  public logOut() {
    this.setRepoUser(null);
    this.setRepoToken(null);
  }

  get token(): string {
    if (!AuthService._token) {
      this.decodeAndSaveToken(this.repoToken);
    }
    return AuthService._token;
  }

  saveResponse(response: HttpResponse<User>) {
    const token = response.headers.get(AuthConfig.tokenHeader);
    this.decodeAndSaveToken(token);
    this.setRepoUser(response.body);
  }

  get repoUser() {
    return StorageUtil.getString(config.USER);
  }

  setRepoUser(user: User) {
    StorageUtil.setItem(config.USER, user);
  }

  get repoToken() {
    return StorageUtil.getString(config.TOKEN);
  }

  setRepoToken(token: any) {
    AuthService._token = token;
    StorageUtil.setItem(config.TOKEN, token);
  }


}
