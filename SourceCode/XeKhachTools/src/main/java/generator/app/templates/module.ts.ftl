import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {${root.capitalizeName}Component} from './${root.url}.component';
import {${root.capitalizeName}RoutingModule} from "./${root.url}-routing.module";
<#list root.children as child>
  <#lt>import {${child.componentName}} from "./${child.url}/${child.url}.component";
</#list>

@NgModule({
  imports: [
    ${root.capitalizeName}RoutingModule,
    ngFormsModule,
    RouterModule,
  ],
  declarations: [
<#lt>${""?left_pad(4)}${root.capitalizeName}Component,
<#list root.children as child>
    <#lt>${""?left_pad(4)}${child.componentName},
</#list>
  ],
  exports: [
    ${root.capitalizeName}Component
  ]
})
export class ${root.capitalizeName}Module { }
