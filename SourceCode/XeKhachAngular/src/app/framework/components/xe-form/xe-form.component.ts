import {Component, ContentChildren, Input, OnDestroy} from '@angular/core';
import {Subscription} from "rxjs";
import {XeInputComponent} from "../xe-input/xe-input.component";
import {FormAbstract} from "../../../business/abstract/form.abstract";
import {Notifier} from "../../notify/notify.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'xe-form',
  templateUrl: './xe-form.component.html',
  styleUrls: ['./xe-form.component.scss']
})
export class XeFormComponent implements OnDestroy {

  @Input() name;
  @Input() hide;
  get show() {
    if (this.hide === undefined) {
      return true;
    }
    if (this.hide === '') {
      return false;
    }
    return this.hide;
  }
  set show(value) {
    this.hide = value;
  }
  @ContentChildren(XeInputComponent) formControls;
  ctrl: FormAbstract;
  isLoading: boolean = false;

  public onSubmit(htmlForm: HTMLFormElement) {

    const formName = htmlForm.name;
    let model = {};

    const invalidNumber = this.formControls.filter(control => {
      model[control.name] = control.value;
      return control.validateFailed();
    }).length;

    if (Object.keys(model).length === 1) {
      model = this.formControls.first.value;
    }

    if (invalidNumber === 0) {
      const forms = this.ctrl.handlers();
      let form = forms[formName];
      if (!form) form = forms;

      const processor = form['processor'];
      if (!processor) {
        return;
      }

      this.isLoading = true;
      this.subscriptions.push(
        processor(model).subscribe(
          (response: any) => {
            this.isLoading = false;
            form.success(response);
          },
          (error: HttpErrorResponse) => {
            this.isLoading = false;
            Notifier.httpErrorResponse(error);
          }
        )
      );
    }
    return false;
  }

  protected subscriptions: Subscription[] = [];

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
