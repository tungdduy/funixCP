import {User} from "../model/user";
import {HttpErrorResponse} from "@angular/common/http";
import {Subscription} from "rxjs";
import {Component, OnDestroy, QueryList} from "@angular/core";
import {Notifier} from "../../framework/notify/notify.service";
import {XeInputComponent} from "../../framework/components/xe-input/xe-input.component";
import {AppMessages} from "../i18n";

@Component({
  template: '',
})
export abstract class XeFormComponent implements OnDestroy {

  public onSubmit() {
    const m = {};
    const invalidNumber = this.getFormControls().filter(control => {
      m[control.name] = control.value;
      return !control.isValidateSuccess();
    }).length;
    if (invalidNumber === 0) {
      if (!this.getObservable(m)) {
        Notifier.error(AppMessages.DEFAULT_ERROR_MESSAGE);
        return;
      }
      this.doSubmit(m);
    }
    return false;
  }
  public isLoading: boolean = false;

  protected subscriptions: Subscription[] = [];

  doSubmit(model: any): void {
    this.isLoading = true;
    this.subscriptions.push(
      this.getObservable(model).subscribe(
        (response: User) => {
          this.isLoading = false;
          this.onSubmitSuccess(response);
        },
        (error: HttpErrorResponse) => {
          this.isLoading = false;
          Notifier.httpErrorResponse(error);
        }
      )
    );
  }
  abstract getObservable(model: any);
  abstract onSubmitSuccess(response: any);
  abstract getFormControls(): QueryList<XeInputComponent>;

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
