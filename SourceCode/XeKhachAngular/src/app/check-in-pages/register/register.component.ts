import {Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {XeNotifierService} from "../../_core/service/xe-notifier.service";
import {RegisterModel} from "../../_core/static/model/register.model";
import {XeForm} from "../../_core/abstract/xe-form.abstract";
import {XeInputComponent} from "../../@theme/components/xe-input/xe-input.component";
import {XeRouter} from "../../_core/service/xe-router";
import {AuthService} from "../../_core/security/auth.service";
import {Subscription} from "rxjs";
import {AppUrl} from "../../_core/static/url";
import {User} from "../../_core/static/model/user";
import {AppMessages} from "../../_core/static/app-messages";
import {HttpErrorResponse} from "@angular/common/http";


@Component({
  selector: 'xe-register',
  styles: [],
  templateUrl: './register.component.html'
})
export class RegisterComponent extends XeForm implements  OnInit {

  @ViewChildren(XeInputComponent) formControls: QueryList<XeInputComponent>;
  getFormControls = () => this.formControls;

  private subscriptions: Subscription[] = [];
  public showLoading: boolean = false;

  constructor(private authService: AuthService,
              private xeRouter: XeRouter,
              private notifier: XeNotifierService) {
    super();
  }

  ngOnInit(): void {
    if(this.authService.isUserLoggedIn()) {
      // this.xeRouter.navigateNow(AppUrl.DEFAULT_URL_AFTER_LOGIN);
    }
  }

  doSubmitAfterBasicValidate(user: RegisterModel): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.authService.register(user).subscribe(
        (response: User) => {
          this.showLoading = false;
          this.notifier.success(AppMessages.REGISTER_ACCOUNT_SUCCESS(response.username));
          this.xeRouter.navigateNow(AppUrl.LOGIN);
        },
        (error: HttpErrorResponse) => {
          this.notifier.httpErrorResponse(error);
          this.showLoading = false;
        }
      )
    )
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}


