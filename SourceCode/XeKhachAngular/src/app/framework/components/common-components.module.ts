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
    NbInputModule, NbSelectModule,
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
import { XeNavComponent } from './xe-nav/xe-nav.component';
import { BasicBussSchemeComponent } from './basic-buss-scheme/basic-buss-scheme.component';
import { RoleInfoComponent } from './role-info/role-info.component';
import { IconWrapComponent } from './details/icon-wrap/icon-wrap.component';


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
        NbSelectModule
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
