import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "${root.pathToFramework}framework/url/url.declare";
<#--
<#list root.components as component>
import {${component.name}} from "./${component.path}/${component.path}.component";
</#list>
import {${root.capName}Component} from "./${root.url}.component";
import {RouterBuilder} from "${root.pathToFramework}framework/url/router.builder";
const routes = [{
  path: '', component: ${root.capName}Component,
  children: [
  <#list root.components as component>
    { path: '${component.path}', component: ${component.name}<#if component.canActivate?has_content>, canActivate: ["${component.canActivate}"]</#if> },
  </#list>
  <#list root.modules as module>
    { path: '${module.path}', loadChildren: () => import('./${module.path}/${module.path}.module').then(m => m['${module.name}']) },
  </#list>
    { path: '', redirectTo: '${root.components[0].path}', pathMatch: 'full' }
  ],
}];
-->
import {RouterBuilder} from "../../../framework/url/router.builder";

const routes = RouterBuilder.build(Url.app.${root.urlKeyChain});
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: Url.app.${root.urlKeyChain}._self.activateProviders
})
export class ${root.capName}RoutingModule {
  constructor() {}
}
