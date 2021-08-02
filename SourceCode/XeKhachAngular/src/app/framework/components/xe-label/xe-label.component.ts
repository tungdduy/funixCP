import {Component, Input, OnInit} from '@angular/core';
import {XeLbl} from "../../../business/i18n";
import {Message} from "../../model/message.model";

@Component({
  selector: 'lbl',
  templateUrl: './xe-label.component.html',
  styleUrls: ['./xe-label.component.scss']
})
export class XeLabelComponent implements OnInit {


  @Input("key") key;
  textValue: string;

  @Input("br") br;
  breakLine: boolean;

  @Input() p: any;
  isParagraph: boolean;

  constructor() {
  }

  _msg: Message;

  @Input("msg")
  get msg() {
    return this._msg;
  }

  set msg(val: Message) {

    this._msg = val;
    if (!!val) {
      this.textValue = XeLbl(this._msg.code);
      setTimeout(() => {
        this._msg = undefined;
        this.textValue = undefined;
      }, 5000);
    }
  }

  setMessage(val: Message) {
    this.msg = val;
  }

  get msgClass() {
    if (this._msg !== undefined) {
      return "text-" + this._msg.state;
    }
    return "";
  }

  ngOnInit(): void {
    if (!this.textValue) {
      this.textValue = XeLbl(this.key);
    }
    this.breakLine = this.br === '';
    this.isParagraph = this.p === '';
  }

}
