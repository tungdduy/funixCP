package net.timxekhach.operation.data.repository;
<#assign camelName = root.entityCamelName>
<#assign capName = root.entityClassName>
${root.importSeparator}
<#compress>
<#if root.importContent?has_content>
${root.importContent}
</#if>
</#compress>

${root.importSeparator}

@Repository
public interface ${capName}Repository extends JpaRepository<${capName}, ${capName}_MAPPED.Pk> {

    @Modifying
    @Query("delete from ${capName} e where e.${camelName}Id in ?1")
    void deleteBy${capName}Id(Long... id);
    ${capName} findBy${capName}Id(Long id);

    <#list root.byPkMethods as pkMethod>
    @SuppressWarnings("unused")
    void deleteBy${pkMethod.name}(<#list pkMethod.params as param>Long ${param}<#if param_has_next>, </#if></#list>);
    @SuppressWarnings("unused")
    List<${capName}> findBy${pkMethod.name}(<#list pkMethod.params as param>Long ${param}<#if param_has_next>, </#if></#list>);
    </#list>

${root.bodySeparator}

<#if root.bodyContent?has_content>
    ${root.bodyContent}
</#if>

${root.bodySeparator}

}
