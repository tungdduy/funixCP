import {Component, Input, EventEmitter, Output, AfterViewInit} from '@angular/core';
import {ObjectUtil} from "../../util/object.util";
import {RegexUtil} from "../../util/regex.util";
import {AppMessages, XeLbl} from "../../../business/i18n";
import {StringUtil} from "../../util/string.util";


@Component({
  selector: 'xe-input',
  templateUrl: './xe-input.component.html',
  styleUrls: ['./xe-input.component.scss']
})
export class XeInputComponent implements AfterViewInit {
  _originValue;
  ngAfterViewInit(): void {
      this._originValue = this.value;
      if (this.name?.includes("password")) {
        setTimeout(() => {
          this.type = "password";
        }, 0);
      }
  }

  get isChanged() {
    return this._originValue !== this.value;
  }

  @Input() type: string = "text";
  @Input() lblKey: string;
  @Input() id: string;
  @Input() required: any;
  @Input() validatorMsg?: string;
  @Input() minLength?: bigint;
  @Input() maxLength?: bigint;
  @Input() matching?: any;
  @Input() name?: string;
  @Input() _disabled;
  get disabled() {
    return this._disabled === false || this._disabled === undefined;
  }
  set disabled(val) {
    this.errorMessage = undefined;
    this._disabled = val;
  }
  @Input() grid?: any;
  @Input() get value() {
    return this._value;
  }
  set value(val) {
    this._value = val;
    this.valueChange.emit(this._value);
  }
  _value: string;
  @Output() valueChange = new EventEmitter<any>();
  @Input() icon?;
  public errorMessage?: string;

  private _label: string;

  get isShowError() {
    return !this.disabled && !!this.errorMessage;
  }

  public get hint() {
    if (this.disabled) {
      return XeLbl('NO_VALUE');
    } else {
      return XeLbl('PLEASE_INPUT');
    }
  }

  public get label() {
    if (!this._label) {
      if (!this.lblKey) this.lblKey = this.getName();
      this._label = XeLbl(this.lblKey);
    }
    return this._label;
  }

  public get placeHolder() {
    if (this.isGrid) {
      return XeLbl(this.hint);
    }
    return this.label;
  }

  icons = {
    email: 'envelope',
    username: 'id-card',
    password: 'key',
    fullName: 'user',
    phoneNumber: 'mobile-alt',
  };

  getIcon() {
    if (!this.icon) {
      this.icon = this.icons[this.type] ? this.icons[this.type]
      : this.icons[this.name] ? this.icons[this.name]
          : this.fetchAnyPossibleIconFromName();
    }
    return this.icon;
  }

  get isGrid() {
    return this.grid === '' || this.grid === true;
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

  validateFailed() {
    return !this.isValidateSuccess();
  }

  get isRequire() {
    return this.required === '' || this.required === true || this.minLength || this.maxLength || this.matching;
  }

  isValidateSuccess(): boolean {
    if (this.isRequire &&
      (StringUtil.isBlank(this.value))) {
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

    if (this.getName().toLowerCase().includes("phone") && !RegexUtil.isValidPhone(this.value)) {
      this.errorMessage = AppMessages.PHONE_NOT_VALID;
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

  private fetchAnyPossibleIconFromName() {
    for (const key in this.icons) {
      if (this.getName().toLowerCase().includes(key)) {
        return this.icons[key];
      }
    }
    return 'book-open';
  }

  reset() {
    this.value = this._originValue;
  }
  update() {
    this._originValue = this.value;
  }
}

