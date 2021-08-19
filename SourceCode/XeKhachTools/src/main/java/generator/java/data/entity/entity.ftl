package net.timxekhach.operation.data.entity;
${root.separators.import.all}

@Entity @Getter @Setter
public class ${root.entityCapName} extends ${root.entityCapName}_MAPPED {

    <#if root.constructorParams?size gt 0>
    public ${root.entityCapName}() {}
    public ${root.entityCapName}(<#list root.constructorParams as param>${param.simpleClassName} ${param.name}<#if param_has_next>, </#if></#list>) {
        super(<#list root.constructorParams as param>${param.name}<#if param_has_next>, </#if></#list>);
    }
    </#if>
    <#if root.hasProfileImage>
    @Override
    public String getProfileImageUrl() {
        return super.getProfileImageUrl();
    }
    </#if>

${root.separators.body.all}

}

