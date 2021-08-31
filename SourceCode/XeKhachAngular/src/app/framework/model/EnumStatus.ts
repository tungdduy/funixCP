// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import {XeDatePipe} from "../components/pipes/date.pipe";
import {XeTimePipe} from "../components/pipes/time.pipe";
import {MoneyPipe} from "../components/pipes/money.pipe";
import {XePipe} from "../components/pipes/XePipe";
import {PhonePipe} from "../components/pipes/phone.pipe";
import {Options, WeekDays} from "./EntityEnum";
import {XeTableData} from "./XeTableData";
import {PathPipe} from "../components/pipes/Path.pipe";
import {PathPointPipe} from "../components/pipes/PathPoint.pipe";
import {LocationPipe} from "../components/pipes/location.pipe";
import {Observable, of} from "rxjs";
import {BussSchedulePointPipe} from "../components/pipes/BussSchedulePointPipe";
import {SeatPipe} from "../components/pipes/SeatPipe";
import {SelectItem} from "./SelectItem";
import {TripStatusPipe} from "../components/pipes/TripStatusPipe";
import {XeLbl} from "../../business/i18n";

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


export class InputMode {
  readonly name;

  constructor(private _inputMode, private _input: string, private _readonly: string, private _hideTitle: string, private _showTitle: string, private _acceptAnyString: string, private _disabled: string, private _forbidChange: string, private _html: string, private _text: string) {
    this.name = _inputMode;
  }

  static get input() {
    return new InputMode('input', "", null, null, null, null, null, null, null, null);
  }

  static get disabled() {
    return new InputMode('disabled', null, null, null, null, null, "", null, null, null);
  }

  static get readonly() {
    return new InputMode('readonly', null, "", null, null, null, null, null, null, null);
  }

  static get selectOnly() {
    return new InputMode('selectOnly', '', '', null, null, null, null, null, null, null);
  }

  static get acceptAnyString() {
    return new InputMode('acceptAnyString', null, null, null, null, "", null, null, null, null);
  }

  static get inputNoTitle() {
    return new InputMode('inputNoTitle', '', null, '', null, null, null, null, null, null);
  }

  static get text() {
    return new InputMode('text', null, null, null, null, null, null, '', null, null);
  }

  static get html() {
    return new InputMode('html', null, null, null, null, null, null, '', null, null);
  }

  static get htmlTitle() {
    return new InputMode('htmlTitle', null, null, null, '', null, null, '', '', null);
  }

  static get textTitle() {
    return new InputMode('textTitle', null, null, null, '', null, null, '', null, '');
  }

  get hasInput() {
    return this._input === "";
  }

  get input() {
    return this._input;
  }

  get hasReadonly() {
    return this._readonly === "";
  }

  get readonly() {
    return this._readonly;
  }

  get hasHideTitle() {
    return this._hideTitle === '';
  }

  get hideTitle() {
    return this._hideTitle;
  }

  get hasShowTitle() {
    return this._showTitle === '';
  }

  get showTitle() {
    return this._showTitle;
  }

  get hasAcceptAnyString() {
    return this._acceptAnyString === "";
  }

  get acceptAnyString() {
    return this._acceptAnyString;
  }

  get hasDisabled() {
    return this._disabled === "";
  }

  get disabled() {
    return this._disabled;
  }

  get hasForbidChange() {
    return this._forbidChange === '';
  }

  get forbidChange() {
    return this._forbidChange;
  }

  get hasHtml() {
    return this._html === '';
  }

  get html() {
    return this._html;
  }

  get hasText() {
    return this._text === '';
  }

  get text() {
    return this._text;
  }

  get isInput() {
    return this._inputMode === 'input';
  }

  get isDisabled() {
    return this._inputMode === 'disabled';
  }

  get isReadonly() {
    return this._inputMode === 'readonly';
  }

  get isSelectOnly() {
    return this._inputMode === 'selectOnly';
  }

  get hasSelectOnly() {
    return this._inputMode === 'selectOnly';
  }

  get isAcceptAnyString() {
    return this._inputMode === 'acceptAnyString';
  }

  get isInputNoTitle() {
    return this._inputMode === 'inputNoTitle';
  }

  get hasInputNoTitle() {
    return this._inputMode === 'inputNoTitle';
  }

  get isText() {
    return this._inputMode === 'text';
  }

  get isHtml() {
    return this._inputMode === 'html';
  }

  get isHtmlTitle() {
    return this._inputMode === 'htmlTitle';
  }

