import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatRippleModule} from '@angular/material/core';
import {
  NbActionsModule,
  NbButtonModule,
  NbCheckboxModule,
  NbContextMenuModule,
  NbIconModule,
  NbInputModule,
  NbLayoutModule,
  NbMenuModule,
  NbSearchModule,
  NbSelectModule,
  NbSidebarModule,
  NbThemeModule,
  NbUserModule,
} from '@nebular/theme';
import {NbEvaIconsModule} from '@nebular/eva-icons';
import {NbSecurityModule} from '@nebular/security';

import {FooterComponent, HeaderComponent, SearchInputComponent, TinyMCEComponent, } from './components';
import {CapitalizePipe, NumberWithCommasPipe, PluralPipe, RoundPipe, TimingPipe, } from './pipes';
import {OneColumnLayoutComponent, ThreeColumnsLayoutComponent, TwoColumnsLayoutComponent, } from './layouts';
import {DEFAULT_THEME} from './styles/theme.default';
import {COSMIC_THEME} from './styles/theme.cosmic';
import {CORPORATE_THEME} from './styles/theme.corporate';
import {DARK_THEME} from './styles/theme.dark';
import {MATERIAL_LIGHT_THEME} from './styles/material/theme.material-light';
import {MATERIAL_DARK_THEME} from './styles/material/theme.material-dark';
import {XeInputComponent} from '../framework/components/xe-input/xe-input.component';
import {XeBtnWithCheckboxComponent} from '../framework/components/xe-btn-with-checkbox/xe-btn-with-checkbox.component';
import {XeCenterBtnComponent} from '../framework/components/xe-center-btn/xe-center-btn.component';
import {FormsModule} from "@angular/forms";

const NB_MODULES = [
  NbLayoutModule,
  NbMenuModule,
  NbUserModule,
  NbActionsModule,
  NbSearchModule,
  NbSidebarModule,
  NbContextMenuModule,
  NbSecurityModule,
  NbButtonModule,
  NbSelectModule,
  NbIconModule,
  NbEvaIconsModule,
];
const COMPONENTS = [
  HeaderComponent,
  FooterComponent,
  SearchInputComponent,
  TinyMCEComponent,
  OneColumnLayoutComponent,
  ThreeColumnsLayoutComponent,
  TwoColumnsLayoutComponent,
];
const PIPES = [
  CapitalizePipe,
  PluralPipe,
  RoundPipe,
  TimingPipe,
  NumberWithCommasPipe,
];

@NgModule({
  imports: [
    CommonModule,
    MatRippleModule,
    ...NB_MODULES,
    NbInputModule,
    NbCheckboxModule,
    FormsModule
  ],
  exports: [
    CommonModule,
    MatRippleModule,
    ...PIPES,
    ...COMPONENTS,
    XeInputComponent,
    XeBtnWithCheckboxComponent,
    XeCenterBtnComponent,
  ],
  declarations: [
    ...COMPONENTS,
    ...PIPES,
    XeInputComponent,
    XeBtnWithCheckboxComponent,
    XeCenterBtnComponent,
  ],
})
export class ThemeModule {
  static forRoot(): ModuleWithProviders<ThemeModule> {
    return {
      ngModule: ThemeModule,
      providers: [
        ...NbThemeModule.forRoot(
          {
            name: 'default',
          },
          [DEFAULT_THEME, COSMIC_THEME, CORPORATE_THEME, DARK_THEME, MATERIAL_LIGHT_THEME, MATERIAL_DARK_THEME],
        ).providers,
      ],
    };
  }
}
