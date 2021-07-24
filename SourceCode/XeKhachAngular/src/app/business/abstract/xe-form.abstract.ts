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

  public onSubmit(formCode: string) {
    const m = {};
    const invalidNumber = this.getFormControls(formCode).filter(control => {
      m[control.name] = control.value;
      return control.validateFailed();
    }).length;
    if (invalidNumber === 0) {
      if (!this.getObservable(m, formCode)) {
        Notifier.error(AppMessages.DEFAULT_ERROR_MESSAGE);
        return;
      }
      this.doSubmit(m, formCode);
    }
    return false;
  }
  public isLoading: boolean = false;

  protected subscriptions: Subscription[] = [];

  doSubmit(model: any, formCode = null): void {
    this.isLoading = true;
    this.subscriptions.push(
      this.getObservable(model, formCode).subscribe(
        (response: User) => {
          this.isLoading = false;
          this.onSubmitSuccess(response, formCode);
        },
        (error: HttpErrorResponse) => {
          this.isLoading = false;
          Notifier.httpErrorResponse(error);
        }
      )
    );
  }
  abstract getObservable(model: any, formCode: any);
  abstract onSubmitSuccess(response: any, formCode: any);
  abstract getFormControls(formCode: any): QueryList<XeInputComponent>;

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
