import {User} from "../model/user";
import {HttpErrorResponse} from "@angular/common/http";
import {Subscription} from "rxjs";
import {OnDestroy, QueryList} from "@angular/core";
import {Notifier} from "../../framework/notify/notify.service";
import {XeInputComponent} from "../../framework/components/xe-input/xe-input.component";

export abstract class XeForm implements OnDestroy {

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  public onSubmit() {
    const m = {};
    const invalidNumber = this.getFormControls().filter(control => {
      m[control.name] = control.value;
      return !control.isValidateSuccess();
    }).length;
    if (invalidNumber === 0) {
      this.doSubmit(m);
    }
  }
  public isLoading: boolean = false;

  private subscriptions: Subscription[] = [];

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

}
