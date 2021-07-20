package net.timxekhach.operation.data.repository;
${root.importSeparator}
<#compress>
    <#if root.importContent?has_content>
        ${root.importContent}
    <#else>
    import net.timxekhach.operation.data.entity.${root.entityClassName};
    import net.timxekhach.operation.data.mapped.${root.entityClassName}_MAPPED;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    </#if>
</#compress>

${root.importSeparator}

@Repository
public interface ${root.entityClassName}Repository extends JpaRepository<${root.entityClassName}, ${root.entityClassName}_MAPPED.Pk> {

${root.bodySeparator}

<#if root.bodyContent?has_content>
    ${root.bodyContent}
</#if>

${root.bodySeparator}

}
