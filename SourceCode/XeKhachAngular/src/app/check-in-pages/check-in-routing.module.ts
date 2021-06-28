import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {CheckInComponent} from "./check-in.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {UrlUtil} from "../_core/static/utils/url.util";

const routes: Routes = [{
  path: '', component: CheckInComponent,
  children: UrlUtil.buildShortPath([
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent
  ])
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CheckInRoutingModule {
}


