import {AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {ObjectUtil} from '../../util/object.util';
import {RegexUtil} from '../../util/regex.util';
import {AppMessages, XeLbl} from '../../../business/i18n';
import {StringUtil} from '../../util/string.util';
import {SelectItem} from '../../model/SelectItem';
import {InputMode, InputTemplate} from "../../model/EnumStatus";
import {Observable, of} from "rxjs";
import {XeTimePipe} from "../pipes/time.pipe";
import {AbstractXe} from "../../model/AbstractXe";
import {MultiOptionUtil} from "../../model/EntityEnum";


@Component({
  selector: 'xe-input',
  templateUrl: './xe-input.component.html',
  styleUrls: ['./xe-input.component.scss'],
})
export class XeInputComponent extends AbstractXe implements AfterViewInit {
  _originValue;

  @ViewChild("htmlInput") htmlInput: ElementRef;

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.init();
    }, 0);
  }

  init() {
    this._originValue = this.appValue;
    if (this.name?.toLowerCase().includes('password')) {
      this.type = 'password';
    }
    if (this.template.isTime) {
      this.timeOptions$ = of(XeTimePipe.allHourOptions());
    }
  }

  onShortInputBlur($event: any) {
    if (this.template?.pipe?.toAppFormat && this.htmlInput?.nativeElement) {
      $event.target.value = this.template?.pipe.toReadableString(this.value);
    }
  }

  onShortInputFocus($event: any) {
    $event.target.value = this.template?.pipe ? this.template.pipe.toRawInputString(this._value) : this._value;
  }

  get isChanged() {
    return (!this._value && this._originValue)
      || (this._value && !this._originValue)
      || (this.template.hasPipe
        ? !this.template.pipe.areEquals(this._originValue, this.appValue)
        : this._originValue !== this.appValue);
  }

  @Input('disabledUpdate') _disabledUpdate?;
  get disabledUpdate() {
    return this._disabledUpdate === true || this._disabledUpdate === '';
  }

  @Input() type: 'text' | 'email' | 'password' = 'text';
  @Input() lblKey: string;
  @Input('label') _label: string;
  @Input() id: string;
  @Input() required: any;
  @Input() validatorMsg?: string;
  @Input() minLength?: bigint;
  @Input() maxLength?: bigint;
  @Input() matching?: any;
  @Input() name?: string;
  @Input() icon?;
  @Input() selectOneMenu: () => SelectItem<any>[];
  @Input("template") _template: InputTemplate;
  @Input() converter: any;
  @Input("inputMode") _inputMode: InputMode;
  get inputMode() {
    return this._inputMode ? this._inputMode : InputMode.input;
  }
  set inputMode(mode: InputMode) {
    this._inputMode = mode;
  }

  get template() {
    return this._template ? this._template : InputTemplate.shortInput;
  }

  @Input() grid?: any;

  @Input() get value(): any {
    return this._value;
  }
  set value(val) {
    this._value = val;
    this.valueChange.emit(this._value);
  }
  _value: any;
  @Output() valueChange = new EventEmitter<any>();

  // VALUE CONVERTER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  inputToApp(val) {
    this._value = this.template.hasPipe ? this.template.pipe.toAppFormat(val) : val;
    this.valueChange.emit(this._value);
  }

  get inputStringValue() {
    return this.template.hasPipe ? this.template.pipe.toRawInputString(this._value) : this._value;
  }

  get submitValue() {
    return this.template.hasPipe ? this.template.pipe.toSubmitFormat(this._value) : this._value;
  }

  get appValue() {
    return this._value = this.template?.hasPipe ? this.template.pipe.toAppFormat(this._value) : this._value;
  }
  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< VALUE CONVERTER


  public errorMessage?: string;


  get isShowError() {
    return this.inputMode?.isInput
      && this.errorMessage;
  }

  public get hint() {
    if (!this.inputMode.isInput) {
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
    'user.fullName': 'user',
    'user.phoneNumber': 'mobile-alt',
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

  get readableValue() {
    return this.template?.hasPipe ? this.template.pipe.toReadableString(this._value) : this._value;
  }

  get valueStringLength() {
    return typeof this.value === 'string' ? this.value.length : 0;
  }

  get isNumberGreaterThan0() {
    return typeof this.value === 'number' ? this.value > 0 : false;
  }

  get isObject() {
    return typeof this.value === 'object';
  }

  get isShowLabel() {
    return this.valueStringLength > 0
      || this.isNumberGreaterThan0
      || this.isObject
      || this.isGrid;
  }

  validateFailed() {
    return !this.isValidateSuccess();
  }

  get isRequire() {
    return this.required === '' || this.required === true || this.minLength || this.maxLength || this.matching;
  }

  isValidateSuccess(): boolean {
    if (!this.template.isTypeShortInput) {
      return true;
    }
    if (this.isRequire &&
      (StringUtil.isBlank(String(this.value)))) {
      this.errorMessage = AppMessages.PLEASE_INPUT(this.label);
      return;
    }

    if (this.getName().endsWith('Id') && (this.value as unknown as number) <= 0) {
      this.errorMessage = AppMessages.PLEASE_INPUT(this.label);
      return;
    }

    if (this.minLength && this.valueStringLength < this.minLength) {
      this.errorMessage = AppMessages.FIELD_MUST_HAS_AT_LEAST_CHAR(this.label, this.minLength);
      return;
    }

    if (this.maxLength && this.valueStringLength > this.maxLength) {
      this.errorMessage = AppMessages.MAXIMUM_LENGTH_OF_FIELD(this.label, this.maxLength);
      return;
    }

    if (this.getName().toLowerCase().includes('email') && !RegexUtil.isValidEmail(this.value)) {
      this.errorMessage = AppMessages.EMAIL_NOT_VALID;
      return;
    }

    if (this.getName().toLowerCase().includes('phone') && !RegexUtil.isValidPhone(this.value)) {
      this.errorMessage = AppMessages.PHONE_NOT_VALID;
      return;
    }

    if (this.matching) {
      if (this.matching instanceof XeInputComponent && this.matching.value !== this.value) {
        this.errorMessage = AppMessages.FIELD_NOT_MATCH(this.label);
        return;
      }

      if (this.matching instanceof RegExp && !this.matching.test(String(this.value))) {
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

  updateToNewValue() {
    if (!this.disabledUpdate) {
      this._originValue = this.value;
    }
  }

  onDateChange($event: any) {
    this.value = $event.value._d;
  }


  // Multi OPTIONS  >>>>>>>>>>>>>>>>>>>>>>>>>>
  toggleOption(option: string) {
    if (this.inputMode.isBareTextOnly) return;
    this.value = MultiOptionUtil.toggle(this.template.options.ALL, this.value, option);
  }

  hasOption(option: string) {
    return MultiOptionUtil.has(this.value, option);
  }
  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Multi OPTIONS

  // TIME >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  timeOptions$: Observable<string[]>;
  timeOptions = [];

  onTimeChange(time: any) {
    const timer = String(time).trim().split(":");
    const hour = parseInt(timer[0], 10);
    const minute = parseInt(timer[1], 10);
    this.value = new Date(0, 0, 0, hour, minute);
  }

  onInputTime() {
    this.timeOptions$ = of(XeTimePipe.filterTime(this.htmlInput.nativeElement.value));
  }
  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< TIME
}


