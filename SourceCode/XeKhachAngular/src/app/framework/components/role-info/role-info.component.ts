import {Component, Input, OnInit} from '@angular/core';
import {RoleInfo} from "../../util/role.util";

@Component({
  selector: 'role-info',
  templateUrl: './role-info.component.html',
  styleUrls: ['./role-info.component.scss']
})
export class RoleInfoComponent implements OnInit {

  @Input() clazz: string;
  @Input() roleInfos: RoleInfo[];
  constructor() { }

  ngOnInit(): void {
  }

}
