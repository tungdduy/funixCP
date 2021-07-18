import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "../../../framework/url/url.declare";
import {RouterBuilder} from "../../../framework/url/router.builder";

const routes = RouterBuilder.build(Url.app.ADMIN);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: Url.app.ADMIN._self.activateProviders
})
export class AdminRoutingModule {
  constructor() {}
}
