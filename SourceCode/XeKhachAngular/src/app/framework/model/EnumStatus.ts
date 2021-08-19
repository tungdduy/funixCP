// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import {XeDatePipe} from "../components/pipes/date.pipe";
import {XeTimePipe} from "../components/pipes/time.pipe";
import {MoneyPipe} from "../components/pipes/money.pipe";
import {XePipe} from "../components/pipes/XePipe";
import {PhonePipe} from "../components/pipes/phone.pipe";
import {Options, WeekDays} from "./EntityEnum";
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


export class InputMode {
  constructor(private _inputMode) {}

  static readonly disabled = new InputMode('disabled');
  get isDisabled() {return this._inputMode === 'disabled'; }

  static readonly input = new InputMode('input');
  get isInput() {return this._inputMode === 'input'; }

  static readonly bareTextOnly = new InputMode('bareTextOnly');
  get isBareTextOnly() {return this._inputMode === 'bareTextOnly'; }

}


export class InputTemplate {
  constructor(private _inputTemplate, private _display: string, private _options: Options, private _pipe: XePipe, private _type: string) {}
  get isDisplayCustom() {return this._display === "custom"; }
  get options() {return this._options; }
  get hasOptions() {return this._options !== null; }
  get pipe() {return this._pipe; }
  get hasPipe() {return this._pipe !== null; }
  get isTypeMultiOption() {return this._type === "multiOption"; }
  get isTypeSelectOneMenu() {return this._type === "selectOneMenu"; }
  get isTypeTime() {return this._type === "time"; }
  get isTypeShortInput() {return this._type === "shortInput"; }
  get isTypeDate() {return this._type === "date"; }

  static readonly shortInput = new InputTemplate('shortInput', null, null, null, "shortInput");
  get isShortInput() {return this._inputTemplate === 'shortInput'; }

  static readonly selectOneMenu = new InputTemplate('selectOneMenu', null, null, null, "selectOneMenu");
  get isSelectOneMenu() {return this._inputTemplate === 'selectOneMenu'; }

  static readonly date = new InputTemplate('date', null, null, XeDatePipe.instance, "date");
  get isDate() {return this._inputTemplate === 'date'; }

  static readonly time = new InputTemplate('time', null, null, XeTimePipe.instance, "time");
  get isTime() {return this._inputTemplate === 'time'; }

  static readonly phone = new InputTemplate('phone', null, null, PhonePipe.instance, "shortInput");
  get isPhone() {return this._inputTemplate === 'phone'; }

  static readonly money = new InputTemplate('money', null, null, MoneyPipe.instance, "shortInput");
  get isMoney() {return this._inputTemplate === 'money'; }

  static readonly weekDays = new InputTemplate('weekDays', "custom", WeekDays, null, "multiOption");
  get isWeekDays() {return this._inputTemplate === 'weekDays'; }

}

