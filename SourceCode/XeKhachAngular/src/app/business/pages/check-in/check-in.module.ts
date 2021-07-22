import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {LogoutComponent} from "./logout/logout.component";
import {CheckInComponent} from './check-in.component';
// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

import {NgModule} from '@angular/core';
import {NbCardModule, NbLayoutModule} from "@nebular/theme";
import {ThemeModule} from "../../../@theme/theme.module";
import {RouterModule} from "@angular/router";
import {CheckInRoutingModule} from "./check-in-routing.module";

// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

@NgModule({
// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //
  imports: [
    CheckInRoutingModule,
    NbCardModule,
    NbLayoutModule,
    ThemeModule,
    RouterModule,
  ],

// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //
  declarations: [
    CheckInComponent,
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent,
    LogoutComponent,
  ],
  exports: [
    CheckInComponent
  ]
})
export class CheckInModule { }