  get hasHtmlTitle() {
    return this._inputMode === 'htmlTitle';
  }

  get isTextTitle() {
    return this._inputMode === 'textTitle';
  }

  get hasTextTitle() {
    return this._inputMode === 'textTitle';
  }

  toInput = () => this._input = "";

  resetInput = () => this._input = InputMode[this._inputMode].input;

  toReadonly = () => this._readonly = "";

  resetReadonly = () => this._readonly = InputMode[this._inputMode].readonly;

  toHideTitle = () => this._hideTitle = '';

  resetHideTitle = () => this._hideTitle = InputMode[this._inputMode].hideTitle;

  toShowTitle = () => this._showTitle = '';

  resetShowTitle = () => this._showTitle = InputMode[this._inputMode].showTitle;

  toAcceptAnyString = () => this._acceptAnyString = "";

  resetAcceptAnyString = () => this._acceptAnyString = InputMode[this._inputMode].acceptAnyString;

  toDisabled = () => this._disabled = "";

  resetDisabled = () => this._disabled = InputMode[this._inputMode].disabled;

  toForbidChange = () => this._forbidChange = '';

  resetForbidChange = () => this._forbidChange = InputMode[this._inputMode].forbidChange;

  toHtml = () => this._html = '';

  resetHtml = () => this._html = InputMode[this._inputMode].html;

  toText = () => this._text = '';

  resetText = () => this._text = InputMode[this._inputMode].text;

}


export class LabelMode {
  readonly name;

  constructor(private _labelMode, private _always: string, private _auto: string, private _hidden: string) {
    this.name = _labelMode;
  }

  static get hidden() {
    return new LabelMode('hidden', null, null, "");
  }

  static get always() {
    return new LabelMode('always', "", null, null);
  }

  static get auto() {
    return new LabelMode('auto', null, "", null);
  }

  get hasAlways() {
    return this._always === "";
  }

  get always() {
    return this._always;
  }

  get hasAuto() {
    return this._auto === "";
  }

  get auto() {
    return this._auto;
  }

  get hasHidden() {
    return this._hidden === "";
  }

  get hidden() {
    return this._hidden;
  }

  get isHidden() {
    return this._labelMode === 'hidden';
  }

  get isAlways() {
    return this._labelMode === 'always';
  }

  get isAuto() {
    return this._labelMode === 'auto';
  }

  toAlways = () => this._always = "";

  resetAlways = () => this._always = LabelMode[this._labelMode].always;

  toAuto = () => this._auto = "";

  resetAuto = () => this._auto = LabelMode[this._labelMode].auto;

  toHidden = () => this._hidden = "";

  resetHidden = () => this._hidden = LabelMode[this._labelMode].hidden;

}


export class BussSchemeMode {
  readonly name;

  constructor(private _bussSchemeMode, private _constantStatus: string, private _edit: string, private _bussAdmin: string, private _dynamicStatus: string) {
    this.name = _bussSchemeMode;
  }

  static get edit() {
    return new BussSchemeMode('edit', null, "", null, null);
  }

  static get bussAdmin() {
    return new BussSchemeMode('bussAdmin', null, null, "", null);
  }

  static get readonly() {
    return new BussSchemeMode('readonly', '', null, null, null);
  }

  static get ordering() {
    return new BussSchemeMode('ordering', null, null, null, '');
  }

  static get tripAdmin() {
    return new BussSchemeMode('tripAdmin', null, null, null, '');
  }

  get hasConstantStatus() {
    return this._constantStatus === '';
  }

  get constantStatus() {
    return this._constantStatus;
  }

  get hasEdit() {
    return this._edit === "";
  }

  get edit() {
    return this._edit;
  }

  get hasBussAdmin() {
    return this._bussAdmin === "";
  }

  get bussAdmin() {
    return this._bussAdmin;
  }

  get hasDynamicStatus() {
    return this._dynamicStatus === '';
  }

  get dynamicStatus() {
    return this._dynamicStatus;
  }

  get isEdit() {
    return this._bussSchemeMode === 'edit';
  }

  get isBussAdmin() {
    return this._bussSchemeMode === 'bussAdmin';
  }

  get isReadonly() {
    return this._bussSchemeMode === 'readonly';
  }

  get hasReadonly() {
    return this._bussSchemeMode === 'readonly';
  }

  get isOrdering() {
    return this._bussSchemeMode === 'ordering';
  }

  get hasOrdering() {
    return this._bussSchemeMode === 'ordering';
  }

  get isTripAdmin() {
    return this._bussSchemeMode === 'tripAdmin';
  }

