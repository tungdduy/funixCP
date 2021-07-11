import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "../../../framework/url/url.declare";
import {RouterBuilder} from "../../../framework/url/router.builder";

const routes = RouterBuilder.build(Url.app.CHECK_IN);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: Url.app.CHECK_IN.__self.activateProviders
})
export class CheckInRoutingModule {
  constructor() {}
}
