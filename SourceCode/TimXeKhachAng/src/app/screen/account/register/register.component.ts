import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../../security/auth.service";
import {User} from "../../../static/entity/user";
import {Subscription} from "rxjs";
import {AppUrl} from "../../../static/url";
import {AppMessages} from "../../../static/app-messages";
import {Notifier} from "../../../service/notifier";
import {XeReponse} from "../../../static/model/xe-response";
import {XeRouter} from "../../../service/xe-router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {

  private subscriptions: Subscription[] = [];
  public showLoading: boolean = false;

  constructor(private authenticationService: AuthService,
              private xeRouter: XeRouter,
              private notifier: Notifier) {}

  ngOnInit(): void {
    if(this.authenticationService.isUserLoggedIn()) {
      this.xeRouter.navigateNow(AppUrl.DEFAULT_URL_AFTER_LOGIN);
    }
  }

  public onRegister(user: User): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.authenticationService.register(user).subscribe(
        (response: User) => {
          this.showLoading = false;
          this.notifier.success(AppMessages.REGISTER_ACCOUNT_SUCCESS(response.username));
        },
        (errorResponse: XeReponse) => {
          this.notifier.errorReponse(errorResponse);
          this.showLoading = false;
        }
      )
    )
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
