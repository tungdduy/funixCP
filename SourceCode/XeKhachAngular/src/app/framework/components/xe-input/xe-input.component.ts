import {AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {ObjectUtil} from '../../util/object.util';
import {RegexUtil} from '../../util/regex.util';
import {AppMessages, XeLbl} from '../../../business/i18n';
import {StringUtil} from '../../util/string.util';
import {SelectItem} from '../../model/SelectItem';
import {InputMode, InputTemplate, LabelMode} from "../../model/EnumStatus";
import {Observable, Subject} from "rxjs";
import {AbstractXe} from "../../model/AbstractXe";
import {MultiOptionUtil} from "../../model/EntityEnum";
import {EntityField} from "../../model/XeFormData";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs/operators";
import {AutoInputModel} from "../../model/AutoInputModel";


@Component({
  selector: 'xe-input',
  templateUrl: './xe-input.component.html',
  styleUrls: ['./xe-input.component.scss'],
})
export class XeInputComponent extends AbstractXe implements AfterViewInit {
  _originValue;

  @ViewChild("htmlShortInput") htmlShortInput: ElementRef;
  @Input() entityField: EntityField;
  originalMode: InputMode;
  // SELECT ONE MENU >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  selectOneMenu: SelectItem<any>[];
  selectOneMenu$: Observable<SelectItem<any>[]>;
  isViewingBoard = false;
  _oldValue;
  @Input() type: 'text' | 'email' | 'password' = 'text';
  @Input() lblKey: string;
  @Input() id: string;
  @Input() required: any;
  @Input() validatorMsg?: string;
  @Input() minLength?: number;
  @Input() maxLength?: number;

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SELECT ONE MENU
  @Input() matching?: any;
  @Input() name?: string;
  @Input() icon?;
  @Input() converter: any;
  @Input() formMode: InputMode;
  @Input() grid?: any;
  @Output() valueChange = new EventEmitter<any>();
  public errorMessage?: string;
  icons = {
    email: 'envelope',
    username: 'id-card',
    'user.fullName': 'user',
    'user.phoneNumber': 'mobile-alt',
    password: 'key',
    fullName: 'user',
    phoneNumber: 'mobile-alt',
    launchDate: 'calendar-alt',
    'bussSchedulePoint.startPoint': 'chevron-up',
    locationFrom: 'chevron-up',
    locationTo: 'chevron-down',
    companyName: 'id-card',
    companyDesc: 'comment',
    hotLine: 'mobile-alt',
    bussLicense: 'id-card',
    bussDesc: 'comment',
    selectCompany: 'building',
    companyId: 'building',
    bussTypeName: 'bus',
    bussTypeDesc: 'comment',
    seatGroupName: 'couch',
    seatGroupDesc: 'comment',
    totalSeats: 'pen-fancy',
    scheduleUnitPrice: 'money-bill',
    launchTime: 'clock',
    effectiveDateFrom: 'calendar-alt',
    workingDays: 'calendar-day',
    path: 'wave-square',
    startPoint: 'play-circle',
    endPoint: 'stop-circle',
    price: 'money-bill',
    'employee.user.phoneNumber': 'mobile-alt',
    'employee.user.fullName': 'user',
    pointName: 'map-marker-alt',
    pointDesc: 'comment',
  };
  autoInputOptions$: Observable<AutoInputModel[]>;
  private searchTerm = new Subject<string>();

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

  get alwaysShowLabel(): boolean {
    return this.mode.hasShowTitle
      || this.lblMode.hasAlways;
  }

  @Input('label') _label: string;

  public get label() {
    if (!this._label) {
      if (!this.lblKey) this.lblKey = this.getName();
      this._label = XeLbl('input.' + this.template.name + "." + this.lblKey);
    }
    return this._label;
  }

  @Input("template") _template: InputTemplate;

  get template() {
    return this._template ? this._template : InputTemplate.shortInput;
  }

  @Input("labelMode") _lblMode: LabelMode;

  get lblMode() {
    return this._lblMode || LabelMode.auto;
  }

  get isDisabled() {
    return this.formMode?.hasDisabled || this.mode.hasDisabled;
  }

  @Input("mode") _mode: InputMode;

  get mode() {
    return this._mode ? this._mode : InputMode.input;
  }

  set mode(mode: InputMode) {
    this._mode = mode;
  }

  _value: any;

  @Input() get value(): any {
    return this._value;
  }

  set value(val) {
    if (this._value !== val) {
      this._value = val;
      this.valueChange.emit(this._value);
    }
  }

  get submitValue() {
    return this.template.hasPipe ? this.template.pipe.singleToSubmitFormat(this._value) : this._value;
  }

  get appValue() {
    return this._value = this.template?.hasPipe ? this.template.pipe.singleToAppValue(this._value) : this._value;
  }

  get isShowError() {
    return this.mode?.isInput
      && this.errorMessage;
  }

  public get hint() {
    if (!this.mode.isInput) {
      return XeLbl('NO_VALUE');
    } else {
      return XeLbl('PLEASE_INPUT');
    }
  }

  public get placeHolder() {
    if (this.isShowLabel) {
      return XeLbl(this.hint);
    }
    return this.label;
  }

  get isGrid() {
    return this.grid === '' || this.grid === true;
  }

  get asInlineString() {
    return this.template?.hasPipe
      ? this.template.pipe.singleToShortString
      ? this.template.pipe.singleToShortString(this._value)
      : this.template.pipe.singleToInline(this._value)
      : this._value;
  }

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< VALUE CONVERTER

  get asHtml() {
    return this.template?.hasPipe ? this.template.pipe.singleToHtml(this._value) : this._value;
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
    return !this.mode.hasHideTitle
      && (this.valueStringLength > 0
        || this.isNumberGreaterThan0
        || this.isObject
        || this.isGrid
        || this.alwaysShowLabel);
  }

  get isRequire() {
    return this.required === '' || this.required === true || this.minLength || this.maxLength || this.matching;
  }

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
    if (this.template?.pipe?.singleToInline && this.htmlShortInput?.nativeElement) {
      this.htmlShortInput.nativeElement.value = this.template.pipe.singleToInline(this.value);
    }
    if (this.template.hasSelectOneMenu) {
      this.selectOneMenu$ = this.template.selectMenu$;
    } else if (this.template.hasDefaultSelectMenu$) {
      this.selectOneMenu$ = this.template.defaultSelectMenu$;
    }
    if (this.template.hasSwapOn) {
      this.selectOneMenu$.subscribe(
        menu => this.selectOneMenu = menu
      );
    }
    this.initAutoInput();
  }

  selectItem(val) {
    if (!this.mode.hasInput) return;
    this.isViewingBoard = false;
    this._preChange(val);
    if (this._value !== val) {
      this._value = val;
      this.valueChange.emit(this._value);
      this._postChange();
    }
  }

  _preChange(comingValue) {
    if (this.entityField?.action?.preChange) {
      this.entityField.action.preChange(this._value, comingValue);
    }
    this._oldValue = this._value;
    if (this.template.preChange) {
      if (this.template.criteria) {
        this.template.criteria['inputName'] = this.getName();
      }
      this.template.preChange(this._value, comingValue, this.template.criteria);
    }
  }

  _postChange() {
    if (this.entityField?.action?.postChange) {
      this.entityField.action.postChange(this._value, this._oldValue);
    }
    if (this.template.postChange) {
      if (this.template.criteria) {
        this.template.criteria['inputName'] = this.getName();
      }
      this.template.postChange(this._value, this._oldValue, this.template.criteria);
    }
  }

  clickSwapItem() {
    if (!this.mode.hasInput) return;
    if (this.template.hasBoardMenu) {
      this.isViewingBoard = true;
    } else {
      this.selectNextItem();
    }
  }

  selectNextItem() {
    let idx = 0;
    this.selectOneMenu.forEach((item, index) => {
      if (this._value === item.value) idx = index + 1;
      if (idx === this.selectOneMenu.length) idx = 0;
    });
    const val = this.selectOneMenu[idx].value;
    this._preChange(val);
    this._value = val;
    this.valueChange.emit(val);
    this._postChange();
  }

  onShortInputBlur($event: any) {
    if (this.template?.pipe?.singleToInline && this.htmlShortInput?.nativeElement) {
      $event.target.value = this.template?.pipe.singleToInline(this.value);
    }
  }

  onShortInputFocus($event: any) {
    $event.target.value = this.template?.pipe?.singleToManualShortInput(this._value) || this._value || "";
  }

  // VALUE CONVERTER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  inputToApp(val) {
    this._value = this.template.hasPipe ? this.template.pipe.singleToAppValue(val) : val;
    this.valueChange.emit(this._value);
  }

  getIcon() {
    if (!this.icon) {
      this.icon = this.icons[this.type] ? this.icons[this.type]
        : this.icons[this.name] ? this.icons[this.name]
          : this.fetchAnyPossibleIconFromName();
    }
    return this.icon;
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

  optionAsHtml(value) {
    return this.template?.hasPipe ? this.template.pipe.singleToHtml(value) : value;
  }

  validateFailed() {
    return !this.isValidateSuccess();
  }

  isValidateSuccess(): boolean {
    this.errorMessage = undefined;
    if (this.isRequire && !this._value
      && !this.template.hasTypeBooleanToggle) {
      this.errorMessage = AppMessages.PLEASE_INPUT(this.label);
      return false;
    }
    if (this.template.hasPipe) {
      this.errorMessage = this.template?.pipe?.singleValidate(this.appValue);
      if (!!this.errorMessage)
        return false;
    }
    if (this.isRequire &&
      (StringUtil.isBlank(String(this.value)))) {
      this.errorMessage = AppMessages.PLEASE_INPUT(this.label);
      return false;
    }
    if (!this.isRequire) {
      return true;
    }

    if (this.getName().endsWith('Id') && (this.value as unknown as number) <= 0) {
      this.errorMessage = AppMessages.PLEASE_INPUT(this.label);
      return false;
    }

    if (this.minLength && this.valueStringLength < this.minLength) {
      this.errorMessage = AppMessages.FIELD_MUST_HAS_AT_LEAST_CHAR(this.label, this.minLength);
      return false;
    }

    if (this.maxLength && this.valueStringLength > this.maxLength) {
      this.errorMessage = AppMessages.MAXIMUM_LENGTH_OF_FIELD(this.label, this.maxLength);
      return false;
    }

    if (this.getName().toLowerCase().includes('email') && !RegexUtil.isValidEmail(this.value)) {
      this.errorMessage = AppMessages.EMAIL_NOT_VALID;
      return false;
    }

    if (this.getName().toLowerCase().includes('phone') && !RegexUtil.isValidPhone(this.value)) {
      this.errorMessage = AppMessages.PHONE_NOT_VALID;
      return false;
    }

    if (this.matching) {
      if (this.matching instanceof XeInputComponent && this.matching.value !== this.value) {
        this.errorMessage = AppMessages.FIELD_NOT_MATCH(this.label);
        return false;
      }

      if (this.matching instanceof RegExp && !this.matching.test(String(this.value))) {
        this.errorMessage = AppMessages.INVALID_FIELD(this.label);
        return false;
      }

      if (ObjectUtil.isString(this.matching) && this.matching !== this.value) {
        this.errorMessage = AppMessages.FIELD_NOT_MATCH(this.matching);
        return false;
      }
    }

    if (this.validatorMsg) {
      this.errorMessage = this.validatorMsg;
      return false;
    }

    this.errorMessage = undefined;
    return true;
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
    this._preChange($event.value._d);
    this._value = $event.value._d;
    this.valueChange.emit(this._value);
    this._postChange();
  }

  // Multi OPTIONS  >>>>>>>>>>>>>>>>>>>>>>>>>>
  toggleOption(option: string) {
    if (this.mode.hasForbidChange) return;
    this.value = MultiOptionUtil.toggle(this.template.options.ALL, this.value, option);
  }

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Multi OPTIONS

  // AUTO INPUT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

  hasOption(option: string) {
    return MultiOptionUtil.has(this.value, option);
  }

  onAutoInputChange($event: any) {
    const inputValue = $event.target.value;
    if (StringUtil.isBlank(inputValue)) {
      this.value = undefined;
      return;
    }
    if (this.mode.hasAcceptAnyString) {
      this.value = inputValue;
    } else {
      this.searchTerm.next(inputValue);
    }
  }

  onClickAutoInput() {
    if (this.template.hasTriggerOnClick) {
      this.searchTerm.next(this.value);
    }
  }

  onAutoInputSelected(_v: any, autoInputTemplate: HTMLInputElement) {
    this._preChange(_v);
    this._value = _v;
    this.valueChange.emit(this._value);
    autoInputTemplate.value = this.asInlineString;
    this._postChange();
  }

  // SEARCH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  search() {
    const origin: any = this.preSearch();
    this.template.tableData.table.action.postSelect = (entity: any) => {
      this.tableSelect(entity);
      this.isValidateSuccess();
      this.template.tableData.xeScreen.back();
      this.postSearch(origin);
    };
  }

  tableSelect(value) {
    this._preChange(value);
    this._value = value;
    this.valueChange.emit(value);
    this._postChange();
  }

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< TIME

  preSearch() {
    const origin = {
      screen: this.template.tableData.xeScreen,
      readonly: this.template.tableData.table.mode.readonly,
      onTablePostSelect: this.template.tableData.table.action.postSelect,
      hideSelectColumn: this.template.tableData.table.mode.hideSelectColumn,
      onFormPostCancel: this.template.tableData.formData.action.postCancel,
      adminContainer: {
        screen: {config: {preGo: this.adminContainer.screen.config.preGo}},
        tableData: this.adminContainer.tableData
      }
    };

    this.template.tableData.xeScreen = this.adminContainer.screen;
    this.template.tableData.table.mode.readonly = true;
    this.template.tableData.table.mode.hideSelectColumn = true;
    this.template.tableData.formData.action.postCancel = (screen) => this.postSearch(origin);
    this.adminContainer.screen.config.preGo = (screen) => screen !== this.adminContainer.screens.table ? this.postSearch(origin) : '';
    this.adminContainer.tableData = this.template.tableData;

    this.adminContainer.screen.go(this.adminContainer.screens.table);

    return origin;
  }

  postSearch(origin) {
    this.template.tableData.xeScreen = origin.screen;
    this.template.tableData.table.mode.readonly = origin.readonly;
    this.template.tableData.table.mode.hideSelectColumn = origin.hideSelectColumn;
    this.template.tableData.table.action.postSelect = origin.onTablePostSelect;
    this.template.tableData.formData.action.postCancel = origin.onFormPostCancel;
    this.adminContainer.screen.config.preGo = origin.adminContainer.screen.config.preGo;
    this.adminContainer.tableData = origin.adminContainer.tableData;
  }

  // TOGGLE BOOLEAN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  toggleBoolean() {
    if (!this.mode.hasInput) return;
    this.value = !this.value;
  }

  private fetchAnyPossibleIconFromName() {
    for (const key in this.icons) {
      if (this.getName().toLowerCase().includes(key)) {
        return this.icons[key];
      }
    }
    return 'book-open';
  }

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SEARCH

  private initAutoInput() {
    if (!this.template.hasAutoInput) return;
    this.autoInputOptions$ = this.searchTerm.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => {
        if (this.template?.criteria) {
          this.template.criteria.inputName = this.name;
          this.template.criteria.inputText = term;
        }
        return this.template.observable(this.template.criteria);
      })
    );
  }

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< TOGGLE BOOLEAN


}


