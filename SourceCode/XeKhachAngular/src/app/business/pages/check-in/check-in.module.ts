import {NgModule} from '@angular/core';
import {CheckInComponent} from './check-in.component';
import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbIconModule,
  NbInputModule,
  NbLayoutModule,
  NbMenuModule
} from "@nebular/theme";
import {ThemeModule} from "../../../@theme/theme.module";
import {FormsModule as ngFormsModule} from "@angular/forms";
import {LoginComponent} from "./login/login.component";
import {RouterModule} from "@angular/router";
import {CheckInRoutingModule} from "./check-in-routing.module";
import {RegisterComponent} from "./register/register.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";

@NgModule({
  imports: [
    CheckInRoutingModule,
    NbInputModule,
    NbCardModule,
    NbButtonModule,
    NbActionsModule,
    NbCheckboxModule,
    NbIconModule,
    ngFormsModule,
    NbMenuModule,
    RouterModule,
    ThemeModule,
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
