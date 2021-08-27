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
import {Observable} from "rxjs";
import {AutoInputModel} from "./AutoInputModel";
import {BussSchedulePointPipe} from "../components/pipes/BussSchedulePointPipe";
import {SeatPipe} from "../components/pipes/SeatPipe";
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


export class InputMode {
  readonly name: string;
  constructor(private _inputMode, private _input: string, private _readonly: string, private _hideTitle: string, private _showTitle: string, private _acceptAnyString: string, private _disabled: string, private _forbidChange: string, private _html: string, private _text: string) {
    this.name = _inputMode;
  }
  get hasInput() {return this._input === ""; }
  get input() {return this._input; }
  get hasReadonly() {return this._readonly === ""; }
  get readonly() {return this._readonly; }
  get hasHideTitle() {return this._hideTitle === ''; }
  get hideTitle() {return this._hideTitle; }
  get hasShowTitle() {return this._showTitle === ''; }
  get showTitle() {return this._showTitle; }
  get hasAcceptAnyString() {return this._acceptAnyString === ""; }
  get acceptAnyString() {return this._acceptAnyString; }
  get hasDisabled() {return this._disabled === ""; }
  get disabled() {return this._disabled; }
  get hasForbidChange() {return this._forbidChange === ''; }
  get forbidChange() {return this._forbidChange; }
  get hasHtml() {return this._html === ''; }
  get html() {return this._html; }
  get hasText() {return this._text === ''; }
  get text() {return this._text; }
  static readonly input = new InputMode('input', "", null, null, null, null, null, null, null, null);
  get isInput() {return this._inputMode === 'input'; }
  static readonly disabled = new InputMode('disabled', null, null, null, null, null, "", null, null, null);
  get isDisabled() {return this._inputMode === 'disabled'; }
  static readonly readonly = new InputMode('readonly', null, "", null, null, null, null, null, null, null);
  get isReadonly() {return this._inputMode === 'readonly'; }
  static readonly selectOnly = new InputMode('selectOnly', '', '', null, null, null, null, null, null, null);
  get isSelectOnly() {return this._inputMode === 'selectOnly'; }
  static readonly acceptAnyString = new InputMode('acceptAnyString', null, null, null, null, "", null, null, null, null);
  get isAcceptAnyString() {return this._inputMode === 'acceptAnyString'; }
  static readonly inputNoTitle = new InputMode('inputNoTitle', '', null, '', null, null, null, null, null, null);
  get isInputNoTitle() {return this._inputMode === 'inputNoTitle'; }
  static readonly text = new InputMode('text', null, null, null, null, null, null, '', null, null);
  get isText() {return this._inputMode === 'text'; }
  static readonly html = new InputMode('html', null, null, null, null, null, null, '', null, null);
  get isHtml() {return this._inputMode === 'html'; }
  static readonly htmlTitle = new InputMode('htmlTitle', null, null, null, '', null, null, '', '', null);
  get isHtmlTitle() {return this._inputMode === 'htmlTitle'; }
  static readonly textTitle = new InputMode('textTitle', null, null, null, '', null, null, '', null, '');
  get isTextTitle() {return this._inputMode === 'textTitle'; }

}


export class BussSchemeMode {
  readonly name: string;
  constructor(private _bussSchemeMode, private _tripAdmin: string, private _readonly: string, private _edit: string, private _ordering: string) {
    this.name = _bussSchemeMode;
  }
  get hasTripAdmin() {return this._tripAdmin === ""; }
  get tripAdmin() {return this._tripAdmin; }
  get hasReadonly() {return this._readonly === ""; }
  get readonly() {return this._readonly; }
  get hasEdit() {return this._edit === ""; }
  get edit() {return this._edit; }
  get hasOrdering() {return this._ordering === ""; }
  get ordering() {return this._ordering; }
  static readonly edit = new BussSchemeMode('edit', null, null, "", null);
  get isEdit() {return this._bussSchemeMode === 'edit'; }
  static readonly readonly = new BussSchemeMode('readonly', null, "", null, null);
  get isReadonly() {return this._bussSchemeMode === 'readonly'; }
  static readonly ordering = new BussSchemeMode('ordering', null, null, null, "");
  get isOrdering() {return this._bussSchemeMode === 'ordering'; }
  static readonly tripAdmin = new BussSchemeMode('tripAdmin', "", null, null, null);
  get isTripAdmin() {return this._bussSchemeMode === 'tripAdmin'; }

}


export class SeatStatus {
  readonly name: string;
  constructor(private _seatStatus, private _classes: string) {
    this.name = _seatStatus;
  }
  get hasClassesSeatSelected() {return this._classes === "seat-selected"; }
  get hasClassesSeatLockedByBuss() {return this._classes === "seat-locked-by-buss"; }
  get hasClassesSeatAvailable() {return this._classes === "seat-available"; }
  get hasClassesSeatHidden() {return this._classes === "seat-hidden"; }
  get hasClassesSeatLockedByTrip() {return this._classes === "seat-locked-by-trip"; }
  get hasClassesSeatBooked() {return this._classes === "seat-booked"; }
  get hasClassesSeatLocked() {return this._classes === "seat-locked"; }
  get classes() {return this._classes; }
  static readonly locked = new SeatStatus('locked', "seat-locked");
  get isLocked() {return this._seatStatus === 'locked'; }
  static readonly lockedByBuss = new SeatStatus('lockedByBuss', "seat-locked-by-buss");
  get isLockedByBuss() {return this._seatStatus === 'lockedByBuss'; }
  static readonly lockedByTrip = new SeatStatus('lockedByTrip', "seat-locked-by-trip");
  get isLockedByTrip() {return this._seatStatus === 'lockedByTrip'; }
  static readonly hidden = new SeatStatus('hidden', "seat-hidden");
  get isHidden() {return this._seatStatus === 'hidden'; }
  static readonly available = new SeatStatus('available', "seat-available");
  get isAvailable() {return this._seatStatus === 'available'; }
  static readonly booked = new SeatStatus('booked', "seat-booked");
  get isBooked() {return this._seatStatus === 'booked'; }
  static readonly selected = new SeatStatus('selected', "seat-selected");
  get isSelected() {return this._seatStatus === 'selected'; }

}


