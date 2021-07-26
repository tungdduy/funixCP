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

  public forgotPassword(email: string): Observable<any> {
    return this.http.post<any>(Url.api.USER.FORGOT_PASSWORD.full, email);
  }

  public forgotPasswordSecretKey(secretInfo: any): Observable<any> {
    return this.http.post<any>(Url.api.USER.FORGOT_PASSWORD_SECRET_KEY.full, secretInfo);
  }

  public changePassword(changePasswordInfo): Observable<any> {
    return this.http.post<any>(Url.api.USER.CHANGE_PASSWORD.full, changePasswordInfo);
  }

  updateUser(data): Observable<any> {
    return this.http.post<any>(Url.api.USER.UPDATE_USER.full, data);
  }

  updatePassword(data): Observable<any> {
    return this.http.post<any>(Url.api.USER.UPDATE_PASSWORD.full, data);
  }

  updateProfileImage(formData: FormData) {
    return this.http.post<any>(Url.api.USER.UPDATE_PROFILE_IMAGE.full, formData);
  }
}
