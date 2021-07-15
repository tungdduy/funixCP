package net.timxekhach.operation.data.entity;
${root.importSeparator}
<#if root.importContent?has_content>
${root.importContent}
<#else>
    import lombok.Getter;
    import lombok.Setter;
    import net.timxekhach.operation.data.mapped.${root.entityClassName}_MAPPED;

    import javax.persistence.Entity;
</#if>
${root.importSeparator}

@Entity @Getter @Setter
public class ${root.entityClassName} extends ${root.entityClassName}_MAPPED {

${root.bodySeparator}
${root.bodyContent}
${root.bodySeparator}

}

