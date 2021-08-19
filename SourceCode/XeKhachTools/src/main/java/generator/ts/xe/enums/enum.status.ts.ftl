${root.separators.import.all}

<#list root.enums as enum>

export class ${enum.capName} {
  constructor(private _${enum.camelName}<#list enum.propertyIdentifiers as propId>, private _${propId.camelName}: ${propId.originClassName}</#list>) {}
  <#list enum.propertyIdentifiers as property>
  <#if property.isString>
    <#list property.propertyChoices as choice>
  get is${property.capName}${choice.valueCapName}() {return this._${property.camelName} === ${choice.valueAsString}; }
    </#list>
  <#else>
  get ${property.camelName}() {return this._${property.camelName}; }
  get has${property.capName}() {return this._${property.camelName} !== null; }
  </#if>
  </#list>

  <#list enum.options as option>
  static readonly ${option.camelName} = new ${enum.capName}('${option.camelName}'<#list option.fullChoiceProperties as property>, ${property.valueAsString}${property.valuePost}</#list>);
  get is${option.capName}() {return this._${enum.camelName} === '${option.camelName}'; }

  </#list>
}

</#list>
