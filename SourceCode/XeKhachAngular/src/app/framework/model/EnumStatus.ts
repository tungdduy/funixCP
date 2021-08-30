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
import {XeLbl} from "../../business/i18n";
import {TripStatusPipe} from "../components/pipes/TripStatusPipe";
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


export class InputMode {
  readonly name;
  constructor(private _inputMode, private _input: string, private _readonly: string, private _hideTitle: string, private _showTitle: string, private _acceptAnyString: string, private _disabled: string, private _forbidChange: string, private _html: string, private _text: string) {
    this.name = _inputMode;
  }

  get hasInput() {return this._input === ""; }
  toInput = () => this._input = "";
  resetInput = () => this._input = InputMode[this._inputMode].input;
  get input() {return this._input; }
  get hasReadonly() {return this._readonly === ""; }
  toReadonly = () => this._readonly = "";
  resetReadonly = () => this._readonly = InputMode[this._inputMode].readonly;
  get readonly() {return this._readonly; }
  get hasHideTitle() {return this._hideTitle === ''; }
  toHideTitle = () => this._hideTitle = '';
  resetHideTitle = () => this._hideTitle = InputMode[this._inputMode].hideTitle;
  get hideTitle() {return this._hideTitle; }
  get hasShowTitle() {return this._showTitle === ''; }
  toShowTitle = () => this._showTitle = '';
  resetShowTitle = () => this._showTitle = InputMode[this._inputMode].showTitle;
  get showTitle() {return this._showTitle; }
  get hasAcceptAnyString() {return this._acceptAnyString === ""; }
  toAcceptAnyString = () => this._acceptAnyString = "";
  resetAcceptAnyString = () => this._acceptAnyString = InputMode[this._inputMode].acceptAnyString;
  get acceptAnyString() {return this._acceptAnyString; }
  get hasDisabled() {return this._disabled === ""; }
  toDisabled = () => this._disabled = "";
  resetDisabled = () => this._disabled = InputMode[this._inputMode].disabled;
  get disabled() {return this._disabled; }
  get hasForbidChange() {return this._forbidChange === ''; }
  toForbidChange = () => this._forbidChange = '';
  resetForbidChange = () => this._forbidChange = InputMode[this._inputMode].forbidChange;
  get forbidChange() {return this._forbidChange; }
  get hasHtml() {return this._html === ''; }
  toHtml = () => this._html = '';
  resetHtml = () => this._html = InputMode[this._inputMode].html;
  get html() {return this._html; }
  get hasText() {return this._text === ''; }
  toText = () => this._text = '';
  resetText = () => this._text = InputMode[this._inputMode].text;
  get text() {return this._text; }
  static get input() {return new InputMode('input', "", null, null, null, null, null, null, null, null); }
  get isInput() {return this._inputMode === 'input'; }
  static get disabled() {return new InputMode('disabled', null, null, null, null, null, "", null, null, null); }
  get isDisabled() {return this._inputMode === 'disabled'; }
  static get readonly() {return new InputMode('readonly', null, "", null, null, null, null, null, null, null); }
  get isReadonly() {return this._inputMode === 'readonly'; }
  static get selectOnly() {return new InputMode('selectOnly', '', '', null, null, null, null, null, null, null); }
  get isSelectOnly() {return this._inputMode === 'selectOnly'; }
  get hasSelectOnly() {return this._inputMode === 'selectOnly'; }
  static get acceptAnyString() {return new InputMode('acceptAnyString', null, null, null, null, "", null, null, null, null); }
  get isAcceptAnyString() {return this._inputMode === 'acceptAnyString'; }
  static get inputNoTitle() {return new InputMode('inputNoTitle', '', null, '', null, null, null, null, null, null); }
  get isInputNoTitle() {return this._inputMode === 'inputNoTitle'; }
  get hasInputNoTitle() {return this._inputMode === 'inputNoTitle'; }
  static get text() {return new InputMode('text', null, null, null, null, null, null, '', null, null); }
  get isText() {return this._inputMode === 'text'; }
  static get html() {return new InputMode('html', null, null, null, null, null, null, '', null, null); }
  get isHtml() {return this._inputMode === 'html'; }
  static get htmlTitle() {return new InputMode('htmlTitle', null, null, null, '', null, null, '', '', null); }
  get isHtmlTitle() {return this._inputMode === 'htmlTitle'; }
  get hasHtmlTitle() {return this._inputMode === 'htmlTitle'; }
  static get textTitle() {return new InputMode('textTitle', null, null, null, '', null, null, '', null, ''); }
  get isTextTitle() {return this._inputMode === 'textTitle'; }
  get hasTextTitle() {return this._inputMode === 'textTitle'; }

}


