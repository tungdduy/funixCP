import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "../../../framework/url/url.declare";
import {RouterBuilder} from "../../../framework/url/router.builder";


@NgModule({
  imports: [RouterModule.forChild(RouterBuilder.build(Url.app.ADMIN))],
  exports: [RouterModule],
  providers: Url.app.ADMIN._self.activateProviders
})
export class AdminRoutingModule {
  constructor() {}
}
