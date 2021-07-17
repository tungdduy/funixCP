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

<#list root.primaryKeys as primaryKey>
    @Id
    @Column(nullable = false, updatable = false)
    <#if primaryKey.isAutoIncrement>@GeneratedValue(strategy = GenerationType.AUTO)</#if>
    protected Long ${primaryKey.fieldName};
</#list>

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
    <#list root.primaryKeys as primaryKey>
        protected Long ${primaryKey.fieldName};
    </#list>
    }

    protected ${root.entityClassName}_MAPPED(){}

    protected ${root.entityClassName}_MAPPED(<#list root.constructorParams as param>${param.type} ${param.name}<#if param_has_next>, </#if></#list>) {
    <#list root.constructParams as param>
        this.${param.name} = ${param.name};
    </#list>
    }

<#list root.pkMaps as pkMap>
    @ManyToOne
    @JoinColumns({
    <#list pkMap.joins as join>
        @JoinColumn(
        name = "${join}",
        referencedColumnName = "${join}",
        insertable = false,
        updatable = false)<#if join_has_next>, </#if>
    </#list>
    })
    protected ${pkMap.simpleClassName} ${pkMap.fieldName};
</#list>

<#list root.mapColumns as map>
    <#if map.mappedBy?has_content>
        @OneToMany(
        mappedBy = "${map.mappedBy}",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
        )
    </#if>
    <#if map.joins?size gt 0>
        <#if map.isUnique>@OneToOne<#else>@ManyToOne</#if>
        @JoinColumns({
        <#list map.joins as join>
            @JoinColumn(
            name = "${join.thisName}",
            referencedColumnName = "${join.referencedName}",
            insertable = false,
            updatable = false)<#if join_has_next>, </#if>
        </#list>
        })
    </#if>
    protected ${map.simpleClassName} ${map.fieldName}${map.initialString};
</#list>

<#list root.columns as column>
    <#if column.orderBy?has_content>@OrderBy("${column.orderBy}")</#if>
    <#if column.requireSize>@Size(<#if column.maxSize?has_content>max = ${column.maxSize}, </#if><#if column.minSize?has_content>min = ${column.minSize}</#if></#if>
    <#if column.isNotBlank>@NotBlank</#if>
    <#if column.isEmail>@Email</#if>
    <#if column.min?has_content>@Min(${column.min})</#if>
    <#if column.max?has_content>@Max(${column.max})</#if>
    <#if column.isEmail>@Email</#if>
    <#if column.regex?has_content>@Pattern(regexp = "${column.regex}"</#if>
    protected ${column.simpleClassName} ${column.fieldName}${column.initialString};
</#list>


}