import {Component, OnDestroy, OnInit, ViewChildren} from '@angular/core';
import {XeForm} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";
import {Subscription} from "rxjs";
import {XeLabel} from "../../../i18n";

@Component({
  selector: 'xe-register',
  styles: [],
  templateUrl: 'register.component.html',
})
export class RegisterComponent extends XeForm implements OnInit, OnDestroy {
  public showLoading: boolean;
  private subscriptions: Subscription[] = [];
  label = XeLabel;
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
  @ViewChildren(XeInputComponent) formControls;
  getFormControls = () => this.formControls;

  doSubmitAfterBasicValidate(model: any): void {
  }

  ngOnInit(): void {
  }

  constructor() {
    super();
  }
}
