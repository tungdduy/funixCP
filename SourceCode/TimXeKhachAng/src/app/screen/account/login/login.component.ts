import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AuthService} from "../../../security/auth.service";
import {XeNotifierService} from "../../../service/xe-notifier.service.module";
import {AppUrl} from "../../../static/url";
import {User} from "../../../static/entity/user";
import {XeReponse} from "../../../static/model/xe-response";
import {HttpResponse} from "@angular/common/http";
import {XeRouter} from "../../../service/xe-router";
import {AppEnum} from "../../../static/app.enum";
import {AppMessages} from "../../../static/app-messages";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  public showLoading: boolean = false;
  private subscriptions: Subscription[] = [];
  public messages = AppMessages;

  constructor(private xeRouter: XeRouter,
              private notifier: XeNotifierService,
              private authService: AuthService,
              ) { }

  ngOnDestroy(): void {
        this.subscriptions.forEach(sub => sub.unsubscribe());
    }

  ngOnInit(): void {
    if(this.authService.isUserLoggedIn()) {
      this.xeRouter.navigateNow(AppUrl.DEFAULT_URL_AFTER_LOGIN);
    } else {
      this.xeRouter.navigateNow(AppUrl.LOGIN);
    }
  }

  public onLogin(user: User): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.authService.login(user).subscribe(
        (response: HttpResponse<User>) => {
          this.authService.saveResponse(response);
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
