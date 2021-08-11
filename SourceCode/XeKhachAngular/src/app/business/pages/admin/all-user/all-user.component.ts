import {Component} from '@angular/core';
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {XeTableData} from "../../../../framework/model/XeTableData";
import {User} from "../../../entities/User";

@Component({
  selector: 'xe-all-user',
  templateUrl: './all-user.component.html',
  styleUrls: ['./all-user.component.scss']
})
export class AllUserComponent extends FormAbstract {
  user = () => this.userTable.formData.share.entity;
  userTable: XeTableData = User.userTable();
}
