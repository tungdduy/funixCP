${root.separators.import.all}

<#list root.enums as enum>

export class ${enum.capName} {
  readonly name: string;
  constructor(private _${enum.camelName}<#list enum.propertyIdentifiers as propId>, private _${propId.camelName}: ${propId.originClassName}</#list>) {
    this.name = _${enum.camelName};
  }
  <#list enum.propertyIdentifiers as property>
    <#if property.isString>
      <#list property.propertyChoices as choice>
  get has${property.capName}${choice.valueCapName}() {return this._${property.camelName} === ${choice.valueAsString}; }
      </#list>
    <#else>
  get has${property.capName}() {return this._${property.camelName} !== null; }
    </#if>
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
  static readonly ${option.camelName} = new ${enum.capName}('${option.camelValue}'<#list option.fullChoiceProperties as property>, ${property.valueAsString}${property.valuePost}</#list>);
  get is${option.capName}() {return this._${enum.camelName} === '${option.camelName}'; }
  </#list>

}

</#list>