export class BussSchemeMode {
  readonly name;
  constructor(private _bussSchemeMode, private _constantStatus: string, private _dynamicStatus: string) {
    this.name = _bussSchemeMode;
  }

  get hasConstantStatus() {return this._constantStatus === ''; }
  toConstantStatus = () => this._constantStatus = '';
  resetConstantStatus = () => this._constantStatus = BussSchemeMode[this._bussSchemeMode].constantStatus;
  get constantStatus() {return this._constantStatus; }
  get hasDynamicStatus() {return this._dynamicStatus === ''; }
  toDynamicStatus = () => this._dynamicStatus = '';
  resetDynamicStatus = () => this._dynamicStatus = BussSchemeMode[this._bussSchemeMode].dynamicStatus;
  get dynamicStatus() {return this._dynamicStatus; }
  static get edit() {return new BussSchemeMode('edit', '', null); }
  get isEdit() {return this._bussSchemeMode === 'edit'; }
  get hasEdit() {return this._bussSchemeMode === 'edit'; }
  static get readonly() {return new BussSchemeMode('readonly', '', null); }
  get isReadonly() {return this._bussSchemeMode === 'readonly'; }
  get hasReadonly() {return this._bussSchemeMode === 'readonly'; }
  static get ordering() {return new BussSchemeMode('ordering', null, ''); }
  get isOrdering() {return this._bussSchemeMode === 'ordering'; }
  get hasOrdering() {return this._bussSchemeMode === 'ordering'; }
  static get tripAdmin() {return new BussSchemeMode('tripAdmin', null, ''); }
  get isTripAdmin() {return this._bussSchemeMode === 'tripAdmin'; }
  get hasTripAdmin() {return this._bussSchemeMode === 'tripAdmin'; }

}


export class TripUserStatus {
  readonly name;
  constructor(private _tripUserStatus, private _status: string) {
    this.name = _tripUserStatus;
  }
  static readonly selectMenu: SelectItem<string>[] = [new SelectItem(XeLbl('selectItem.TripUserStatus.PENDING'), 'PENDING'), new SelectItem(XeLbl('selectItem.TripUserStatus.CONFIRMED'), 'CONFIRMED'), new SelectItem(XeLbl('selectItem.TripUserStatus.DELETED'), 'DELETED')];

  get hasStatusDELETED() {return this._status === "DELETED"; }
  toStatusDELETED = () => this._status = "DELETED";
  get hasStatusPENDING() {return this._status === "PENDING"; }
  toStatusPENDING = () => this._status = "PENDING";
  get hasStatusCONFIRMED() {return this._status === "CONFIRMED"; }
  toStatusCONFIRMED = () => this._status = "CONFIRMED";
  resetStatus = () => this._status = TripUserStatus[this._tripUserStatus].status;
  get status() {return this._status; }
  static get PENDING() {return new TripUserStatus('PENDING', "PENDING"); }
  get isPENDING() {return this._tripUserStatus === 'PENDING'; }
  get hasPENDING() {return this._tripUserStatus === 'PENDING'; }
  static get CONFIRMED() {return new TripUserStatus('CONFIRMED', "CONFIRMED"); }
  get isCONFIRMED() {return this._tripUserStatus === 'CONFIRMED'; }
  get hasCONFIRMED() {return this._tripUserStatus === 'CONFIRMED'; }
  static get DELETED() {return new TripUserStatus('DELETED', "DELETED"); }
  get isDELETED() {return this._tripUserStatus === 'DELETED'; }
  get hasDELETED() {return this._tripUserStatus === 'DELETED'; }

}


export class EditOnRow {
  readonly name;
  constructor(private _editOnRow, private _disabled: string, private _enabled: string, private _editing: string) {
    this.name = _editOnRow;
  }

