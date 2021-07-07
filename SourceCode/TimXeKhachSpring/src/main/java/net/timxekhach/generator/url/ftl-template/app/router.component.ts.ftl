import {Component} from '@angular/core';
import {Url} from "${router.pathToFramework}framework/url/url.declare";
import {XeLabel} from "${router.pathToI18n}i18n";

@Component({
  selector: 'xe-${router.url}',
  styleUrls: ['./${router.url}.component.scss'],
  templateUrl: './${router.url}.component.html'
})
export class ${router.capitalizeName}Component {
<#list router.children as child>
    <#lt>${""?left_pad(2)}${child.key} = Url.app.${child.keyChain}.__short;
</#list>
  label = XeLabel;
  constructor() {}
}

