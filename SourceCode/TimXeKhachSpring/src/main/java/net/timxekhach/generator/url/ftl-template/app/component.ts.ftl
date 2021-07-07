import {Component, OnDestroy, OnInit, ViewChildren} from '@angular/core';
import {XeForm} from "${component.pathToAbstract}abstract/xe-form.abstract";
import {XeInputComponent} from "${component.pathToFramework}framework/components/xe-input/xe-input.component";
import {Subscription} from "rxjs";
import {XeLabel} from "${component.pathToI18n}i18n";

@Component({
  selector: 'xe-${component.url}',
  styles: [],
  templateUrl: '${component.url}.component.html',
})
export class ${component.componentName} extends XeForm implements OnInit, OnDestroy {
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
