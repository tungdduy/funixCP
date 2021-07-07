import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "${router.pathToFramework}framework/url/url.declare";
import {RouterBuilder} from "${router.pathToFramework}framework/url/router.builder";

const routes = RouterBuilder.build(Url.app.${router.urlKeyChain});

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ${router.capitalizeName}RoutingModule {
  constructor() {}
}