  get hasTripAdmin() {
    return this._bussSchemeMode === 'tripAdmin';
  }

  toConstantStatus = () => this._constantStatus = '';

  resetConstantStatus = () => this._constantStatus = BussSchemeMode[this._bussSchemeMode].constantStatus;

  toEdit = () => this._edit = "";

  resetEdit = () => this._edit = BussSchemeMode[this._bussSchemeMode].edit;

  toBussAdmin = () => this._bussAdmin = "";

  resetBussAdmin = () => this._bussAdmin = BussSchemeMode[this._bussSchemeMode].bussAdmin;

  toDynamicStatus = () => this._dynamicStatus = '';

  resetDynamicStatus = () => this._dynamicStatus = BussSchemeMode[this._bussSchemeMode].dynamicStatus;

}


export class TripUserStatus {
  static readonly selectMenu: SelectItem<string>[] = [new SelectItem(XeLbl('selectItem.TripUserStatus.PENDING'), 'PENDING'), new SelectItem(XeLbl('selectItem.TripUserStatus.CONFIRMED'), 'CONFIRMED'), new SelectItem(XeLbl('selectItem.TripUserStatus.DELETED'), 'DELETED')];
  readonly name;

  constructor(private _tripUserStatus, private _status: string) {
    this.name = _tripUserStatus;
  }

  static get PENDING() {
    return new TripUserStatus('PENDING', "PENDING");
  }

  static get CONFIRMED() {
    return new TripUserStatus('CONFIRMED', "CONFIRMED");
  }

  static get DELETED() {
    return new TripUserStatus('DELETED', "DELETED");
  }

  get hasStatusPENDING() {
    return this._status === "PENDING";
  }

  get hasStatusCONFIRMED() {
    return this._status === "CONFIRMED";
  }

  get hasStatusDELETED() {
    return this._status === "DELETED";
  }

  get status() {
    return this._status;
  }

  get isPENDING() {
    return this._tripUserStatus === 'PENDING';
  }

  get hasPENDING() {
    return this._tripUserStatus === 'PENDING';
  }

  get isCONFIRMED() {
    return this._tripUserStatus === 'CONFIRMED';
  }

  get hasCONFIRMED() {
    return this._tripUserStatus === 'CONFIRMED';
  }

  get isDELETED() {
    return this._tripUserStatus === 'DELETED';
  }

  get hasDELETED() {
    return this._tripUserStatus === 'DELETED';
  }

  toStatusPENDING = () => this._status = "PENDING";

  toStatusCONFIRMED = () => this._status = "CONFIRMED";

  toStatusDELETED = () => this._status = "DELETED";

  resetStatus = () => this._status = TripUserStatus[this._tripUserStatus].status;

}


export class EditOnRow {
  readonly name;

  constructor(private _editOnRow, private _disabled: string, private _enabled: string, private _editing: string) {
    this.name = _editOnRow;
  }

  static get always() {
    return new EditOnRow('always', null, '', "yes");
  }

  static get onClick() {
    return new EditOnRow('onClick', null, '', "no");
  }

  static get disabled() {
    return new EditOnRow('disabled', "", null, null);
  }

  get hasDisabled() {
    return this._disabled === "";
  }

  get disabled() {
    return this._disabled;
  }

  get hasEnabled() {
    return this._enabled === '';
  }

  get enabled() {
    return this._enabled;
  }

  get hasEditingNo() {
    return this._editing === "no";
  }

  get hasEditingYes() {
    return this._editing === "yes";
  }

  get editing() {
    return this._editing;
  }

  get isAlways() {
    return this._editOnRow === 'always';
  }

  get hasAlways() {
    return this._editOnRow === 'always';
  }

  get isOnClick() {
    return this._editOnRow === 'onClick';
  }

  get hasOnClick() {
    return this._editOnRow === 'onClick';
  }

  get isDisabled() {
    return this._editOnRow === 'disabled';
  }

  toDisabled = () => this._disabled = "";

  resetDisabled = () => this._disabled = EditOnRow[this._editOnRow].disabled;

  toEnabled = () => this._enabled = '';

  resetEnabled = () => this._enabled = EditOnRow[this._editOnRow].enabled;

  toEditingNo = () => this._editing = "no";

  toEditingYes = () => this._editing = "yes";

  resetEditing = () => this._editing = EditOnRow[this._editOnRow].editing;

}


export class SeatStatus {
  readonly name;

  constructor(private _seatStatus, private _classes: string) {
    this.name = _seatStatus;
  }

