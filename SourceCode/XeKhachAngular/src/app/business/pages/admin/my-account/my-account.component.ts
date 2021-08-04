import {Component} from '@angular/core';
import {FormAbstract} from "../../../abstract/form.abstract";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {AuthService} from "../../../../framework/auth/auth.service";
import {XeFormData} from "../../../abstract/XeFormData";
import {User} from "../../../entities/user";

@Component({
  selector: 'xe-my-account',
  styleUrls: ['my-account.component.scss'],
  templateUrl: 'my-account.component.html',
})
export class MyAccountComponent extends FormAbstract {
  public user = AuthUtil.instance.user;

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
        {name: "userId", value: this.user.userId}
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

}
