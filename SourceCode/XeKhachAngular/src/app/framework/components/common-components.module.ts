import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {XeInputComponent} from "./xe-input/xe-input.component";
import {XeLabelComponent} from "./xe-label/xe-label.component";
import {XeLinkComponent} from "./xe-link/xe-link.component";
import {XeBtnComponent} from "./xe-btn/xe-btn.component";
import {RouterModule} from "@angular/router";
import {XeFormComponent} from './xe-form/xe-form.component';
import {FormsModule} from "@angular/forms";
import {
  NbButtonModule,
  NbCardModule, NbCheckboxModule,
  NbFormFieldModule,
  NbIconLibraries,
  NbIconModule,
  NbInputModule,
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
import { UserTableComponent } from './user-table/user-table.component';


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
    NbCheckboxModule
  ],
  providers: [
    {provide: MatPaginatorIntl, useValue: CustomPaginator()}
  ]
})
export class CommonComponentsModule {
  constructor(iconsLibrary: NbIconLibraries) {
    iconsLibrary.registerFontPack('fa', { packClass: 'fa', iconClassPrefix: 'fa' });
    iconsLibrary.registerFontPack('far', { packClass: 'far', iconClassPrefix: 'fa' });
  }
}
