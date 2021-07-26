import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {XeInputComponent} from "./xe-input/xe-input.component";
import {XeLabelComponent} from "./xe-label/xe-label.component";
import {XeLinkComponent} from "./xe-link/xe-link.component";
import {XeBtnComponent} from "./xe-btn/xe-btn.component";
import {RouterModule} from "@angular/router";
import { XeFormComponent } from './xe-form/xe-form.component';
import {FormsModule} from "@angular/forms";
import {NbButtonModule, NbFormFieldModule, NbIconLibraries, NbIconModule, NbInputModule} from "@nebular/theme";


@NgModule({
  declarations: [
    XeInputComponent,
    XeLabelComponent,
    XeLinkComponent,
    XeBtnComponent,
    XeFormComponent,
  ],
    exports: [
        XeInputComponent,
        XeLabelComponent,
        XeLinkComponent,
        XeBtnComponent,
        XeFormComponent,
    ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    NbInputModule,
    NbButtonModule,
    NbIconModule,
    NbFormFieldModule
  ]
})
export class CommonComponentsModule {
  constructor(iconsLibrary: NbIconLibraries) {
    iconsLibrary.registerFontPack('fa', { packClass: 'fa', iconClassPrefix: 'fa' });
    iconsLibrary.registerFontPack('far', { packClass: 'far', iconClassPrefix: 'fa' });
  }
}
