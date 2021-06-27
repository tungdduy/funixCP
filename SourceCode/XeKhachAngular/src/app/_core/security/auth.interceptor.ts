import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "./auth.service";
import {ApiUrl} from "../static/url";
import {AppEnum} from "../static/app.enum";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(this.needHandle(request) ? this.handle(request) : request);
  }

  private needHandle = (request: HttpRequest<any> ) =>
    !(request.url.includes(ApiUrl.LOGIN) || request.url.includes(ApiUrl.REGISTER))

  private handle(request: HttpRequest<any>): HttpRequest<any> {
    const token = this.authService.loadThenGetToken();
    return request.clone({setHeaders: {Authorization: `${AppEnum.tokenPrefix}${token}` }});
  }
}
