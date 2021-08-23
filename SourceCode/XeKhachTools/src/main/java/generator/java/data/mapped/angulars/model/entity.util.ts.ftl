${root.separators.top.all}
<#list root.allModels as model>
    ${model.entityCapName}: {
        capName: '${model.entityCapName}',
        camelName: '${model.entityCamelName}',
        pkMetas: () => [<#list model.entity.primaryKeyEntities as pk>EntityUtil.metas.${pk.capName}<#if pk_has_next>, </#if></#list>],
        mainIdName: '${model.entity.camelName}Id',
        mapFields: () => [<#list model.mapColumns as map>{name: '${map.fieldName}', meta: EntityUtil.metas.${map.mapTo.simpleClassName}}<#if map_has_next>, </#if></#list>],
    } as ClassMeta<#if model_has_next>,</#if>
</#list>
  };

  static getClassMeta(name: string) {
<#list root.allModels as model>
    if (StringUtil.equalsIgnoreCase(name, '${model.entityCapName}')) return this.metas.${model.entityCapName};
</#list>
  }
${root.separators.bottom.all}
