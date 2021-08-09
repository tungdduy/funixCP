import {AfterViewInit, Component} from '@angular/core';
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {AuthService} from "../../../../framework/auth/auth.service";
import {XeFormData} from "../../../../framework/model/XeFormData";
import {User} from "../../../entities/User";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {RoleInfo, RoleUtil} from "../../../../framework/util/role.util";
import {Company} from "../../../entities/Company";
import {PhonePipe} from "../../../../framework/components/pipes/phone-pipe";

@Component({
  selector: 'xe-my-account',
  styleUrls: ['my-account.component.scss'],
  templateUrl: 'my-account.component.html',
})
export class MyAccountComponent extends FormAbstract implements AfterViewInit {
  user: User = AuthUtil.instance.user;
  company = this.user?.employee?.company;
  initMe = () => this;
  roleInfos: RoleInfo[] = RoleUtil.getRolesInfo(this.user.role);

  screens = {
    account: 'account',
    password: 'password'
  };
  screen = new XeScreen(this.screens.account, 'user', undefined);

  handlers = [
    {
      name: "updatePassword",
      processor: (data) => this.authService.updatePassword(data),
    }
  ];

  userForm: XeFormData = User.userTable({
    formData: {
      header: {
        descField: undefined
      },
      share: {entity: this.user},
      fields: {
        4: {hidden: true}
      },
      onAvatarChange: (user) => {
        this.updateUser(user);
      },
      onSuccess: (user) => {
        this.updateUser(user);
      }
    }
  }).formData;
  companyForm = Company.companyTable({
    formData: {
      share: {entity: this.company}
    }
  }).formData;
  userFormClass = "col-md-6";
  companyFormClass = this.userFormClass;
  userBodyInfoClass;
  companyBodyInfoClass;
  startEditUser = () => {
    this.userFormClass = "col-md-8";
    this.companyFormClass = "col-md-4";
    this.userBodyInfoClass = "d-none";
  }
  startEditCompany = () => {
    this.userFormClass = "col-md-4";
    this.companyFormClass = "col-md-8";
    this.companyBodyInfoClass = "d-none";
  }
  cancelEdit = () => {
    this.userFormClass = "col-md-6";
    this.companyFormClass = "col-md-6";
    this.userBodyInfoClass = "";
    this.companyBodyInfoClass = "";
  }
  private updateUser(user) {
    this.user = user;
    AuthUtil.instance.setRepoUser(user);
  }

  constructor(private authService: AuthService) {
    super();
  }

  ngAfterViewInit() {
    super.ngAfterViewInit();
    this.refresh(this.user, "User");
  }
}
