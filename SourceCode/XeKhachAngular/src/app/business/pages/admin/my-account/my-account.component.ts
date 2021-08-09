import {AfterViewInit, Component} from '@angular/core';
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {AuthService} from "../../../../framework/auth/auth.service";
import {XeFormData} from "../../../../framework/model/XeFormData";
import {User} from "../../../entities/User";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {RoleInfo, RoleUtil} from "../../../../framework/util/role.util";

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
  userForm: XeFormData = {
    share: {entity: this.user},
    entityIdentifier: {
      className: "User",
      idFields: () => [
        {name: "userId", value: this.user?.userId}
      ]
    },
    grid: true,
    header: {
      profileImage: {name: 'profileImageUrl'},
      titleField: {name: 'fullName'},
      descField: {name: 'phoneNumber'},
    },
    fields: [
      {name: "username"},
      {name: "fullName", required: true},
      {name: "email", required: true},
      {name: "phoneNumber", required: true}
    ],
    onAvatarChange: (user) => {
      this.updateUser(user);
    },
    onSuccess: (user) => {
      this.updateUser(user);
    }
  };

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
