export abstract class XeForm {

  public onSubmit() {
    const m = {};
    const invalidNumber = this.getFormControls().filter(control => {
      m[control.name] = control.value;
      return !control.validate();
    }).length;
    if (invalidNumber === 0) {
      this.doSubmitAfterBasicValidate(m);
    }
  }

  abstract getFormControls();
  abstract doSubmitAfterBasicValidate(model: any): void;

}
