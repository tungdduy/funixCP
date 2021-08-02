package net.timxekhach.operation.data.mapped;

<#compress>
<#list root.imports as import>
import ${import};
</#list>

<#list root.staticImports as import>
import static ${import};
</#list>
</#compress>


@MappedSuperclass @Getter @Setter
@IdClass(${root.entityClassName}_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class ${root.entityClassName}_MAPPED extends XeEntity {

<#list root.primaryKeys as primaryKey>
    @Id
    @Column(nullable = false, updatable = false)
    <#if primaryKey.isAutoIncrement()>
    @GeneratedValue(strategy = GenerationType.AUTO)
    </#if>
    @Setter(AccessLevel.PRIVATE) //id join
    protected Long ${primaryKey.fieldName};

    <#if primaryKey.isAutoIncrement()>
    protected Long getIncrementId() {
        return this.${primaryKey.fieldName};
    }
    </#if>

</#list>
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
    <#list root.primaryKeys as primaryKey>
        protected Long ${primaryKey.fieldName};
    </#list>
    }

    public static Pk pk(Map<String, String> data) {
        try {
            <#list root.primaryKeys as pk>
            Long ${pk.fieldName}Long = Long.parseLong(data.get("${pk.fieldName}"));
            </#list>
            if(NumberUtils.min(new long[]{<#list root.primaryKeys as pk>${pk.fieldName}Long<#if pk_has_next>, </#if></#list>}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new ${root.entityClassName}_MAPPED.Pk(<#list root.primaryKeys as pk>${pk.fieldName}Long<#if pk_has_next>, </#if></#list>);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new ${root.entityClassName}_MAPPED.Pk(<#list root.primaryKeys as pk>0L<#if pk_has_next>, </#if></#list>);
    }

    <#if root.constructorParams?size gt 0>
    protected ${root.entityClassName}_MAPPED(){}
    protected ${root.entityClassName}_MAPPED(<#list root.constructorParams as param>${param.simpleClassName} ${param.name}<#if param_has_next>, </#if></#list>) {
    <#list root.constructorParams as param>
        this.set${param.simpleClassName}(${param.name});
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
    @JsonIgnore
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
    @OneTo<#if map.isUnique>One<#else>Many</#if>(
        mappedBy = "${map.mappedBy}",
        cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    <#if map.isUnique>
    protected ${map.mapTo.simpleClassName} ${map.fieldName};
    <#else>
    protected List<${map.mapTo.simpleClassName}> ${map.fieldName} = new ArrayList<>();
    </#if>
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
    @JsonIgnore
    protected ${map.mapTo.simpleClassName} ${map.fieldName};

    public void set${map.fieldName?cap_first}(${map.mapTo.simpleClassName} ${map.fieldName}) {
        this.${map.fieldName} = ${map.fieldName};
    <#list map.joins as join>
        this.${join.thisName} = ${map.fieldName}.get${join.referencedName?cap_first}();
    </#list>
    }
    </#if>
    <#if map.hasCountField>
    protected Integer total${map.fieldCapName};
    public Integer getTotal${map.fieldCapName}() {
        if (this.total${map.fieldCapName} == null) {
           this.updateTotal${map.fieldCapName}(); 
        }
        return this.total${map.fieldCapName};
    }
    public void updateTotal${map.fieldCapName}() {
        this.total${map.fieldCapName} = this.${map.fieldName}.size();
    } 
    </#if>

</#list>

    
<#list root.joinIdColumns as idColumn>
    <#if idColumn.isUnique>
    @Column(unique = true)
    </#if>
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
    <#if column.isEnumerated>
    @Enumerated(EnumType.STRING)
    </#if>
    <#if column.isUnique>
    @Column(unique = true)
    </#if>
    <#if column.jsonIgnore>
    @JsonIgnore
    </#if>
    protected ${column.simpleClassName} ${column.fieldName}${column.initialString};

</#list>

<#assign countOnMoves = root.countOnInsertDeletes>
<#if countOnMoves?size gt 0>
    boolean isPersisted;
    @PrePersist
    public void prePersist() {
        if (!this.isPersisted) {
            <#list countOnMoves as countOnMove>
                this.${countOnMove.entityCamelName}.${fieldCamelName}++;
            </#list>
            this.isPersisted = true;
        }
    }
    boolean isRemoved;
    @PreRemove
    public void preRemove() {
        if (!this.isRemoved) {
            <#list countOnMoves as countOnMove>
                this.${countOnMove.entityCamelName}.${fieldCamelName}--;
            </#list>
            this.isRemoved = true;
        }
    }
</#if>
    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
        <#list root.fieldsAbleAssignByString as column>
            if (fieldName.equals("${column.fieldName}")) {
            <#if column.simpleClassName == 'Date'>
                try {
                this.${column.fieldName} = DateUtils.parseDate(value);
                } catch (Exception e) {
                ErrorCode.INVALID_TIME_FORMAT.throwNow(fieldName);
                }
            <#else>
                this.${column.fieldName} = ${column.simpleClassName}.valueOf(value);
            </#if>
                continue;
            }
        </#list>
        <#list root.primaryKeys as pk>
            if (fieldName.equals("${pk.fieldName}")) {
                this.${pk.fieldName} = Long.valueOf(value);
                <#if pk_has_next>
                    continue;
                </#if>
            }
        </#list>
        }
    }



}
