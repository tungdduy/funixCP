import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {LogoutComponent} from "./logout/logout.component";
import {CheckInComponent} from './check-in.component';
// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {NbCardModule, NbLayoutModule} from "@nebular/theme";
import {ThemeModule} from "../../../@theme/theme.module";
import { LogoutComponent } from './logout/logout.component';

// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

@NgModule({
// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //
NbCardModule,
    NbLayoutModule,
    ThemeModule,

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
