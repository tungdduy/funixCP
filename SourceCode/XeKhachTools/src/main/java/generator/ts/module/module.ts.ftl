<#list root.children as child>
<#lt>import {${child.componentName}} from "./${child.url}/${child.url}.component";
</#list>
import {${root.capitalizeName}Component} from './${root.url}.component';
${root.headerImportSeparator}
<#if root.headerImportContent?has_content>
${root.headerImportContent}
<#else>
import {NgModule} from '@angular/core';
import {FormsModule as ngFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";

</#if>

${root.headerImportSeparator}

@NgModule({
${root.moduleImportSeparator}
<#if root.moduleImportContent?has_content>
${root.moduleImportContent}
<#else>
  imports: [
    ${root.capitalizeName}RoutingModule,
    ngFormsModule,
    RouterModule,
  ],
</#if>

${root.moduleImportSeparator}
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
