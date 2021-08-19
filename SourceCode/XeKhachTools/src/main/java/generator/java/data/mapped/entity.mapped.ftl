package net.timxekhach.operation.data.mapped;

${root.separators.import.all}


@MappedSuperclass @Getter @Setter
@IdClass(${root.entityCapName}_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class ${root.entityCapName}_MAPPED extends XeEntity {

<#list root.primaryKeys as primaryKey>
    @Id
    @Column(nullable = false, updatable = false)
    <#if primaryKey.isAutoIncrement()>
    @GeneratedValue(strategy = GenerationType.AUTO)
    </#if>
    @Setter(AccessLevel.PRIVATE)
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
            return new ${root.entityCapName}_MAPPED.Pk(<#list root.primaryKeys as pk>${pk.fieldName}Long<#if pk_has_next>, </#if></#list>);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new ${root.entityCapName}_MAPPED.Pk(<#list root.primaryKeys as pk>0L<#if pk_has_next>, </#if></#list>);
    }

    <#if root.constructorParams?size gt 0>
    protected ${root.entityCapName}_MAPPED(){}
    protected ${root.entityCapName}_MAPPED(<#list root.constructorParams as param>${param.simpleClassName} ${param.name}<#if param_has_next>, </#if></#list>) {
    <#list root.constructorParams as param>
        this.set${param.simpleClassName}(${param.name});
    </#list>
    }
    </#if>
//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#-- +++++++++++++++++++NEWSECION+++++++++++++++++++++++ -->
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#if root.pkMaps?size gt 0>
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
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "${pkMap.fieldName}Id")
    protected ${pkMap.simpleClassName} ${pkMap.fieldName};

    public ${pkMap.simpleClassName} get${pkMap.simpleClassName}(){
        if (this.${pkMap.fieldName} == null) {
            this.${pkMap.fieldName} = CommonUpdateService.get${pkMap.simpleClassName}Repository().findBy${pkMap.simpleClassName}Id(this.${pkMap.fieldName}Id);
        }
        return this.${pkMap.fieldName};
    }

    public void set${pkMap.fieldName?cap_first}(${pkMap.simpleClassName} ${pkMap.fieldName}) {
        this.${pkMap.fieldName} = ${pkMap.fieldName};
        <#list pkMap.joins as join>
        this.${join} = ${pkMap.fieldName}.get${join?cap_first}();
        </#list>
    }
</#list>
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//
</#if>
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#-- +++++++++++++++++++NEWSECION+++++++++++++++++++++++ -->
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#if root.mapColumns?size gt 0>
<#list root.mapColumns as map>
    <#if map.mappedBy?has_content>
    @OneTo<#if map.isUnique>One<#else>Many</#if>(
        mappedBy = "${map.mappedBy}",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    <#if map.isUnique>
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "${map.mapTo.entity.camelName}Id")
    protected ${map.mapTo.simpleClassName} ${map.fieldName};
    <#-- +++++++++++++++++++...Field...+++++++++++++++++++++++ -->
    <#else>
    <#if map.orderBy?has_content>
    @OrderBy("${map.orderBy}")
    </#if>
    protected List<${map.mapTo.simpleClassName}> ${map.fieldName} = new ArrayList<>();
    <#-- +++++++++++++++++++...Field...+++++++++++++++++++++++ -->
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
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "${map.mapTo.entity.camelName}Id")
    protected ${map.mapTo.simpleClassName} ${map.fieldName};
    <#-- +++++++++++++++++++...Field...+++++++++++++++++++++++ -->

    public void set${map.fieldCapName}(${map.mapTo.simpleClassName} ${map.fieldName}) {
        this.${map.fieldName} = ${map.fieldName};
    <#list map.joins as join>
        this.${join.thisName} = ${map.fieldName}.get${join.referencedName?cap_first}();
    </#list>
    }

    </#if>
</#list>
//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//
</#if>
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#-- +++++++++++++++++++NEWSECION+++++++++++++++++++++++ -->
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#if root.countMethods?size gt 0>
<#list root.countMethods as counter>
    public Integer get${counter.fieldCapName}() {
        return CommonUpdateService.get${counter.countCapName}Repository().count${counter.countCapName}IdBy${counter.thisCapName}Id(this.${root.entityCamelName}Id);
    }
</#list>
//=====================================================================//
//==================== END of MAP COUNT ENTITIES ======================//
//====================================================================//
</#if>
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#-- +++++++++++++++++++NEWSECION+++++++++++++++++++++++ -->
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->

<#if root.joinIdColumns?size gt 0>
<#list root.joinIdColumns as idColumn>
    <#if idColumn.isUnique>
    @Column(unique = true)
    </#if>
    @Setter(AccessLevel.PRIVATE)
    protected Long ${idColumn.fieldName};
</#list>
//====================================================================//
//==================== END of JOIN ID COLUMNS ========================//
//====================================================================//
</#if>
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#-- +++++++++++++++++++NEWSECION+++++++++++++++++++++++ -->
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#if root.columns?size gt 0>
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
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//
</#if>

<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<#-- +++++++++++++++++++NEWSECION+++++++++++++++++++++++ -->
<#-- +++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
        <#list root.fieldsAbleAssignByString as column>
            if (fieldName.equals("${column.fieldName}")) {
            <#if column.parseExpression?has_content>
                this.${column.fieldName} = ${column.parseExpression};
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
