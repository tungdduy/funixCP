${root.separators.newEntityByDefiner.all}
<#list root.entities as entity>
    if (entityDefine.className === '${entity.capName}') {
        entity = new ${entity.capName}();
    <#list entity.primaryKeyEntities as pkEntity>
        entity['${pkEntity.camelName}'] = new ${pkEntity.capName}();
        <#list pkEntity.primaryKeyEntities as pkLvl2>
        entity['${pkEntity.camelName}']['${pkLvl2.camelName}'] = new ${pkLvl2.capName}();
        </#list>
    </#list>
    }
</#list>
${root.separators.aboveMainEntityId.all}
<#list root.entities as entity>
    if (className === '${entity.capName}') return ['${entity.camelName}Id'<#list entity.primaryKeyEntities as pk>, '${pk.camelName}Id'</#list>];
</#list>
${root.separators.belowMainEntityId.all}
<#list root.entities as entity>
    if (className === '${entity.capName}') return [<#list entity.primaryKeyEntities as pk>{name: '${pk.camelName}'}<#if pk_has_next>, </#if></#list>];
</#list>
${root.separators.entityCache.all}
