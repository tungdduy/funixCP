import {AfterViewInit, Component} from '@angular/core';
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {AuthService} from "../../../../framework/auth/auth.service";
import {User} from "../../../entities/User";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {RoleInfo, RoleUtil} from "../../../../framework/util/role.util";
import {Company} from "../../../entities/Company";
import {Notifier} from "../../../../framework/notify/notify.service";

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
  screen = new XeScreen({home: this.screens.account, homeIcon: 'user'});

  handlers = [
    {
      name: "updatePassword",
      processor: (data) => this.authService.updatePassword(data),
    }
  ];

  userForm = User.tableData({
    formData: {
      header: {
        descField: undefined
      },
      share: {entity: this.user},
      fields: [{}, {}, {}, {}, undefined],
      action: {
        postUpdateProfile: (user) => {
          this.updateUser(user);
        },
        postUpdate: (user) => {
          this.updateUser(user);
        },
        postCancel: () => this.cancelEdit(),
        preEdit: () => this.startEditUser(),
      }
    }
  }, this.user).formData;
  companyForm = Company.tableData({
    formData: {
      share: {entity: this.user?.employee?.company},
      action: {
        postCancel: () => this.cancelEdit(),
        preEdit: () => this.startEditCompany()
      }
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

  private updateUser(user: User) {
    this.user = user;
    this.company = user?.employee?.company;
    AuthUtil.instance.setRepoUser(user);
  }

  constructor(private authService: AuthService) {
    super();
    this.subscriptions.push(this.refresh$(this.user, User).subscribe(
      newUsers => this.updateUser(newUsers[0]),
      error => Notifier.httpErrorResponse(error)
    ));
  }


}
