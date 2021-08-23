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
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


export class InputMode {
  readonly name: string;
  constructor(private _inputMode, private _input: string, private _hideTitle: string, private _showTitle: string, private _disabled: string, private _forbidChange: string, private _html: string, private _text: string) {
    this.name = _inputMode;
  }
  get hasInput() {return this._input === ""; }
  get hasHideTitle() {return this._hideTitle === ''; }
  get hasShowTitle() {return this._showTitle === ''; }
  get hasDisabled() {return this._disabled === ""; }
  get hasForbidChange() {return this._forbidChange === ''; }
  get hasHtml() {return this._html === ''; }
  get hasText() {return this._text === ''; }
  static readonly input = new InputMode('input', "", null, null, null, null, null, null);
  get isInput() {return this._inputMode === 'input'; }
  static readonly disabled = new InputMode('disabled', null, null, null, "", null, null, null);
  get isDisabled() {return this._inputMode === 'disabled'; }
  static readonly inputNoTitle = new InputMode('inputNoTitle', '', '', null, null, null, null, null);
  get isInputNoTitle() {return this._inputMode === 'inputNoTitle'; }
  static readonly text = new InputMode('text', null, null, null, null, '', null, null);
  get isText() {return this._inputMode === 'text'; }
  static readonly html = new InputMode('html', null, null, null, null, '', null, null);
  get isHtml() {return this._inputMode === 'html'; }
  static readonly htmlTitle = new InputMode('htmlTitle', null, null, '', null, '', '', null);
  get isHtmlTitle() {return this._inputMode === 'htmlTitle'; }
  static readonly textTitle = new InputMode('textTitle', null, null, '', null, '', null, '');
  get isTextTitle() {return this._inputMode === 'textTitle'; }

}


export class InputTemplate {
  readonly name: string;
  constructor(private _inputTemplate, private _tableOrder: string, private _display: string, private _options: Options, private _pipe: XePipe, private _type: string) {
    this.name = _inputTemplate;
  }
  get hasTableOrder() {return this._tableOrder === ""; }
  get hasDisplayCustom() {return this._display === "custom"; }
  get options() {return this._options; }
  get hasOptions() {return this._options !== null; }
  get pipe() {return this._pipe; }
  get hasPipe() {return this._pipe !== null; }
  get hasTypeTime() {return this._type === "time"; }
  get hasTypeSelectOneMenu() {return this._type === "selectOneMenu"; }
  get hasTypeShortInput() {return this._type === "shortInput"; }
  get hasTypeBooleanToggle() {return this._type === "booleanToggle"; }
  get hasTypeMultiOption() {return this._type === "multiOption"; }
  get hasTypeSearchTable() {return this._type === "searchTable"; }
  get hasTypeDate() {return this._type === "date"; }
  _tableData = (tableData) => {
  this.tableData = tableData;
  return this;
  }
  tableData: XeTableData<any>;
  static readonly shortInput = new InputTemplate('shortInput', null, null, null, null, "shortInput");
  get isShortInput() {return this._inputTemplate === 'shortInput'; }
  static readonly booleanToggle = new InputTemplate('booleanToggle', null, "custom", null, null, "booleanToggle");
  get isBooleanToggle() {return this._inputTemplate === 'booleanToggle'; }
  static readonly selectOneMenu = new InputTemplate('selectOneMenu', null, null, null, null, "selectOneMenu");
  get isSelectOneMenu() {return this._inputTemplate === 'selectOneMenu'; }
  static readonly date = new InputTemplate('date', null, null, null, XeDatePipe.instance, "date");
  get isDate() {return this._inputTemplate === 'date'; }
  static readonly time = new InputTemplate('time', null, null, null, XeTimePipe.instance, "time");
  get isTime() {return this._inputTemplate === 'time'; }
  static readonly phone = new InputTemplate('phone', null, null, null, PhonePipe.instance, "shortInput");
  get isPhone() {return this._inputTemplate === 'phone'; }
  static readonly money = new InputTemplate('money', null, null, null, MoneyPipe.instance, "shortInput");
  get isMoney() {return this._inputTemplate === 'money'; }
  static readonly weekDays = new InputTemplate('weekDays', null, "custom", WeekDays, null, "multiOption");
  get isWeekDays() {return this._inputTemplate === 'weekDays'; }
  static readonly tableOrder = new InputTemplate('tableOrder', "", null, null, null, null);
  get isTableOrder() {return this._inputTemplate === 'tableOrder'; }
  static readonly pathPointSearch = new InputTemplate('pathPointSearch', null, "custom", null, PathPointPipe.instance, "searchTable");
  get isPathPointSearch() {return this._inputTemplate === 'pathPointSearch'; }
  static readonly pathSearch = new InputTemplate('pathSearch', null, "custom", null, PathPipe.instance, "searchTable");
  get isPathSearch() {return this._inputTemplate === 'pathSearch'; }
  static readonly locationSearch = new InputTemplate('locationSearch', null, "custom", null, LocationPipe.instance, "searchTable");
  get isLocationSearch() {return this._inputTemplate === 'locationSearch'; }

}

