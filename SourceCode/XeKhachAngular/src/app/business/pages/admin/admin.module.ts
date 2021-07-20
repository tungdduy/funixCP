import {MyAccountComponent} from "./my-account/my-account.component";
import {MyTripComponent} from "./my-trip/my-trip.component";
import {CompanyManagerComponent} from "./company-manager/company-manager.component";
import {CallerEmployeeComponent} from "./caller-employee/caller-employee.component";
import {BussTypeComponent} from "./buss-type/buss-type.component";
import {BussComponent} from "./buss/buss.component";
import {BussEmployeeComponent} from "./buss-employee/buss-employee.component";
import {BussStopComponent} from "./buss-stop/buss-stop.component";
import {TicketComponent} from "./ticket/ticket.component";
import {AdminComponent} from './admin.component';
import {AdminRoutingModule} from "./admin-routing.module";
// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {NbCardModule, NbIconModule, NbLayoutModule, NbMenuModule} from "@nebular/theme";
import {ThemeModule} from "../../../@theme/theme.module";

// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

@NgModule({
// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //
  imports: [
    AdminRoutingModule,
    ngFormsModule,
    RouterModule,
    NbMenuModule,
    ThemeModule,
    NbCardModule,
    NbLayoutModule,

// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //
  ],
  declarations: [
    AdminComponent,
    MyAccountComponent,
    MyTripComponent,
    CompanyManagerComponent,
    CallerEmployeeComponent,
    BussTypeComponent,
    BussComponent,
    BussEmployeeComponent,
    BussStopComponent,
    TicketComponent,
  ],
  exports: [
    AdminComponent
  ]
})
export class AdminModule { }
