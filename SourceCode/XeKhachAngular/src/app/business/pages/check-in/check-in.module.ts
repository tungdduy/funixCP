import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {CheckInComponent} from './check-in.component';
import {CheckInRoutingModule} from "./check-in-routing.module";
// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {NbCardModule, NbLayoutModule} from "@nebular/theme";
import {ThemeModule} from "../../../@theme/theme.module";
import {XeLinkComponent} from "../../../framework/components/xe-link/xe-link.component";

// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

@NgModule({
  imports: [
    CheckInRoutingModule,
    ngFormsModule,
    RouterModule,
// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //
NbCardModule,
    NbLayoutModule,
    ThemeModule,
// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //
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
