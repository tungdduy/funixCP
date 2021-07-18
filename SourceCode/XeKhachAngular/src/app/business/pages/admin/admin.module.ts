import {BussStaffComponent} from "./buss-staff/buss-staff.component";
import {AdminComponent} from './admin.component';
import {AdminRoutingModule} from "./admin-routing.module";
// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";

// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //

@NgModule({
  imports: [
    AdminRoutingModule,
    ngFormsModule,
    RouterModule,
// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //
  ],
  declarations: [
    AdminComponent,
    BussStaffComponent,
  ],
  exports: [
    AdminComponent
  ]
})
export class AdminModule { }
