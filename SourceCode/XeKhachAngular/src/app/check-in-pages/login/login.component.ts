import {Component, OnInit, ViewChildren} from '@angular/core';
import {XeNotifierService} from "../../_core/service/xe-notifier.service";
import {XeForm} from "../../_core/abstract/xe-form.abstract";
import {XeInputComponent} from "../../@theme/components/xe-input/xe-input.component";
import {AuthService} from "../../_core/security/auth.service";
import {XeRouter} from "../../_core/service/xe-router";
import {Subscription} from "rxjs";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {User} from "../../_core/static/model/user";
import {AppUrl} from "../../_core/static/url";

@Component({
  selector: 'xe-login',
  styles: [],
  templateUrl: 'login.component.html',
})
export class LoginComponent extends XeForm implements OnInit {

  public showLoading: boolean;
  private subscriptions: Subscription[] = [];

  @ViewChildren(XeInputComponent) formControls;
  getFormControls = () => this.formControls;
  constructor(private authService: AuthService,
              private notifier: XeNotifierService,
              private router: XeRouter) {
    super();
  }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()){
      this.router.navigateNow(AppUrl.DEFAULT_URL_AFTER_LOGIN);
    }
  }

  doSubmitAfterBasicValidate(model: any): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.authService.login(model).subscribe(
        (response: HttpResponse<User>) => {
          this.authService.saveResponse(response);
          this.showLoading = false;
          this.router.navigateNow(AppUrl.DEFAULT_URL_AFTER_LOGIN);
        },
        (error: HttpErrorResponse) => {
          this.notifier.httpErrorResponse(error);
          this.showLoading = false;
        }
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
