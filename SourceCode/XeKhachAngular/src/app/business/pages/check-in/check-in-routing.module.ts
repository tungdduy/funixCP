import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {Url} from "../../../framework/url/url.declare";
import {NotFoundComponent} from "../demo-pages/miscellaneous/not-found/not-found.component";
import {CheckInComponent} from "./check-in.component";

const routes = Url.app.CHECK_IN.__self.buildConfig();
const routes2 = [
  {path: '', component: CheckInComponent,
  children: [
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'forgot-password', component: ForgotPasswordComponent},
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: '**', component: NotFoundComponent}
  ]}
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CheckInRoutingModule {
  constructor() {
  }

}


