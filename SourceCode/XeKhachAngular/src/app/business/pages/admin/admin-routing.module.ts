import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "../../../framework/url/url.declare";
import {MyAccountComponent} from "./my-account/my-account.component";
import {AllUserComponent} from "./all-user/all-user.component";
import {MyTripComponent} from "./my-trip/my-trip.component";
import {CompanyManagerComponent} from "./company-manager/company-manager.component";
import {CallerEmployeeComponent} from "./caller-employee/caller-employee.component";
import {BussTypeComponent} from "./buss-type/buss-type.component";
import {BussComponent} from "./buss/buss.component";
import {BussEmployeeComponent} from "./buss-employee/buss-employee.component";
import {BussStopComponent} from "./buss-stop/buss-stop.component";
import {TicketComponent} from "./ticket/ticket.component";
import {AdminComponent} from "./admin.component";

const routes = [{
  path: '', component: AdminComponent,
  children: [
    { path: 'my-account', component: MyAccountComponent, canActivate: ["ADMIN.MY_ACCOUNT"] },
    { path: 'all-user', component: AllUserComponent, canActivate: ["ADMIN.ALL_USER"] },
    { path: 'my-trip', component: MyTripComponent, canActivate: ["ADMIN.MY_TRIP"] },
    { path: 'company-manager', component: CompanyManagerComponent, canActivate: ["ADMIN.COMPANY_MANAGER"] },
    { path: 'caller-employee', component: CallerEmployeeComponent, canActivate: ["ADMIN.CALLER_EMPLOYEE"] },
    { path: 'buss-type', component: BussTypeComponent, canActivate: ["ADMIN.BUSS_TYPE"] },
    { path: 'buss', component: BussComponent, canActivate: ["ADMIN.BUSS"] },
    { path: 'buss-employee', component: BussEmployeeComponent, canActivate: ["ADMIN.BUSS_EMPLOYEE"] },
    { path: 'buss-stop', component: BussStopComponent, canActivate: ["ADMIN.BUSS_STOP"] },
    { path: 'ticket', component: TicketComponent, canActivate: ["ADMIN.TICKET"] },
    { path: '', redirectTo: 'my-account', pathMatch: 'full' }
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: Url.app.ADMIN._self.activateProviders
})
export class AdminRoutingModule {
  constructor() {}
}
