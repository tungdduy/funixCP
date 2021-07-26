import {Component} from '@angular/core';
import {FormAbstract} from "../../../abstract/form.abstract";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {AuthService} from "../../../../framework/auth/auth.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Notifier} from "../../../../framework/notify/notify.service";

@Component({
  selector: 'xe-my-account',
  styleUrls: ['my-account.component.scss'],
  templateUrl: 'my-account.component.html',
})
export class MyAccountComponent extends FormAbstract {
  constructor(private authService: AuthService) {
    super();
  }

  public user = AuthUtil.user;

  handlers = [
    {
      name: "userInfo",
      processor: (data) => this.authService.updateUser(data),
      success: {call: () => AuthUtil.setRepoUser(this.user)}
    },
    {
      name: "updatePassword",
      processor: (data) => this.authService.updatePassword(data),
    }
  ];

  subscriptions = [];
  onProfileImageChange(file: File) {
    const formData = new FormData();
    formData.append("profileImage", file);
    formData.append("userId", this.user.userId);
    this.subscriptions.push(
      this.authService.updateProfileImage(formData).subscribe(
        (response: string) => {
          console.log(response);
        },
        (error: HttpErrorResponse) => {
          Notifier.httpErrorResponse(error);
        }
      )
    );
  }

}
