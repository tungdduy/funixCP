import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {${router.capitalizeName}Component} from './${router.url}.component';
import {${router.capitalizeName}RoutingModule} from "./${router.url}-routing.module";
<#list router.children as child>
  <#lt>import {${child.componentName}} from "./${child.url}/${child.url}.component";
</#list>

@NgModule({
  imports: [
    ${router.capitalizeName}RoutingModule,
    ngFormsModule,
    RouterModule,
  ],
  declarations: [
<#lt>${""?left_pad(4)}${router.capitalizeName}Component,
<#list router.children as child>
    <#lt>${""?left_pad(4)}${child.componentName},
</#list>
  ],
  exports: [
    ${router.capitalizeName}Component
  ]
})
export class ${router.capitalizeName}Module { }
