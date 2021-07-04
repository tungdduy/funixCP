import {Component, OnDestroy, OnInit, QueryList, ViewChildren} from '@angular/core';
import {XeNotifierService} from "../../../../framework/notify/xe.notifier.service";
import {RegisterModel} from "../../../model/register.model";
import {XeForm} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";
import {XeRouter} from "../../../service/xe-router";
import {AuthService} from "../../../../framework/auth/auth.service";
import {Subscription} from "rxjs";
import {User} from "../../../model/user";
import {AppMessages, XeLabel} from "../../../i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {Url} from "../../../../framework/url/url.declare";


@Component({
  selector: 'xe-register',
  styles: [],
  templateUrl: './register.component.html'
})
export class RegisterComponent extends XeForm implements OnInit, OnDestroy {

  @ViewChildren(XeInputComponent) formControls: QueryList<XeInputComponent>;
  getFormControls = () => this.formControls;
  label = XeLabel;

  private subscriptions: Subscription[] = [];
  public showLoading: boolean = false;

  constructor(private authService: AuthService,
              private xeRouter: XeRouter,
              private notifier: XeNotifierService) {
    super();
  }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()) {
      this.xeRouter.navigateNow(Url.DEFAULT_URL_AFTER_LOGIN());
    }
  }

  doSubmitAfterBasicValidate(user: RegisterModel): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.authService.register(user).subscribe(
        (response: User) => {
          this.showLoading = false;
          this.notifier.success(AppMessages.REGISTER_ACCOUNT_SUCCESS(response.username));
          this.xeRouter.navigateNow(Url.app.CHECK_IN.LOGIN.full);
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


