<#list root.children as child>
<#lt>import {${child.componentName}} from "./${child.url}/${child.url}.component";
</#list>
import {${root.capName}Component} from './${root.url}.component';
${root.separators.headerImport.all}

@NgModule({

${root.separators.moduleImport.all}

  declarations: [
<#lt>${""?left_pad(4)}${root.capName}Component,
<#list root.children as child>
    <#lt>${""?left_pad(4)}${child.componentName},
</#list>
  ],
  exports: [
    ${root.capName}Component
  ]
})
export class ${root.capName}Module { }
