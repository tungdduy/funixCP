import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {CheckInComponent} from './check-in.component';
import {CheckInRoutingModule} from "./check-in-routing.module";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {ThemeModule} from "../../../@theme/theme.module";
import {NbCardModule, NbLayoutModule} from "@nebular/theme";

@NgModule({
  imports: [
    CheckInRoutingModule,
    ngFormsModule,
    RouterModule,
    ThemeModule,
    NbCardModule,
    NbLayoutModule,
  ],
  declarations: [
    CheckInComponent,
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent,
  ],
  exports: [
    CheckInComponent
  ]
})
export class CheckInModule { }
