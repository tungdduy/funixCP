import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./screen/account/login/login.component";
import {RegisterComponent} from "./screen/account/register/register.component";
import {AppUrl} from "./static/url";

const routes: Routes = [
  { path: AppUrl.LOGIN, component: LoginComponent },
  { path: AppUrl.REGISTER, component: RegisterComponent },
  { path: AppUrl.EMPTY, redirectTo: AppUrl.LOGIN, pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
