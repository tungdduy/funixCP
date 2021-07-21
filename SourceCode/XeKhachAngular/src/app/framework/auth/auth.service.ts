import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../business/model/user";
import {RegisterModel} from "../../business/model/register.model";
import {Url} from "../url/url.declare";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  public login(user: User): Observable<HttpResponse<User>> | any {
    return this.http.post<User>(Url.api.USER.LOGIN.full, user, {observe: 'response'});
  }

  public register(user: RegisterModel): Observable<User> {
    return this.http.post<User>(Url.api.USER.REGISTER.full, user);
  }
}
