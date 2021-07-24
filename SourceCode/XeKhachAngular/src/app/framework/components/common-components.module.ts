import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {XeInputComponent} from "./xe-input/xe-input.component";
import {XeLabelComponent} from "./xe-label/xe-label.component";
import {XeLinkComponent} from "./xe-link/xe-link.component";
import {XeCenterBtnComponent} from "./xe-center-btn/xe-center-btn.component";
import {RouterModule} from "@angular/router";
import { XeFormComponent } from './xe-form/xe-form.component';
import {FormsModule} from "@angular/forms";
import {NbButtonModule, NbInputModule} from "@nebular/theme";



@NgModule({
  declarations: [
    XeInputComponent,
    XeLabelComponent,
    XeLinkComponent,
    XeCenterBtnComponent,
    XeFormComponent,
  ],
  exports: [
    XeInputComponent,
    XeLabelComponent,
    XeLinkComponent,
    XeCenterBtnComponent,
    XeFormComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    NbInputModule,
    NbButtonModule
  ]
})
export class CommonComponentsModule { }
