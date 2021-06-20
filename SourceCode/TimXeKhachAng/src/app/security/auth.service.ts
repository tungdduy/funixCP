import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../static/entity/user";
import { JwtHelperService } from "@auth0/angular-jwt";
import {ApiUrl} from "../static/url";
import {StorageService} from "../service/storage.service";
import {AppEnum} from "../static/app.enum";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _jwtHelper = new JwtHelperService();
  private _token: string | null | undefined;
  private _currentUsername!: string | null;

  constructor(private http: HttpClient, private storage: StorageService) { }

  public login(user: User): Observable<HttpResponse<User>> | any {
    return this.http.post<User>(ApiUrl.LOGIN, user, {observe: 'response'});
  }

  public register(user: User): Observable<User> {
    return this.http.post<User>(ApiUrl.REGISTER, user);
  }

  isUserLoggedIn() {
    this._token = this.storage.token;
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
    this.storage.user = null;
    this.storage.users = null;
    this.storage.token = null;
  }

  public loadThenGetToken() {
    this._token = this.storage.token;
    return this._token;
  }

  saveResponse(response: HttpResponse<User>) {
    const token = response.headers.get(AppEnum.tokenHeader);
    this._token = token;
    this.storage.token = token;
    this.storage.user = response.body;
  }
}