  static get locked() {
    return new SeatStatus('locked', "seat-locked");
  }

  static get lockedByBuss() {
    return new SeatStatus('lockedByBuss', "seat-locked-by-buss");
  }

  static get lockedByTrip() {
    return new SeatStatus('lockedByTrip', "seat-locked-by-trip");
  }

  static get hidden() {
    return new SeatStatus('hidden', "seat-hidden");
  }

  static get available() {
    return new SeatStatus('available', "seat-available");
  }

  static get booked() {
    return new SeatStatus('booked', "seat-booked");
  }

  static get selected() {
    return new SeatStatus('selected', "seat-selected");
  }

  static get confirmed() {
    return new SeatStatus('confirmed', "seat-confirmed");
  }

  get hasClassesSeatLockedByTrip() {
    return this._classes === "seat-locked-by-trip";
  }

  get hasClassesSeatConfirmed() {
    return this._classes === "seat-confirmed";
  }

  get hasClassesSeatAvailable() {
    return this._classes === "seat-available";
  }

  get hasClassesSeatHidden() {
    return this._classes === "seat-hidden";
  }

  get hasClassesSeatLockedByBuss() {
    return this._classes === "seat-locked-by-buss";
  }

  get hasClassesSeatLocked() {
    return this._classes === "seat-locked";
  }

  get hasClassesSeatBooked() {
    return this._classes === "seat-booked";
  }

  get hasClassesSeatSelected() {
    return this._classes === "seat-selected";
  }

  get classes() {
    return this._classes;
  }

  get isLocked() {
    return this._seatStatus === 'locked';
  }

  get hasLocked() {
    return this._seatStatus === 'locked';
  }

  get isLockedByBuss() {
    return this._seatStatus === 'lockedByBuss';
  }

  get hasLockedByBuss() {
    return this._seatStatus === 'lockedByBuss';
  }

  get isLockedByTrip() {
    return this._seatStatus === 'lockedByTrip';
  }

  get hasLockedByTrip() {
    return this._seatStatus === 'lockedByTrip';
  }

  get isHidden() {
    return this._seatStatus === 'hidden';
  }

  get hasHidden() {
    return this._seatStatus === 'hidden';
  }

  get isAvailable() {
    return this._seatStatus === 'available';
  }

  get hasAvailable() {
    return this._seatStatus === 'available';
  }

  get isBooked() {
    return this._seatStatus === 'booked';
  }

  get hasBooked() {
    return this._seatStatus === 'booked';
  }

  get isSelected() {
    return this._seatStatus === 'selected';
  }

  get hasSelected() {
    return this._seatStatus === 'selected';
  }

  get isConfirmed() {
    return this._seatStatus === 'confirmed';
  }

  get hasConfirmed() {
    return this._seatStatus === 'confirmed';
  }

  toClassesSeatLockedByTrip = () => this._classes = "seat-locked-by-trip";

  toClassesSeatConfirmed = () => this._classes = "seat-confirmed";

  toClassesSeatAvailable = () => this._classes = "seat-available";

  toClassesSeatHidden = () => this._classes = "seat-hidden";

  toClassesSeatLockedByBuss = () => this._classes = "seat-locked-by-buss";

  toClassesSeatLocked = () => this._classes = "seat-locked";

  toClassesSeatBooked = () => this._classes = "seat-booked";

  toClassesSeatSelected = () => this._classes = "seat-selected";

  resetClasses = () => this._classes = SeatStatus[this._seatStatus].classes;

}


export class InputTemplate {
  readonly name;
  postChange: (currentValue, oldValue, criteria?) => any;
  preChange: (currentValue, comingValue, criteria?) => any;
  criteria: any;
  observable: (inputValue, criteria?) => Observable<any>;
  selectMenu$: Observable<SelectItem<any>[]>;
  tableData: XeTableData<any>;

  constructor(private _inputTemplate, private _tableOrder: string, private _swap: string, private _display: string, private _autoInput: string, private _options: Options, private _boardMenu: string, private _pipe: XePipe, private _defaultSelectMenu$: Observable<SelectItem<any>[]>, private _type: string, private _triggerOnClick: string) {
    this.name = _inputTemplate;
  }

  static get shortInput() {
    return new InputTemplate('shortInput', null, null, null, null, null, null, null, null, "shortInput", null);
  }

  static get booleanToggle() {
    return new InputTemplate('booleanToggle', null, null, "custom", null, null, null, null, null, "booleanToggle", null);
  }

