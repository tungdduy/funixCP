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
    @SuppressWarnings("unused")
    default void update${capName}(Map<String, String> data) {
        ${capName} ${camelName} = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(${capName}.pk(data)));
        ${camelName}.setFieldByName(data);
        this.save(${camelName});
    }

${root.bodySeparator}

<#if root.bodyContent?has_content>
    ${root.bodyContent}
</#if>

${root.bodySeparator}

}
