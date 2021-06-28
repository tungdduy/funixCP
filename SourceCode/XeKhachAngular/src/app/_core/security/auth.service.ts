import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../static/model/user";
import {JwtHelperService} from "@auth0/angular-jwt";
import {StorageService} from "../service/storage.service";
import {AppEnum} from "../static/app.enum";
import {RegisterModel} from "../static/model/register.model";
import {StringUtil} from "../static/utils/xe-string.util";
import {XeUrl} from "../static/url.declare";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private static _jwtHelper = new JwtHelperService();
  private static _token: string | null | undefined;
  private static _authorities: [];

  get authorities(): [] {
    return AuthService._authorities;
  }

  constructor(private http: HttpClient, private storage: StorageService) {
  }

  public login(user: User): Observable<HttpResponse<User>> | any {
    return this.http.post<User>(XeUrl.full.api.USER.LOGIN, user, {observe: 'response'});
  }

  public register(user: RegisterModel): Observable<User> {
    return this.http.post<User>(XeUrl.full.api.USER.REGISTER, user);
  }

  isUserLoggedIn(): boolean {
    if (StringUtil.blankOrNotEqual(AuthService._token, this.storage.token)) {
      return this.decodeToken(this.storage.token);
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

  private decodeToken(token: string): boolean {

    if (StringUtil.isBlank(token)) return false;

    const tokenContent = AuthService._jwtHelper.decodeToken(token);
    if (StringUtil.isNotBlank(tokenContent?.sub)) {
      if (!AuthService._jwtHelper.isTokenExpired(token)) {
        AuthService._token = token;
        AuthService._authorities = tokenContent.authorities;
        return true;
      }
    }
    this.logOut();
    return false;
  }

  private logOut() {
    AuthService._token = null;
    this.storage.user = null;
    this.storage.token = null;
  }

  public loadThenGetToken() {
    AuthService._token = this.storage.token;
    return AuthService._token;
  }

  saveResponse(response: HttpResponse<User>) {
    const token = response.headers.get(AppEnum.tokenHeader);
    AuthService._token = token;
    this.storage.token = token;
    this.storage.user = response.body;
  }
}
