import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {InputTemplate} from "../../model/EnumStatus";
import {MultiOptionUtil, Options} from "../../model/EntityEnum";

@Component({
  selector: 'xe-multi-option',
  templateUrl: './multi-option.component.html',
  styleUrls: ['./multi-option.component.scss']
})
export class MultiOptionComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {  }

  @Input() template: InputTemplate;
  @Input() options: Options;
  action = MultiOptionUtil;

  private _value: string;
  @Output() valueChange = new EventEmitter<string>();
  @Input() get value(): string {
    return this._value;
  }
  set value(val) {
    this._value = val;
    this.valueChange.emit(this._value);
  }

  toggleOption(option: string) {
    this.value = this.action.toggle(this.options.ALL, this.value, option);
  }

  has(option: string) {
    return this.action.has(this.value, option);
  }
}
