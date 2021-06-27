import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

export const routes: Routes = [
  {path: 'admin', loadChildren: () => import('./demo-pages/demo.module').then(m => m.DemoModule)},
  {path: 'auth', loadChildren: () => import('./check-in-pages/check-in.module').then(m => m.CheckInModule)},
  {path: 'demo', loadChildren: () => import('./demo-pages/demo.module').then(m => m.DemoModule)},

  {path: '', redirectTo: 'auth', pathMatch: 'full' },
  {path: '**', redirectTo: 'demo' },
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
