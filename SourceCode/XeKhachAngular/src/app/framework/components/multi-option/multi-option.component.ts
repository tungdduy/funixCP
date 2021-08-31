import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {InputTemplate} from "../../model/EnumStatus";
import {MultiOptionUtil, Options} from "../../model/EntityEnum";

@Component({
  selector: 'xe-multi-option',
  templateUrl: './multi-option.component.html',
  styleUrls: ['./multi-option.component.scss']
})
export class MultiOptionComponent implements OnInit {

  @Input() template: InputTemplate;
  @Input() options: Options;
  action = MultiOptionUtil;
  @Output() valueChange = new EventEmitter<string>();

  constructor() {
  }

  private _value: string;

  @Input() get value(): string {
    return this._value;
  }

  set value(val) {
    this._value = val;
    this.valueChange.emit(this._value);
  }

  ngOnInit(): void {
  }

  toggleOption(option: string) {
    this.value = this.action.toggle(this.options.ALL, this.value, option);
  }

  has(option: string) {
    return this.action.has(this.value, option);
  }
}
