import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "../../../framework/url/url.declare";
import {MyAccountComponent} from "./my-account/my-account.component";
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
    { path: 'my-account', component: MyAccountComponent },
    { path: 'my-trip', component: MyTripComponent, canActivate: ["ADMIN.MY_TRIP"] },
    { path: 'company-manager', component: CompanyManagerComponent },
    { path: 'caller-employee', component: CallerEmployeeComponent },
    { path: 'buss-type', component: BussTypeComponent },
    { path: 'buss', component: BussComponent },
    { path: 'buss-employee', component: BussEmployeeComponent },
    { path: 'buss-stop', component: BussStopComponent },
    { path: 'ticket', component: TicketComponent },
    { path: '', redirectTo: 'my-account', pathMatch: 'full' },
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
