import {Component, OnDestroy, OnInit, ViewChildren} from '@angular/core';
import {XeForm} from "${root.pathToAbstract}abstract/xe-form.abstract";
import {XeInputComponent} from "${root.pathToFramework}framework/components/xe-input/xe-input.component";
import {Subscription} from "rxjs";
import {XeLabel} from "${root.pathToI18n}i18n";

@Component({
  selector: 'xe-${root.url}',
  styles: [],
  templateUrl: '${root.url}.component.html',
})
export class ${root.componentName} extends XeForm implements OnInit, OnDestroy {
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
