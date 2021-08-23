import {MyAccountComponent} from "./my-account/my-account.component";
import {MyCompanyComponent} from "./my-company/my-company.component";
import {AllUserComponent} from "./all-user/all-user.component";
import {MyTripComponent} from "./my-trip/my-trip.component";
import {CompanyManagerComponent} from "./company-manager/company-manager.component";
import {BussTypeComponent} from "./buss-type/buss-type.component";
import {BussComponent} from "./buss/buss.component";
import {EmployeeComponent} from "./employee/employee.component";
import {PathComponent} from "./path/path.component";
import {TicketComponent} from "./ticket/ticket.component";
import {AdminComponent} from './admin.component';
// ____________________ ::HEADER_IMPORT_SEPARATOR:: ____________________ //
import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {
    NbBadgeModule,
    NbButtonModule,
    NbCardModule, NbIconModule,
    NbInputModule,
    NbLayoutModule, NbListModule,
    NbMenuModule,
    NbRadioModule, NbStepperModule, NbTreeGridModule,
    NbUserModule
} from "@nebular/theme";
import {ThemeModule} from "../../../@theme/theme.module";
import {AdminRoutingModule} from "./admin-routing.module";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {CommonComponentsModule} from "../../../framework/components/common-components.module";
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
        NbRadioModule,
        NbButtonModule,
        NbInputModule,
        CommonComponentsModule,
        NbUserModule,
        NbIconModule,
        NbBadgeModule,
        NbListModule,
        NbTreeGridModule,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatFormFieldModule,
        MatInputModule,
        NbStepperModule
    ],
// ____________________ ::MODULE_IMPORT_SEPARATOR:: ____________________ //

  declarations: [
    AdminComponent,
    MyAccountComponent,
    MyCompanyComponent,
    AllUserComponent,
    MyTripComponent,
    CompanyManagerComponent,
    BussTypeComponent,
    BussComponent,
    EmployeeComponent,
    PathComponent,
    TicketComponent,
  ],
  exports: [
    AdminComponent
  ]
})
export class AdminModule { }
