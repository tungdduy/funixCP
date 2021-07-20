import {Component, Input} from '@angular/core';
import {XeLabel} from "../../../business/i18n";

@Component({
  selector: 'xe-center-btn',
  templateUrl: './xe-center-btn.component.html',
  styleUrls: ['./xe-center-btn.component.scss']
})
export class XeCenterBtnComponent {
  @Input() lblKey: string = "LOGIN";
  @Input() btnStatus: string = "info";
  @Input() btnType: string = "submit";
  @Input() disabled: boolean = false;
  private _label: string;
  get label(): string {
    if (!this._label) {
      this._label = XeLabel[this.lblKey] ? XeLabel[this.lblKey] : this.lblKey;
    }
    return this._label;
  }
}
