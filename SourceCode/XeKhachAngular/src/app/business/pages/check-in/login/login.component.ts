import {Component, OnDestroy, OnInit, ViewChildren} from '@angular/core';
import {XeNotifierService} from "../../../../framework/notify/xe.notifier.service";
import {XeForm} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";
import {AuthService} from "../../../../framework/auth/auth.service";
import {XeRouter} from "../../../service/xe-router";
import {Subscription} from "rxjs";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {User} from "../../../model/user";
import {XeLabel} from "../../../i18n/xe-label";
import {Url} from "../../../url.declare";

@Component({
  selector: 'xe-login',
  styles: [],
  templateUrl: 'login.component.html',
})
export class LoginComponent extends XeForm implements OnInit, OnDestroy {

  public showLoading: boolean;
  private subscriptions: Subscription[] = [];
  label = XeLabel;

  @ViewChildren(XeInputComponent) formControls;
  getFormControls = () => this.formControls;
  constructor(private authService: AuthService,
              private notifier: XeNotifierService,
              private router: XeRouter) {
    super();
  }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()) {
      this.router.navigateNow(Url.DEFAULT_URL_AFTER_LOGIN());
    }
  }

  doSubmitAfterBasicValidate(model: any): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.authService.login(model).subscribe(
        (response: HttpResponse<User>) => {
          this.authService.saveResponse(response);
          this.showLoading = false;
          this.router.navigateNow(Url.DEFAULT_URL_AFTER_LOGIN());
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
