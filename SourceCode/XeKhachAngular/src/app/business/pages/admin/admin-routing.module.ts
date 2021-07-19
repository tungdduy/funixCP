import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "../../../framework/url/url.declare";
import {RouterBuilder} from "../../../framework/url/router.builder";
import {BussComponent} from "./buss/buss.component";
import {BussEmployeeComponent} from "./buss-employee/buss-employee.component";
import {MyAccountComponent} from "./my-account/my-account.component";
import {AdminComponent} from "./admin.component";

const routes = RouterBuilder.build(Url.app.ADMIN);
const tRoutes = [{
  path: '', component: AdminComponent,
  children: [
    {path: 'buss', component: BussComponent},
    {path: 'buss-employee', component: BussEmployeeComponent},
    {path: 'my-account', component: MyAccountComponent, canActivate: ["ADMIN.MY_ACCOUNT"]}
  ]
}];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: Url.app.ADMIN._self.activateProviders
})
export class AdminRoutingModule {
  constructor() {}
}
