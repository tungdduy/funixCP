import {Component} from '@angular/core';
import {Url} from "${root.pathToFramework}framework/url/url.declare";
import {XeLabel} from "${root.pathToI18n}i18n";

@Component({
  selector: 'xe-${root.url}',
  styleUrls: ['./${root.url}.component.scss'],
  templateUrl: './${root.url}.component.html'
})
export class ${root.capitalizeName}Component {
<#list root.children as child>
    <#lt>${""?left_pad(2)}${child.key} = Url.app.${child.keyChain}.short;
</#list>
  label = XeLabel;
  constructor() {}
}