  get hasDisabled() {return this._disabled === ""; }
  toDisabled = () => this._disabled = "";
  resetDisabled = () => this._disabled = EditOnRow[this._editOnRow].disabled;
  get disabled() {return this._disabled; }
  get hasEnabled() {return this._enabled === ''; }
  toEnabled = () => this._enabled = '';
  resetEnabled = () => this._enabled = EditOnRow[this._editOnRow].enabled;
  get enabled() {return this._enabled; }
  get hasEditingNo() {return this._editing === "no"; }
  toEditingNo = () => this._editing = "no";
  get hasEditingYes() {return this._editing === "yes"; }
  toEditingYes = () => this._editing = "yes";
  resetEditing = () => this._editing = EditOnRow[this._editOnRow].editing;
  get editing() {return this._editing; }
  static get always() {return new EditOnRow('always', null, '', "yes"); }
  get isAlways() {return this._editOnRow === 'always'; }
  get hasAlways() {return this._editOnRow === 'always'; }
  static get onClick() {return new EditOnRow('onClick', null, '', "no"); }
  get isOnClick() {return this._editOnRow === 'onClick'; }
  get hasOnClick() {return this._editOnRow === 'onClick'; }
  static get disabled() {return new EditOnRow('disabled', "", null, null); }
  get isDisabled() {return this._editOnRow === 'disabled'; }

}


export class SeatStatus {
  readonly name;
  constructor(private _seatStatus, private _classes: string) {
    this.name = _seatStatus;
  }

  get hasClassesSeatSelected() {return this._classes === "seat-selected"; }
  toClassesSeatSelected = () => this._classes = "seat-selected";
  get hasClassesSeatHidden() {return this._classes === "seat-hidden"; }
  toClassesSeatHidden = () => this._classes = "seat-hidden";
  get hasClassesSeatBooked() {return this._classes === "seat-booked"; }
  toClassesSeatBooked = () => this._classes = "seat-booked";
  get hasClassesSeatConfirmed() {return this._classes === "seat-confirmed"; }
  toClassesSeatConfirmed = () => this._classes = "seat-confirmed";
  get hasClassesSeatLocked() {return this._classes === "seat-locked"; }
  toClassesSeatLocked = () => this._classes = "seat-locked";
  get hasClassesSeatLockedByTrip() {return this._classes === "seat-locked-by-trip"; }
  toClassesSeatLockedByTrip = () => this._classes = "seat-locked-by-trip";
  get hasClassesSeatLockedByBuss() {return this._classes === "seat-locked-by-buss"; }
  toClassesSeatLockedByBuss = () => this._classes = "seat-locked-by-buss";
  get hasClassesSeatAvailable() {return this._classes === "seat-available"; }
  toClassesSeatAvailable = () => this._classes = "seat-available";
  resetClasses = () => this._classes = SeatStatus[this._seatStatus].classes;
  get classes() {return this._classes; }
  static get locked() {return new SeatStatus('locked', "seat-locked"); }
  get isLocked() {return this._seatStatus === 'locked'; }
  get hasLocked() {return this._seatStatus === 'locked'; }
  static get lockedByBuss() {return new SeatStatus('lockedByBuss', "seat-locked-by-buss"); }
  get isLockedByBuss() {return this._seatStatus === 'lockedByBuss'; }
  get hasLockedByBuss() {return this._seatStatus === 'lockedByBuss'; }
  static get lockedByTrip() {return new SeatStatus('lockedByTrip', "seat-locked-by-trip"); }
  get isLockedByTrip() {return this._seatStatus === 'lockedByTrip'; }
  get hasLockedByTrip() {return this._seatStatus === 'lockedByTrip'; }
  static get hidden() {return new SeatStatus('hidden', "seat-hidden"); }
  get isHidden() {return this._seatStatus === 'hidden'; }
  get hasHidden() {return this._seatStatus === 'hidden'; }
  static get available() {return new SeatStatus('available', "seat-available"); }
  get isAvailable() {return this._seatStatus === 'available'; }
  get hasAvailable() {return this._seatStatus === 'available'; }
  static get booked() {return new SeatStatus('booked', "seat-booked"); }
  get isBooked() {return this._seatStatus === 'booked'; }
  get hasBooked() {return this._seatStatus === 'booked'; }
  static get selected() {return new SeatStatus('selected', "seat-selected"); }
  get isSelected() {return this._seatStatus === 'selected'; }
  get hasSelected() {return this._seatStatus === 'selected'; }
  static get confirmed() {return new SeatStatus('confirmed', "seat-confirmed"); }
  get isConfirmed() {return this._seatStatus === 'confirmed'; }
  get hasConfirmed() {return this._seatStatus === 'confirmed'; }

}


export class InputTemplate {
  readonly name;
  constructor(private _inputTemplate, private _tableOrder: string, private _swap: string, private _display: string, private _autoInput: string, private _options: Options, private _boardMenu: string, private _pipe: XePipe, private _defaultSelectMenu$: Observable<SelectItem<any>[]>, private _type: string, private _triggerOnClick: string) {
    this.name = _inputTemplate;
  }

