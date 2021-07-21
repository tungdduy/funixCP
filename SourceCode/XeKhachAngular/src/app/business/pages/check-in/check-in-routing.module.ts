import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "../../../framework/url/url.declare";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {LogoutComponent} from "./logout/logout.component";
import {CheckInComponent} from "./check-in.component";
import {MyTripComponent} from "./my-trip/my-trip.component";

const routes = [{
  path: '', component: CheckInComponent,
  children: [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'forgot-password', component: ForgotPasswordComponent },
    { path: 'logout', component: LogoutComponent },
    { path: 'my-trip', component: MyTripComponent },
    { path: '', redirectTo: 'login', pathMatch: 'full' }
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: Url.app.CHECK_IN._self.activateProviders
})
export class CheckInRoutingModule {
  constructor() {}
}
