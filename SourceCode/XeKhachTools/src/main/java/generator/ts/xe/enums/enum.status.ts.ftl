${root.separators.import.all}

<#list root.enums as enum>

export class ${enum.capName} {
  readonly name;
  constructor(private _${enum.camelName}<#list enum.propertyIdentifiers as propId>, private _${propId.camelName}: ${propId.originClassName}</#list>) {
    this.name = _${enum.camelName};
  }
<#if enum.hasBuildSelectMenu?has_content>
  static readonly selectMenu: SelectItem<string>[] = [<#list enum.options as option>new SelectItem(XeLbl('selectItem.${enum.capName}.${option.capName}'), '${option.capName}')<#if option_has_next>, </#if></#list>];
</#if>

  <#list enum.propertyIdentifiers as property>
    <#if property.isString>
      <#if property.propertyChoices?has_content>
      <#list property.propertyChoices as choice>
  get has${property.capName}${choice.valueCapName}() {return this._${property.camelName} === ${choice.valueAsString}; }
  to${property.capName}${choice.valueCapName} = () => this._${property.camelName} = ${choice.valueAsString};
      </#list>
      <#else>
  get has${property.capName}() {return this._${property.camelName} !== null; }
      </#if>
    <#else>
  get has${property.capName}() {return this._${property.camelName} !== null; }
    </#if>
  reset${property.capName} = () => this._${property.camelName} = ${enum.capName}[this._${enum.camelName}].${property.camelName};
  get ${property.camelName}() {return this._${property.camelName}; }
    </#list>
      <#list enum.manualPropertyIdentifiers as prop>
  _${prop.camelName} = (${prop.camelName}) => {
  this.${prop.camelName} = ${prop.camelName};
    return this;
  }
  ${prop.camelName}: ${prop.valueAsString};
      </#list>
    <#list enum.options as option>
  static get ${option.camelName}() {return new ${enum.capName}('${option.camelValue}'<#list option.fullChoiceProperties as property>, ${property.valueAsString}${property.valuePost}</#list>); }
  get is${option.capName}() {return this._${enum.camelName} === '${option.camelName}'; }
  <#if option.isUnique?has_content>
  get has${option.capName}() {return this._${enum.camelName} === '${option.camelName}'; }
  </#if>
  </#list>

}

</#list>
