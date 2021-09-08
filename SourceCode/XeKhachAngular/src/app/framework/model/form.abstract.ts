import {AfterViewInit, Directive, QueryList, ViewChildren} from "@angular/core";
import {XeFormComponent} from "../components/xe-form/xe-form.component";
import {FormHandler} from "./FormHandler";
import {XeSubscriber} from "./XeSubscriber";

@Directive()
export abstract class FormAbstract extends XeSubscriber implements AfterViewInit {
  handlers: FormHandler[];
  @ViewChildren(XeFormComponent) private ___forms: QueryList<XeFormComponent>;

  constructor() {
    super();
    setTimeout(() => {
      this.adminContainer?.screen?.goHome();
    }, 0);
  }

  ngAfterViewInit(): void {
    this.assignCtrlForForms();
  }

  assignCtrlForForms() {
    this.___forms.forEach(form => {
      form.ctrl = this;
    });
  }

  showForm(formCode: string) {
    setTimeout(() => {
      const groupCode = this.getGroupCode(formCode);
      this.___forms.forEach(form => {
        if (form.name.startsWith(groupCode)) {
          form.show = form.name === formCode;
        }
      });
    }, 0);
  }

  private getGroupCode = (formCode: string) => {
    if (!formCode) return "";
    const groups = formCode.split(".");
    if (groups.length > 1) {
      return groups.join(".");
    }
    return "";
  }

}
