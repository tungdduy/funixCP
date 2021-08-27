${root.separators.top.all}
<#list root.allModels as model>
    ${model.entityCapName}: {
        capName: '${model.entityCapName}',
        camelName: '${model.entityCamelName}',
        pkMetas: () => [<#list model.entity.primaryKeyEntities as pk>EntityUtil.metas.${pk.capName}<#if pk_has_next>, </#if></#list>],
        mainIdName: '${model.entity.camelName}Id',
    } as ClassMeta<#if model_has_next>,</#if>
</#list>
  };

  static declaredMapFields = {
<#list root.allModels as model>
    ${model.entityCapName}: {
<#list model.entity.primaryKeyEntities as pk>
      ${pk.camelName}: EntityUtil.metas.${pk.capName},
</#list>
    <#list model.mapColumns as map>
      ${map.fieldName}: EntityUtil.metas.${map.mapTo.simpleClassName}<#if map_has_next>,</#if>
    </#list>
    }<#if model_has_next>,</#if>
</#list>
  };
  static getMeta(name: string) {
<#list root.allModels as model>
    if (StringUtil.equalsIgnoreCase(name, '${model.entityCapName}')) return this.metas.${model.entityCapName};
</#list>
  }
${root.separators.bottom.all}
