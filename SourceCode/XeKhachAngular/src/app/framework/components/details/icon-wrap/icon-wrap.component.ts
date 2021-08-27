import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {IconOption} from "../../../model/XeTableData";

export interface IconWrapper {
  icon?: IconOption;
  clazz?: string;
  action?: () => any;
  content?: any;
}

@Component({
  selector: 'icon-wrap',
  templateUrl: './icon-wrap.component.html',
  styleUrls: ['./icon-wrap.component.scss']
})
export class IconWrapComponent implements OnInit {
  @Input() iconPre: string;
  @Input() iconAfter: string;
  @Input() iconOnly: string;
  @Input() clazz: string;
  @Input() wrapper: IconWrapper;

  ngOnInit(): void {
    this.wrapper = this.wrapper ? this.wrapper : {
      clazz: this.clazz,
      icon: {
        iconPre: this.iconPre,
        iconAfter: this.iconAfter,
        iconOnly: this.iconOnly
      }
    };
  }

}
