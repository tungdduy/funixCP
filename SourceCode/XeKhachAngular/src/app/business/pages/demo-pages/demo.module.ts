import {NgModule} from '@angular/core';
import {NbMenuModule} from '@nebular/theme';

import {ThemeModule} from '../../../@theme/theme.module';
import {DemoComponent} from './demo.component';
import {ECommerceModule} from './e-commerce/e-commerce.module';
import {DemoRoutingModule} from './demo-routing.module';
import {MiscellaneousModule} from './miscellaneous/miscellaneous.module';
import {DashboardComponent} from "./dashboard/dashboard.component";

@NgModule({
  imports: [
    DemoRoutingModule,
    ThemeModule,
    NbMenuModule,
    ECommerceModule,
    MiscellaneousModule,
  ],
  declarations: [
    DemoComponent,
    DashboardComponent
  ],
})
export class DemoModule {
}
