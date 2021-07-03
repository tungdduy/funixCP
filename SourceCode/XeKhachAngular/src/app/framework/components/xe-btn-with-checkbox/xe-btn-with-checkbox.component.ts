import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'xe-btn-with-checkbox',
  templateUrl: './xe-btn-with-checkbox.component.html',
  styleUrls: ['./xe-btn-with-checkbox.component.scss']
})
export class XeBtnWithCheckboxComponent {
  @Input() cbxText: string = "Ghi nhớ";
  @Input() cbxHide?: boolean;
  cbxValue: boolean;
  @Output() cbxEvent = new EventEmitter<boolean>();
  emitCbxValue() {
    this.cbxEvent.emit(this.cbxValue);
  }

  @Input() btnType: string = "submit";
  @Input() btnText: string = "Đăng nhập";

}
