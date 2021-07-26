import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'msg',
  templateUrl: './xe-message.component.html',
  styleUrls: ['./xe-message.component.scss']
})
export class XeMessageComponent implements OnInit {

  @Input() key;
  constructor() { }

  ngOnInit(): void {
  }

}
