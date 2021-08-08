package net.timxekhach.operation.data.repository;
<#assign camelName = root.entityCamelName>
<#assign capName = root.entityCapName>

${root.separators.import.all}

@Repository
public interface ${capName}Repository extends JpaRepository<${capName}, ${capName}_MAPPED.Pk> {

    void deleteBy${capName}Id(Long id);
    void deleteAllBy${capName}IdIn(List<Long> ids);
    ${capName} findBy${capName}Id(Long id);
    <#list root.entitiesCountMe as counter>
    Integer count${root.entityCapName}IdBy${counter.capName}Id(Long ${counter.camelName});
    </#list>
    <#list root.findByPks as pkFinder>
    @SuppressWarnings("unused")
    void deleteBy${pkFinder.name}(<#list pkFinder.params as param>Long ${param}<#if param_has_next>, </#if></#list>);
    @SuppressWarnings("unused")
    List<${capName}> findBy${pkFinder.name}(<#list pkFinder.params as param>Long ${param}<#if param_has_next>, </#if></#list>);
    </#list>

${root.separators.body.all}

}
