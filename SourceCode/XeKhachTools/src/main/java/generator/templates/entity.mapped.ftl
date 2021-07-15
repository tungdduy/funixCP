package net.timxekhach.operation.data.mapped;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;

<#list root.imports as import>
import ${import};
</#list>

<#list root.staticImports as import>
import static ${import};
</#list>

@MappedSuperclass @Getter @Setter
@IdClass(${root.entityClassName}_MAPPED.Pk.class)
public abstract class ${root.entityClassName}_MAPPED extends XeEntity {

<#list root.primaryKeys as pk>
    @Id
    @Column(nullable = false, updatable = false)
    <#if pk.autoIncrement>@GeneratedValue(strategy = GenerationType.AUTO)</#if>
    protected ${pk.className} ${pk.name};
</#list>

<#list root.columns as column>
    <#if column.mappedBy?has_content>
    @OneToMany(
        mappedBy = "${column.mappedBy}",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    </#if>
    <#if column.joins?size gt 0>
    @ManyToOne
    @JoinColumns({
        <#list column.joins as join>
        @JoinColumn(
        name = "${join.thisName}",
        referencedColumnName = "${join.referencedName}",
        insertable = false,
        updatable = false)<#if join_has_next>, </#if>
        </#list>
    })
    </#if>
    <#if column.orderBy?has_content>@OrderBy("${column.orderBy}")</#if>
    <#if column.hasSize>@Size(<#if column.maxSize?has_content>max = ${column.maxSize}, </#if><#if column.minSize?has_content>min = ${column.minSize}</#if></#if>
    <#if column.isNotBlank>@NotBlank</#if>
    <#if column.isEmail>@Email</#if>
    <#if column.regex?has_content>@Pattern(regexp = "${column.regex}"</#if>
    protected ${column.classname} ${column.name}${column.initialString};
</#list>

<#list root.constructors as con>
    protected ${root.entityClassName}_MAPPED(<#list con.params as param>${param.type} ${param.name}</#list>) {
    <#list con.params as param>
        this.${param.name} = ${param.name};
    </#list>
    }
</#list>

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
    <#list root.primaryKeys as pk>
        protected ${pk.className} ${pk.name};
    </#list>
    }

}