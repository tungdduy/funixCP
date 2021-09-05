import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthConfig} from "./auth.config";
import {Url} from "../url/url.declare";
import {AuthUtil} from "./auth.util";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {
  }

  private static handle(request: HttpRequest<any>): HttpRequest<any> {
    const token = AuthUtil.instance.token;
    return request.clone({setHeaders: {Authorization: `${AuthConfig.tokenPrefix}${token}`}});
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(request);
    return next.handle(this.needHandle(request) ? AuthInterceptor.handle(request) : request);
  }

  private needHandle = (request: HttpRequest<any>) => !Url.isPublicApi(request.url);
}