  get hasTableOrder() {return this._tableOrder === ""; }
  toTableOrder = () => this._tableOrder = "";
  resetTableOrder = () => this._tableOrder = InputTemplate[this._inputTemplate].tableOrder;
  get tableOrder() {return this._tableOrder; }
  get hasSwapOff() {return this._swap === "off"; }
  toSwapOff = () => this._swap = "off";
  get hasSwapOn() {return this._swap === "on"; }
  toSwapOn = () => this._swap = "on";
  resetSwap = () => this._swap = InputTemplate[this._inputTemplate].swap;
  get swap() {return this._swap; }
  get hasDisplayCustom() {return this._display === "custom"; }
  toDisplayCustom = () => this._display = "custom";
  resetDisplay = () => this._display = InputTemplate[this._inputTemplate].display;
  get display() {return this._display; }
  get hasAutoInput() {return this._autoInput === ''; }
  toAutoInput = () => this._autoInput = '';
  resetAutoInput = () => this._autoInput = InputTemplate[this._inputTemplate].autoInput;
  get autoInput() {return this._autoInput; }
  get hasOptions() {return this._options !== null; }
  resetOptions = () => this._options = InputTemplate[this._inputTemplate].options;
  get options() {return this._options; }
  get hasBoardMenu() {return this._boardMenu === ''; }
  toBoardMenu = () => this._boardMenu = '';
  resetBoardMenu = () => this._boardMenu = InputTemplate[this._inputTemplate].boardMenu;
  get boardMenu() {return this._boardMenu; }
  get hasPipe() {return this._pipe !== null; }
  resetPipe = () => this._pipe = InputTemplate[this._inputTemplate].pipe;
  get pipe() {return this._pipe; }
  get hasDefaultSelectMenu$() {return this._defaultSelectMenu$ !== null; }
  resetDefaultSelectMenu$ = () => this._defaultSelectMenu$ = InputTemplate[this._inputTemplate].defaultSelectMenu$;
  get defaultSelectMenu$() {return this._defaultSelectMenu$; }
  get hasTypeHtml() {return this._type === "html"; }
  toTypeHtml = () => this._type = "html";
  get hasTypeDate() {return this._type === "date"; }
  toTypeDate = () => this._type = "date";
  get hasTypeSearchTable() {return this._type === "searchTable"; }
  toTypeSearchTable = () => this._type = "searchTable";
  get hasTypeTime() {return this._type === "time"; }
  toTypeTime = () => this._type = "time";
  get hasTypeMultiOption() {return this._type === "multiOption"; }
  toTypeMultiOption = () => this._type = "multiOption";
  get hasTypeBooleanToggle() {return this._type === "booleanToggle"; }
  toTypeBooleanToggle = () => this._type = "booleanToggle";
  get hasTypeShortInput() {return this._type === "shortInput"; }
  toTypeShortInput = () => this._type = "shortInput";
  get hasTypeSelectOneMenu() {return this._type === "selectOneMenu"; }
  toTypeSelectOneMenu = () => this._type = "selectOneMenu";
  resetType = () => this._type = InputTemplate[this._inputTemplate].type;
  get type() {return this._type; }
  get hasTriggerOnClick() {return this._triggerOnClick === ''; }
  toTriggerOnClick = () => this._triggerOnClick = '';
  resetTriggerOnClick = () => this._triggerOnClick = InputTemplate[this._inputTemplate].triggerOnClick;
  get triggerOnClick() {return this._triggerOnClick; }
  _postChange = (postChange) => {
  this.postChange = postChange;
    return this;
  }
  postChange: (currentValue, oldValue, criteria?) => any;
  _preChange = (preChange) => {
  this.preChange = preChange;
    return this;
  }
  preChange: (currentValue, comingValue, criteria?) => any;
  _criteria = (criteria) => {
  this.criteria = criteria;
    return this;
  }
  criteria: any;
  _observable = (observable) => {
  this.observable = observable;
    return this;
  }
  observable: (inputValue, criteria?) => Observable<any>;
  _selectMenu$ = (selectMenu$) => {
  this.selectMenu$ = selectMenu$;
    return this;
  }
  selectMenu$: Observable<SelectItem<any>[]>;
  _tableData = (tableData) => {
  this.tableData = tableData;
    return this;
  }
  tableData: XeTableData<any>;
  static get shortInput() {return new InputTemplate('shortInput', null, null, null, null, null, null, null, null, "shortInput", null); }
  get isShortInput() {return this._inputTemplate === 'shortInput'; }
  get hasShortInput() {return this._inputTemplate === 'shortInput'; }
  static get booleanToggle() {return new InputTemplate('booleanToggle', null, null, "custom", null, null, null, null, null, "booleanToggle", null); }
  get isBooleanToggle() {return this._inputTemplate === 'booleanToggle'; }
  get hasBooleanToggle() {return this._inputTemplate === 'booleanToggle'; }
  static get tripUserStatus() {return new InputTemplate('tripUserStatus', null, "on", "custom", null, null, '', TripStatusPipe.instance, of(TripUserStatus.selectMenu), "selectOneMenu", null); }
  get isTripUserStatus() {return this._inputTemplate === 'tripUserStatus'; }
  get hasTripUserStatus() {return this._inputTemplate === 'tripUserStatus'; }
  static get selectOneMenu() {return new InputTemplate('selectOneMenu', null, "off", null, null, null, null, null, null, "selectOneMenu", null); }
  get isSelectOneMenu() {return this._inputTemplate === 'selectOneMenu'; }
  get hasSelectOneMenu() {return this._inputTemplate === 'selectOneMenu'; }
  static get date() {return new InputTemplate('date', null, null, null, null, null, null, XeDatePipe.instance, null, "date", null); }
  get isDate() {return this._inputTemplate === 'date'; }
  get hasDate() {return this._inputTemplate === 'date'; }
  static get time() {return new InputTemplate('time', null, null, null, '', null, null, XeTimePipe.instance, null, "time", ''); }
  get isTime() {return this._inputTemplate === 'time'; }
  get hasTime() {return this._inputTemplate === 'time'; }
  static get scheduledLocation() {return new InputTemplate('scheduledLocation', null, null, null, '', null, null, LocationPipe.instance, null, null, null); }
  get isScheduledLocation() {return this._inputTemplate === 'scheduledLocation'; }
  get hasScheduledLocation() {return this._inputTemplate === 'scheduledLocation'; }
  static get phone() {return new InputTemplate('phone', null, null, null, null, null, null, PhonePipe.instance, null, "shortInput", null); }
  get isPhone() {return this._inputTemplate === 'phone'; }
  get hasPhone() {return this._inputTemplate === 'phone'; }
  static get seats() {return new InputTemplate('seats', null, null, "custom", null, null, null, SeatPipe.instance, null, "html", null); }
  get isSeats() {return this._inputTemplate === 'seats'; }
  get hasSeats() {return this._inputTemplate === 'seats'; }
  static get money() {return new InputTemplate('money', null, null, null, null, null, null, MoneyPipe.instance, null, "shortInput", null); }
  get isMoney() {return this._inputTemplate === 'money'; }
  get hasMoney() {return this._inputTemplate === 'money'; }
  static get weekDays() {return new InputTemplate('weekDays', null, null, "custom", null, WeekDays, null, null, null, "multiOption", null); }
  get isWeekDays() {return this._inputTemplate === 'weekDays'; }
  get hasWeekDays() {return this._inputTemplate === 'weekDays'; }
  static get tableOrder() {return new InputTemplate('tableOrder', "", null, null, null, null, null, null, null, null, null); }
  get isTableOrder() {return this._inputTemplate === 'tableOrder'; }
  static get bussSchedulePoint() {return new InputTemplate('bussSchedulePoint', null, null, "custom", null, null, null, BussSchedulePointPipe.instance, null, "searchTable", null); }
  get isBussSchedulePoint() {return this._inputTemplate === 'bussSchedulePoint'; }
  get hasBussSchedulePoint() {return this._inputTemplate === 'bussSchedulePoint'; }
  static get pathPoint() {return new InputTemplate('pathPoint', null, null, "custom", null, null, null, PathPointPipe.instance, null, "searchTable", null); }
  get isPathPoint() {return this._inputTemplate === 'pathPoint'; }
  get hasPathPoint() {return this._inputTemplate === 'pathPoint'; }
  static get path() {return new InputTemplate('path', null, null, "custom", null, null, null, PathPipe.instance, null, "searchTable", null); }
  get isPath() {return this._inputTemplate === 'path'; }
  get hasPath() {return this._inputTemplate === 'path'; }
  static get location() {return new InputTemplate('location', null, null, "custom", null, null, null, LocationPipe.instance, null, "searchTable", null); }
  get isLocation() {return this._inputTemplate === 'location'; }
  get hasLocation() {return this._inputTemplate === 'location'; }

}

