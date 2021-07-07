import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {AdminComponent} from './admin.component';
import {AdminRoutingModule} from "./admin-routing.module";
import {BussStaffComponent} from "./buss-staff/buss-staff.component";

@NgModule({
  imports: [
    AdminRoutingModule,
    ngFormsModule,
    RouterModule,
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
