import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "./auth.service";
import {AuthConfig} from "./auth.config";
import {Url} from "../url/url.declare";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(this.needHandle(request) ? AuthInterceptor.handle(request) : request);
  }

  private needHandle = (request: HttpRequest<any>) => !Url.isPublicApi(request.url);

  private static handle(request: HttpRequest<any>): HttpRequest<any> {
    const token = AuthService.token;
    return request.clone({setHeaders: {Authorization: `${AuthConfig.tokenPrefix}${token}`}});
  }
}
