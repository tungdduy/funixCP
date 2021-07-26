package net.timxekhach.operation.data.entity;
${root.importSeparator}
<#compress>
<#if root.importContent?has_content>
${root.importContent}
<#else>
</#if>
</#compress>

${root.importSeparator}

@Entity @Getter @Setter
public class ${root.entityClassName} extends ${root.entityClassName}_MAPPED {

    <#if root.constructorParams?size gt 0>
    public ${root.entityClassName}() {}
    public ${root.entityClassName}(<#list root.constructorParams as param>${param.simpleClassName} ${param.name}<#if param_has_next>, </#if></#list>) {
        super(<#list root.constructorParams as param>${param.name}<#if param_has_next>, </#if></#list>);
    }
    </#if>
${root.bodySeparator}

<#if root.bodyContent?has_content>
${root.bodyContent}
</#if>

${root.bodySeparator}


}

