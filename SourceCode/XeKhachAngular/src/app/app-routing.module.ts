import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

export const routes: Routes = [
  {path: 'admin', loadChildren: () => import('./business/pages/admin/admin.module').then(m => m.AdminModule)},
  {path: 'check-in', loadChildren: () => import('app/business/pages/check-in/check-in.module').then(m => m.CheckInModule)},

  {path: '', redirectTo: 'check-in/login', pathMatch: 'full' },
  {path: '**', redirectTo: 'check-in/login' },
];

const config: ExtraOptions = {
  useHash: false,
};

@NgModule({
  imports: [RouterModule.forRoot(routes, config)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
