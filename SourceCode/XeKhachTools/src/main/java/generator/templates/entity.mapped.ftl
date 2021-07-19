package net.timxekhach.operation.data.mapped;

<#compress>
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;

<#list root.imports as import>
import ${import};
</#list>

<#list root.staticImports as import>
import static ${import};
</#list>
</#compress>



@MappedSuperclass @Getter @Setter
@IdClass(${root.entityClassName}_MAPPED.Pk.class)
public abstract class ${root.entityClassName}_MAPPED extends XeEntity {

<#list root.primaryKeys as primaryKey>
    @Id
    @Column(nullable = false, updatable = false)
    <#if primaryKey.isAutoIncrement()>
    @GeneratedValue(strategy = GenerationType.AUTO)
    </#if>
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long ${primaryKey.fieldName};

</#list>
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
    <#list root.primaryKeys as primaryKey>
        protected Long ${primaryKey.fieldName};
    </#list>
    }
    <#if root.constructorParams?size gt 0>
    protected ${root.entityClassName}_MAPPED(){}
    protected ${root.entityClassName}_MAPPED(<#list root.constructorParams as param>${param.simpleClassName} ${param.name}<#if param_has_next>, </#if></#list>) {
    <#list root.constructorParams as param>
        this.${param.name} = ${param.name};
    </#list>
    }
    </#if>
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

    public void set${pkMap.fieldName?cap_first}(${pkMap.simpleClassName} ${pkMap.fieldName}) {
        this.${pkMap.fieldName} = ${pkMap.fieldName};
        <#list pkMap.joins as join>
        this.${join} = ${pkMap.fieldName}.get${join?cap_first}();
        </#list>
    }

</#list>
<#list root.mapColumns as map>
    <#if map.mappedBy?has_content>
    @OneToMany(
        mappedBy = "${map.mappedBy}",
        cascade = {CascadeType.ALL},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    protected List<${map.mapTo.simpleClassName}> ${map.fieldName} = new ArrayList<>();
    <#elseif map.joins?size gt 0>
    <#if map.isUnique>
    @OneToOne
    <#else>
    @ManyToOne
    </#if>
    @JoinColumns({
    <#list map.joins as join>
        @JoinColumn(
        name = "${join.thisName}",
        referencedColumnName = "${join.referencedName}",
        insertable = false,
        updatable = false)<#if join_has_next>, </#if>
    </#list>
    })
    protected ${map.mapTo.simpleClassName} ${map.fieldName};

    public void set${map.fieldName?cap_first}(${map.mapTo.simpleClassName} ${map.fieldName}) {
        this.${map.fieldName} = ${map.fieldName};
    <#list map.joins as join>
        this.${join.thisName} = ${map.fieldName}.get${join.referencedName?cap_first}();
    </#list>
    }
    </#if>

</#list>
<#list root.joinIdColumns as idColumn>
    @Column
    @Setter(AccessLevel.PRIVATE) //map join
    protected Long ${idColumn.fieldName};

</#list>
<#list root.columns as column>
    <#if column.orderBy?has_content>
    @OrderBy("${column.orderBy}")
    </#if>
    <#if column.maxSize?has_content || column.minSize?has_content>
    @Size(<#if column.maxSize?has_content>max = ${column.maxSize}<#if column.minSize?has_content>, </#if></#if><#if column.minSize?has_content>min = ${column.minSize}</#if>)</#if>
    <#if column.isNotNull>
    @NotBlank
    </#if>
    <#if column.isEmail>
    @Email
    </#if>
    <#if column.min?has_content>
    @Min(${column.min})
    </#if>
    <#if column.max?has_content>
    @Max(${column.max})
    </#if>
    <#if column.regex?has_content>
    @Pattern(regexp = "${column.regex}")
    </#if>
    @Column
    protected ${column.simpleClassName} ${column.fieldName}${column.initialString};

</#list>
}