  static get tripUserStatus() {
    return new InputTemplate('tripUserStatus', null, "on", "custom", null, null, '', TripStatusPipe.instance, of(TripUserStatus.selectMenu), "selectOneMenu", null);
  }

  static get selectOneMenu() {
    return new InputTemplate('selectOneMenu', null, "off", null, null, null, null, null, null, "selectOneMenu", null);
  }

  static get date() {
    return new InputTemplate('date', null, null, null, null, null, null, XeDatePipe.instance, null, "date", null);
  }

  static get time() {
    return new InputTemplate('time', null, null, null, '', null, null, XeTimePipe.instance, null, "time", '');
  }

  static get scheduledLocation() {
    return new InputTemplate('scheduledLocation', null, null, null, '', null, null, LocationPipe.instance, null, null, null);
  }

  static get phone() {
    return new InputTemplate('phone', null, null, null, null, null, null, PhonePipe.instance, null, "shortInput", null);
  }

  static get seats() {
    return new InputTemplate('seats', null, null, "custom", null, null, null, SeatPipe.instance, null, "html", null);
  }

  static get money() {
    return new InputTemplate('money', null, null, null, null, null, null, MoneyPipe.instance, null, "shortInput", null);
  }

  static get weekDays() {
    return new InputTemplate('weekDays', null, null, "custom", null, WeekDays, null, null, null, "multiOption", null);
  }

  static get tableOrder() {
    return new InputTemplate('tableOrder', "", null, null, null, null, null, null, null, null, null);
  }

  static get bussSchedulePoint() {
    return new InputTemplate('bussSchedulePoint', null, null, "custom", null, null, null, BussSchedulePointPipe.instance, null, "searchTable", null);
  }

  static get pathPoint() {
    return new InputTemplate('pathPoint', null, null, "custom", null, null, null, PathPointPipe.instance, null, "searchTable", null);
  }

  static get path() {
    return new InputTemplate('path', null, null, "custom", null, null, null, PathPipe.instance, null, "searchTable", null);
  }

  static get location() {
    return new InputTemplate('location', null, null, "custom", null, null, null, LocationPipe.instance, null, "searchTable", null);
  }

  get hasTableOrder() {
    return this._tableOrder === "";
  }

  get tableOrder() {
    return this._tableOrder;
  }

  get hasSwapOff() {
    return this._swap === "off";
  }

  get hasSwapOn() {
    return this._swap === "on";
  }

  get swap() {
    return this._swap;
  }

  get hasDisplayCustom() {
    return this._display === "custom";
  }

  get display() {
    return this._display;
  }

  get hasAutoInput() {
    return this._autoInput === '';
  }

  get autoInput() {
    return this._autoInput;
  }

  get hasOptions() {
    return this._options !== null;
  }

  get options() {
    return this._options;
  }

  get hasBoardMenu() {
    return this._boardMenu === '';
  }

  get boardMenu() {
    return this._boardMenu;
  }

  get hasPipe() {
    return this._pipe !== null;
  }

  get pipe() {
    return this._pipe;
  }

  get hasDefaultSelectMenu$() {
    return this._defaultSelectMenu$ !== null;
  }

  get defaultSelectMenu$() {
    return this._defaultSelectMenu$;
  }

  get hasTypeSearchTable() {
    return this._type === "searchTable";
  }

  get hasTypeMultiOption() {
    return this._type === "multiOption";
  }

  get hasTypeBooleanToggle() {
    return this._type === "booleanToggle";
  }

  get hasTypeShortInput() {
    return this._type === "shortInput";
  }

  get hasTypeDate() {
    return this._type === "date";
  }

  get hasTypeTime() {
    return this._type === "time";
  }

  get hasTypeHtml() {
    return this._type === "html";
  }

  get hasTypeSelectOneMenu() {
    return this._type === "selectOneMenu";
  }

  get type() {
    return this._type;
  }

  get hasTriggerOnClick() {
    return this._triggerOnClick === '';
  }

  get triggerOnClick() {
    return this._triggerOnClick;
  }

  get isShortInput() {
    return this._inputTemplate === 'shortInput';
  }

  get hasShortInput() {
    return this._inputTemplate === 'shortInput';
  }

  get isBooleanToggle() {
    return this._inputTemplate === 'booleanToggle';
  }

  get hasBooleanToggle() {
    return this._inputTemplate === 'booleanToggle';
  }

  get isTripUserStatus() {
    return this._inputTemplate === 'tripUserStatus';
  }

