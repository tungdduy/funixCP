${root.separators.tsImport.all}

${root.separators.underImport.all}

export class ${root.entityCapName} extends XeEntity {
    static meta = EntityUtil.metas.${root.entityCapName};
    static mapFields = EntityUtil.mapFields['${root.entityCapName}'];
<#list root.primaryKeys as primaryKey>
    ${primaryKey.fieldName}: number;
</#list>
<#list root.pkMaps as pkMap>
    ${pkMap.fieldName}: ${pkMap.simpleClassName};
</#list>
<#list root.mapColumns as map>
<#if map.mappedBy?has_content>
    <#if map.isUnique>
    ${map.fieldName}: ${map.mapTo.simpleClassName};
    <#else>
    ${map.fieldName}: ${map.mapTo.simpleClassName}[];
    </#if>
<#elseif map.joins?size gt 0>
    ${map.fieldName}: ${map.mapTo.simpleClassName} ;
</#if>
</#list>
<#list root.countMethods as counter>
    ${counter.fieldCapName?uncap_first}: number;
</#list>
<#list root.joinIdColumns as idColumn>
    ${idColumn.fieldName}: number;
</#list>
<#list root.columns as column>
    ${column.fieldName}${column.angularClassName};
</#list>
<#if root.entity.hasProfileImage()>
    profileImageUrl = this.initProfileImage();
</#if>
${root.separators.body.all}

  static entityIdentifier = (${root.entityCamelName}: ${root.entityCapName}): EntityIdentifier<${root.entityCapName}> => ({
    entity: ${root.entityCamelName},
    clazz: ${root.entityCapName},
    idFields: [
      {name: "${root.entityCamelName}Id"},
    <#list root.pkIdChains as pk>
      {name: "${pk?replace("?.", ".")}"}<#if pk_has_next>,</#if>
    </#list>
    ]
  })

  static new(option = {}) {
<#if root.entity.primaryKeyEntities?size gt 0>
    const ${root.entityCamelName} = new ${root.entityCapName}();
    <#assign nameChain = root.entityCamelName>
    <#assign count = 0>
    <#macro tree e0>
        <#if count gt 0>
            <#assign nameChain += "." + e0.camelName>
        </#if>
        <#assign count = count + 1>
        <#list e0.primaryKeyEntities as e1>
    ${nameChain}.${e1.camelName} = new ${e1.capName}();
            <@tree e1 />
        </#list>
        <#assign nameChain = nameChain?keep_before_last(".")>
    </#macro>
    <@tree root.entity />
    EntityUtil.assignEntity(option, ${root.entityCamelName});
    return ${root.entityCamelName};
<#else>
    return new ${root.entityCapName}();
</#if>
  }

  static tableData = (option: XeTableData<${root.entityCapName}> = {}, ${root.entityCamelName}: ${root.entityCapName} = ${root.entityCapName}.new()): XeTableData<${root.entityCapName}> => {
    const table = ${root.entityCapName}._${root.entityCamelName}Table(${root.entityCamelName});
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _${root.entityCamelName}Table = (${root.entityCamelName}: ${root.entityCapName}): XeTableData<${root.entityCapName}> => {
${root.separators.entityTable.all}
  }
}

