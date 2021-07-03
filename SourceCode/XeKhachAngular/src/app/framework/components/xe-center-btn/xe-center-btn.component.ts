import {Component, Input} from '@angular/core';

@Component({
  selector: 'xe-center-btn',
  templateUrl: './xe-center-btn.component.html',
  styleUrls: ['./xe-center-btn.component.scss']
})
export class XeCenterBtnComponent {
  @Input() btnText: string = "Đăng nhập";
  @Input() btnStatus: string = "info";
  @Input() btnType: string = "submit";
  @Input() disabled: boolean = false;
}
