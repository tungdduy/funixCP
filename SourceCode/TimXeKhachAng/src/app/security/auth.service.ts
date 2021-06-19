import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../static/entity/user";
import { JwtHelperService } from "@auth0/angular-jwt";
import {constant} from "../static/constant";
import {ApiUrl} from "../static/url";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _jwtHelper = new JwtHelperService();
  private _token!: string | null;
  private _currentUsername!: string | null;

  constructor(private http: HttpClient) { }

  public login(user: User): Observable<HttpResponse<User>> | any {
    return this.http.post<User>(ApiUrl.LOGIN, user, {observe: 'response'});
  }

  public register(user: User): Observable<User> {
    return this.http.post<User>(ApiUrl.REGISTER, user);
  }

  public loadToken(): void {
    this._token = localStorage.getItem(constant.TOKEN);
  }

  public saveToken(token: string): void {
    this._token = token;
    localStorage.setItem(constant.TOKEN, token);
  }

  public addUserToLocalCache(user: User | null): void {
    localStorage.setItem(constant.USER, JSON.stringify(user));
  }

  isUserLoggedIn() {
    this.loadToken();
    if(this._token != null && this._token !== '') {
      if(this._jwtHelper.decodeToken(this._token).sub != null || '') {
        if(!this._jwtHelper.isTokenExpired(this._token)) {
          this._currentUsername = this._jwtHelper.decodeToken(this._token).sub;
          return true;
        }
      }
    }
    this.logOut();
    return false;
  }

  get token(): any {
    return this._token;
  }

  private logOut() {
    this._token = null;
    this._currentUsername = null;
    localStorage.removeItem(constant.USER);
    localStorage.removeItem(constant.TOKEN);
    localStorage.removeItem(constant.USERS);
  }

  public loadThenGetToken() {
    this.loadToken();
    return this._token;
  }
}
