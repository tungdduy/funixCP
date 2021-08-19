${root.separators.header.all}
<#list root.entities as entity>
    if (StringUtil.equalsIgnoreCase(className, '${entity.capName}')) return ${entity.capName};
</#list>
${root.separators.getClassByClassName.all}
