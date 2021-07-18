import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "${root.pathToFramework}framework/url/url.declare";
import {RouterBuilder} from "${root.pathToFramework}framework/url/router.builder";

const routes = RouterBuilder.build(Url.app.${root.urlKeyChain});

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: Url.app.${root.urlKeyChain}._self.activateProviders
})
export class ${root.capitalizeName}RoutingModule {
  constructor() {}
}
