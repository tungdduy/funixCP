import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AuthService} from "../../../security/auth.service";
import {Notifier} from "../../../service/notifier";
import {AppUrl} from "../../../static/url";
import {User} from "../../../static/entity/user";
import {HeaderType} from "../../../static/header-type.enum";
import {XeReponse} from "../../../static/model/xe-response";
import {HttpResponse} from "@angular/common/http";
import {XeRouter} from "../../../service/xe-router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  public showLoading: boolean = false;
  private subscriptions: Subscription[] = [];

  constructor(private xeRouter: XeRouter,
              private notifier: Notifier,
              private authenticator: AuthService) { }

  ngOnDestroy(): void {
        this.subscriptions.forEach(sub => sub.unsubscribe());
    }

  ngOnInit(): void {
    if(this.authenticator.isUserLoggedIn()) {
      this.xeRouter.navigateNow(AppUrl.DEFAULT_URL_AFTER_LOGIN);
    } else {
      this.xeRouter.navigateNow(AppUrl.LOGIN);
    }
  }

  public onLogin(user: User): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.authenticator.login(user).subscribe(
        (response: HttpResponse<User>) => {
          const token = response.headers.get(HeaderType.JWT_TOKEN);
          if (token != null) {
            this.authenticator.saveToken(token);
          }
          this.authenticator.addUserToLocalCache(response.body);
          this.xeRouter.navigateNow(AppUrl.DEFAULT_URL_AFTER_LOGIN);
          this.showLoading = false;
        },
        (errorResponse: XeReponse) => {
          this.notifier.errorReponse(errorResponse);
          this.showLoading = false;
        }
      )
    )
  }

}
