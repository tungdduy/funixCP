import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {XeInputComponent} from "./xe-input/xe-input.component";
import {XeLabelComponent} from "./xe-label/xe-label.component";
import {XeLinkComponent} from "./xe-link/xe-link.component";
import {XeBtnComponent} from "./xe-btn/xe-btn.component";
import {RouterModule} from "@angular/router";
import {XeFormComponent} from './xe-form/xe-form.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PhonePipe} from "./pipes/phone.pipe";

import {
  NbAutocompleteModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbFormFieldModule,
  NbIconLibraries,
  NbIconModule,
  NbInputModule,
  NbSelectModule,
  NbTimepickerModule,
  NbTooltipModule,
  NbUserModule
} from "@nebular/theme";
import {XeBasicFormComponent} from './xe-basic-form/xe-basic-form.component';
import {XeTableComponent} from './xe-table/xe-table.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorIntl, MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatInputModule} from "@angular/material/input";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {CustomPaginator} from "./custom/CustomPaginatorConfiguration";
import {UserTableComponent} from './user-table/user-table.component';
import {XeNavComponent} from './xe-nav/xe-nav.component';
import {BasicBussSchemeComponent} from './basic-buss-scheme/basic-buss-scheme.component';
import {RoleInfoComponent} from './role-info/role-info.component';
import {IconWrapComponent} from './details/icon-wrap/icon-wrap.component';
import {LocationPipe} from "./pipes/location.pipe";
import {OverlayModule} from "@angular/cdk/overlay";
import {MoneyPipe} from "./pipes/money.pipe";
import {DATE_FORMATS, XeDatePipe} from "./pipes/date.pipe";
import {XeTimePipe} from "./pipes/time.pipe";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, NativeDateModule} from "@angular/material/core";
import {MAT_MOMENT_DATE_ADAPTER_OPTIONS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {MatButtonModule} from "@angular/material/button";
import {MultiOptionComponent} from './multi-option/multi-option.component';
import {PathPipe} from "./pipes/Path.pipe";
import {PathPointPipe} from "./pipes/PathPoint.pipe";
import {BussSchedulePointPipe} from "./pipes/BussSchedulePointPipe";
import {SeatPipe} from "./pipes/SeatPipe";
import {TripStatusPipe} from "./pipes/TripStatusPipe";
import {XeDateTimePipe} from "./pipes/DateTimePipe";


@NgModule({
  declarations: [
    XeInputComponent,
    XeLabelComponent,
    XeLinkComponent,
    XeBtnComponent,
    XeFormComponent,
    XeBasicFormComponent,
    XeTableComponent,
    UserTableComponent,
    XeNavComponent,
    BasicBussSchemeComponent,
    RoleInfoComponent,
    IconWrapComponent,
    PhonePipe,
    LocationPipe,
    MoneyPipe,
    XeDatePipe,
    XeTimePipe,
    PathPointPipe,
    PathPipe,
    MultiOptionComponent,
    BussSchedulePointPipe,
    SeatPipe,
    TripStatusPipe,
    XeDateTimePipe
  ],
  exports: [
    XeInputComponent,
    XeLabelComponent,
    XeLinkComponent,
    XeBtnComponent,
    XeFormComponent,
    XeBasicFormComponent,
    XeTableComponent,
    UserTableComponent,
    XeNavComponent,
    BasicBussSchemeComponent,
    RoleInfoComponent,
    IconWrapComponent,
    PhonePipe,
    LocationPipe,
    MoneyPipe,
    XeDatePipe,
    XeTimePipe,
    PathPointPipe,
    PathPipe,
    BussSchedulePointPipe,
    SeatPipe,
    TripStatusPipe,
    XeDateTimePipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    NbInputModule,
    NbButtonModule,
    NbIconModule,
    NbFormFieldModule,
    NbCardModule,
    MatFormFieldModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    NbUserModule,
    MatCheckboxModule,
    NbCheckboxModule,
    NbSelectModule,
    NbTimepickerModule,
    OverlayModule,
    ReactiveFormsModule,
    NbAutocompleteModule,
    MatDatepickerModule,
    NativeDateModule,
    MatButtonModule,
    NbTooltipModule
  ],
  providers: [
    {provide: MatPaginatorIntl, useValue: CustomPaginator()},
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]},
    {provide: MAT_DATE_FORMATS, useValue: DATE_FORMATS}
  ]
})
export class CommonComponentsModule {
  constructor(iconsLibrary: NbIconLibraries) {
    iconsLibrary.registerFontPack('fa', {packClass: 'fa', iconClassPrefix: 'fa'});
    iconsLibrary.registerFontPack('far', {packClass: 'far', iconClassPrefix: 'fa'});
  }
}
