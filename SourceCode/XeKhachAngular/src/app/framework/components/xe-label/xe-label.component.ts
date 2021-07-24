import {Component, Input, OnInit} from '@angular/core';
import {XeLbl} from "../../../business/i18n";

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

  constructor() { }

  ngOnInit(): void {
    this.textValue = XeLbl(this.key);
    this.breakLine = this.br === '';
    this.isParagraph = this.p === '';
  }

}