export class InputTemplate {
  readonly name: string;
  constructor(private _inputTemplate, private _tableOrder: string, private _display: string, private _autoInput: string, private _options: Options, private _pipe: XePipe, private _type: string, private _triggerOnClick: string) {
    this.name = _inputTemplate;
  }
  get hasTableOrder() {return this._tableOrder === ""; }
  get tableOrder() {return this._tableOrder; }
  get hasDisplayCustom() {return this._display === "custom"; }
  get display() {return this._display; }
  get hasAutoInput() {return this._autoInput === ''; }
  get autoInput() {return this._autoInput; }
  get hasOptions() {return this._options !== null; }
  get options() {return this._options; }
  get hasPipe() {return this._pipe !== null; }
  get pipe() {return this._pipe; }
  get hasTypeShortInput() {return this._type === "shortInput"; }
  get hasTypeBooleanToggle() {return this._type === "booleanToggle"; }
  get hasTypeSearchTable() {return this._type === "searchTable"; }
  get hasTypeDate() {return this._type === "date"; }
  get hasTypeMultiOption() {return this._type === "multiOption"; }
  get hasTypeHtml() {return this._type === "html"; }
  get hasTypeSelectOneMenu() {return this._type === "selectOneMenu"; }
  get hasTypeTime() {return this._type === "time"; }
  get type() {return this._type; }
  get hasTriggerOnClick() {return this._triggerOnClick === ''; }
  get triggerOnClick() {return this._triggerOnClick; }
  _postChange = (postChange) => {
  this.postChange = postChange;
    return this;
  }
  postChange: (inputValue, criteria?) => any;
  _preChange = (preChange) => {
  this.preChange = preChange;
    return this;
  }
  preChange: (inputValue, criteria?) => any;
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
  _tableData = (tableData) => {
  this.tableData = tableData;
    return this;
  }
  tableData: XeTableData<any>;
  static readonly shortInput = new InputTemplate('shortInput', null, null, null, null, null, "shortInput", null);
  get isShortInput() {return this._inputTemplate === 'shortInput'; }
  static readonly booleanToggle = new InputTemplate('booleanToggle', null, "custom", null, null, null, "booleanToggle", null);
  get isBooleanToggle() {return this._inputTemplate === 'booleanToggle'; }
  static readonly selectOneMenu = new InputTemplate('selectOneMenu', null, null, null, null, null, "selectOneMenu", null);
  get isSelectOneMenu() {return this._inputTemplate === 'selectOneMenu'; }
  static readonly date = new InputTemplate('date', null, null, null, null, XeDatePipe.instance, "date", null);
  get isDate() {return this._inputTemplate === 'date'; }
  static readonly time = new InputTemplate('time', null, null, '', null, XeTimePipe.instance, "time", '');
  get isTime() {return this._inputTemplate === 'time'; }
  static readonly scheduledLocation = new InputTemplate('scheduledLocation', null, null, '', null, LocationPipe.instance, null, null);
  get isScheduledLocation() {return this._inputTemplate === 'scheduledLocation'; }
  static readonly phone = new InputTemplate('phone', null, null, null, null, PhonePipe.instance, "shortInput", null);
  get isPhone() {return this._inputTemplate === 'phone'; }
  static readonly seats = new InputTemplate('seats', null, "custom", null, null, SeatPipe.instance, "html", null);
  get isSeats() {return this._inputTemplate === 'seats'; }
  static readonly money = new InputTemplate('money', null, null, null, null, MoneyPipe.instance, "shortInput", null);
  get isMoney() {return this._inputTemplate === 'money'; }
  static readonly weekDays = new InputTemplate('weekDays', null, "custom", null, WeekDays, null, "multiOption", null);
  get isWeekDays() {return this._inputTemplate === 'weekDays'; }
  static readonly tableOrder = new InputTemplate('tableOrder', "", null, null, null, null, null, null);
  get isTableOrder() {return this._inputTemplate === 'tableOrder'; }
  static readonly bussSchedulePoint = new InputTemplate('bussSchedulePoint', null, "custom", null, null, BussSchedulePointPipe.instance, "searchTable", null);
  get isBussSchedulePoint() {return this._inputTemplate === 'bussSchedulePoint'; }
  static readonly pathPoint = new InputTemplate('pathPoint', null, "custom", null, null, PathPointPipe.instance, "searchTable", null);
  get isPathPoint() {return this._inputTemplate === 'pathPoint'; }
  static readonly path = new InputTemplate('path', null, "custom", null, null, PathPipe.instance, "searchTable", null);
  get isPath() {return this._inputTemplate === 'path'; }
  static readonly location = new InputTemplate('location', null, "custom", null, null, LocationPipe.instance, "searchTable", null);
  get isLocation() {return this._inputTemplate === 'location'; }

}

