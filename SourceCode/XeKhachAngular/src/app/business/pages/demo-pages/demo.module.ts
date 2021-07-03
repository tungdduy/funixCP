import {NgModule} from '@angular/core';
import {NbMenuModule} from '@nebular/theme';

import {ThemeModule} from '../../../@theme/theme.module';
import {DemoComponent} from './demo.component';
import {DashboardModule} from './dashboard/dashboard.module';
import {ECommerceModule} from './e-commerce/e-commerce.module';
import {AdminRoutingModule} from './demo-routing.module';
import {MiscellaneousModule} from './miscellaneous/miscellaneous.module';

@NgModule({
  imports: [
    AdminRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    ECommerceModule,
    MiscellaneousModule,
  ],
  declarations: [
    DemoComponent,
  ],
})
export class DemoModule {
}
