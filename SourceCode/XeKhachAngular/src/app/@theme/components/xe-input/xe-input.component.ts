import {Component, Input} from '@angular/core';

@Component({
  selector: 'xe-input',
  templateUrl: './xe-input.component.html',
  styleUrls: ['./xe-input.component.scss']
})
export class XeInputComponent {

  @Input() type: string = "text";
  @Input() label: string = "";
  @Input() id: string;
  @Input() required: boolean = true;
  @Input() validatorMsg?: string;
  @Input() minLength?: bigint;
  @Input() maxLength?: bigint;
  @Input() matching?: any;
  @Input() name?: string;
  @Input() disabled?: any;

  value: string;

  getId(): string {
    if (!this.id) {
      this.id = Math.random().toString(36).substring(7);
    }
    return this.id;
  }

  getName(): string {
    if (!this.name) {
      this.name = this.type;
    }
    return this.name;
  }

  public errorMessage?: string;

  isShowLabel() {
    return this.value
      && this.value.length > 0;
  }

  validate(): boolean {
    if (this.required &&
            (!this.value || this.value.trim().length === 0)) {
      this.errorMessage = `Vui lòng nhập ${this.label}`;
      return;
    }

    if (this.minLength && this.value.length < this.minLength) {
      this.errorMessage = `${this.label} cần có ít nhất ${this.minLength} ký tự`;
      return;
    }

    if (this.maxLength && this.value.length > this.maxLength) {
      this.errorMessage = `${this.label} không vượt quá ${this.maxLength} ký tự`;
      return;
    }

    if (this.type === 'email' && !this.isValidEmail(this.value)) {
      this.errorMessage = `Email không hợp lệ`;
      return;
    }

    if (this.matching) {
      if (this.matching instanceof XeInputComponent && this.matching.value !== this.value) {
        this.errorMessage = `${this.matching.label} không khớp`;
        return;
      }

      if (this.matching instanceof RegExp && !this.matching.test(this.value)) {
        this.errorMessage = `${this.label} không hợp lệ`;
        return;
      }

      if (typeof(this.matching) === 'string' && this.matching !== this.value) {
        this.errorMessage = `${this.matching} không khớp`;
        return;
      }
    }

    if (this.validatorMsg) {
      this.errorMessage = this.validatorMsg;
      return;
    }

    this.errorMessage = undefined;
    return true;
  }

  static emailPattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  isValidEmail(email): boolean {
    return XeInputComponent.emailPattern.test(String(email).toLowerCase());
  }

}

