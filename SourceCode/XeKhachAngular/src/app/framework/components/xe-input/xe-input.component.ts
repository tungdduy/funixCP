import {Component, Input} from '@angular/core';
import {ObjectUtil} from "../../util/object.util";
import {RegexUtil} from "../../util/regex.util";
import {AppMessages, XeLabel, XeLbl} from "../../../business/i18n";
import {StringUtil} from "../../util/string.util";

@Component({
  selector: 'xe-input',
  templateUrl: './xe-input.component.html',
  styleUrls: ['./xe-input.component.scss']
})
export class XeInputComponent {

  @Input() type: string = "text";
  @Input() lblKey: string;
  @Input() id: string;
  @Input() required?: any;
  @Input() validatorMsg?: string;
  @Input() minLength?: bigint;
  @Input() maxLength?: bigint;
  @Input() matching?: any;
  @Input() name?: string;
  @Input() disabled?: any;
  @Input() grid?: any;
  @Input() value: string;
  public errorMessage?: string;

  private _label: string;

  public get label() {
    if (!this._label) {
      if (!this.lblKey) this.lblKey = this.getName();
      this._label = XeLbl(this.lblKey);
    }
    return this._label;
  }

  get isGrid() {
    return this.grid === '';
  }

  get isRequire() {
    return this.required === '';
  }

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

  labelHiddenClass() {
    return this.value
    && this.value.length > 0
    || this.isGrid ? '' : 'd-none';
  }

  isValidateSuccess(): boolean {
    if (this.isRequire &&
      (!this.value || this.value.trim().length === 0)) {
      this.errorMessage = AppMessages.PLEASE_INPUT(this.label);
      return;
    }

    if (this.minLength && this.value.length < this.minLength) {
      this.errorMessage = AppMessages.FIELD_MUST_HAS_AT_LEAST_CHAR(this.label, this.minLength);
      return;
    }

    if (this.maxLength && this.value.length > this.maxLength) {
      this.errorMessage = AppMessages.MAXIMUM_LENGTH_OF_FIELD(this.label, this.maxLength);
      return;
    }

    if (this.type === 'email' && !RegexUtil.isValidEmail(this.value)) {
      this.errorMessage = AppMessages.EMAIL_NOT_VALID;
      return;
    }

    if (this.matching) {
      if (this.matching instanceof XeInputComponent && this.matching.value !== this.value) {
        this.errorMessage = AppMessages.FIELD_NOT_MATCH(this.label);
        return;
      }

      if (this.matching instanceof RegExp && !this.matching.test(this.value)) {
        this.errorMessage = AppMessages.INVALID_FIELD(this.label);
        return;
      }

      if (ObjectUtil.isString(this.matching) && this.matching !== this.value) {
        this.errorMessage = AppMessages.FIELD_NOT_MATCH(this.matching);
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

}