  get hasTripUserStatus() {
    return this._inputTemplate === 'tripUserStatus';
  }

  get isSelectOneMenu() {
    return this._inputTemplate === 'selectOneMenu';
  }

  get hasSelectOneMenu() {
    return this._inputTemplate === 'selectOneMenu';
  }

  get isDate() {
    return this._inputTemplate === 'date';
  }

  get hasDate() {
    return this._inputTemplate === 'date';
  }

  get isTime() {
    return this._inputTemplate === 'time';
  }

  get hasTime() {
    return this._inputTemplate === 'time';
  }

  get isScheduledLocation() {
    return this._inputTemplate === 'scheduledLocation';
  }

  get hasScheduledLocation() {
    return this._inputTemplate === 'scheduledLocation';
  }

  get isPhone() {
    return this._inputTemplate === 'phone';
  }

  get hasPhone() {
    return this._inputTemplate === 'phone';
  }

  get isSeats() {
    return this._inputTemplate === 'seats';
  }

  get hasSeats() {
    return this._inputTemplate === 'seats';
  }

  get isMoney() {
    return this._inputTemplate === 'money';
  }

  get hasMoney() {
    return this._inputTemplate === 'money';
  }

  get isWeekDays() {
    return this._inputTemplate === 'weekDays';
  }

  get hasWeekDays() {
    return this._inputTemplate === 'weekDays';
  }

  get isTableOrder() {
    return this._inputTemplate === 'tableOrder';
  }

  get isBussSchedulePoint() {
    return this._inputTemplate === 'bussSchedulePoint';
  }

  get hasBussSchedulePoint() {
    return this._inputTemplate === 'bussSchedulePoint';
  }

  get isPathPoint() {
    return this._inputTemplate === 'pathPoint';
  }

  get hasPathPoint() {
    return this._inputTemplate === 'pathPoint';
  }

  get isPath() {
    return this._inputTemplate === 'path';
  }

  get hasPath() {
    return this._inputTemplate === 'path';
  }

  get isLocation() {
    return this._inputTemplate === 'location';
  }

  get hasLocation() {
    return this._inputTemplate === 'location';
  }

  toTableOrder = () => this._tableOrder = "";

  resetTableOrder = () => this._tableOrder = InputTemplate[this._inputTemplate].tableOrder;

  toSwapOff = () => this._swap = "off";

  toSwapOn = () => this._swap = "on";

  resetSwap = () => this._swap = InputTemplate[this._inputTemplate].swap;

  toDisplayCustom = () => this._display = "custom";

  resetDisplay = () => this._display = InputTemplate[this._inputTemplate].display;

  toAutoInput = () => this._autoInput = '';

  resetAutoInput = () => this._autoInput = InputTemplate[this._inputTemplate].autoInput;

  resetOptions = () => this._options = InputTemplate[this._inputTemplate].options;

  toBoardMenu = () => this._boardMenu = '';

  resetBoardMenu = () => this._boardMenu = InputTemplate[this._inputTemplate].boardMenu;

  resetPipe = () => this._pipe = InputTemplate[this._inputTemplate].pipe;

  resetDefaultSelectMenu$ = () => this._defaultSelectMenu$ = InputTemplate[this._inputTemplate].defaultSelectMenu$;

  toTypeSearchTable = () => this._type = "searchTable";

  toTypeMultiOption = () => this._type = "multiOption";

  toTypeBooleanToggle = () => this._type = "booleanToggle";

  toTypeShortInput = () => this._type = "shortInput";

  toTypeDate = () => this._type = "date";

  toTypeTime = () => this._type = "time";

  toTypeHtml = () => this._type = "html";

  toTypeSelectOneMenu = () => this._type = "selectOneMenu";

  resetType = () => this._type = InputTemplate[this._inputTemplate].type;

  toTriggerOnClick = () => this._triggerOnClick = '';

  resetTriggerOnClick = () => this._triggerOnClick = InputTemplate[this._inputTemplate].triggerOnClick;

  _postChange = (postChange) => {
    this.postChange = postChange;
    return this;
  }

  _preChange = (preChange) => {
    this.preChange = preChange;
    return this;
  }

  _criteria = (criteria) => {
    this.criteria = criteria;
    return this;
  }

  _observable = (observable) => {
    this.observable = observable;
    return this;
  }

  _selectMenu$ = (selectMenu$) => {
    this.selectMenu$ = selectMenu$;
    return this;
  }

  _tableData = (tableData) => {
    this.tableData = tableData;
    return this;
  }

}

