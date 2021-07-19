import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {Url} from "${root.pathToFramework}framework/url/url.declare";
import {RouterBuilder} from "${root.pathToFramework}framework/url/router.builder";
<#list root.components as component>
import {${component.name}} from "./${component.path}/${component.path}.component";
</#list>

const routes = [{
  path: '', component: ${root.capitalizeName}Component,
  children: [
  <#list root.components as component>
    {path: '${component.path}', component: ${component.name} <#if component.canActivate?has_content>, canActivate: ["${component.canActivate}"]</#if>
  </#list>
  <#list root.modules as module>
    {path: '${module.path}', loadChildren: () => import('./${module.path}/${module.path}.module').then(m => m['${module.name}'])},
  </#list>
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: Url.app.${root.urlKeyChain}._self.activateProviders
})
export class ${root.capitalizeName}RoutingModule {
  constructor() {}
}
