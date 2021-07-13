export enum Authority {
<#list root.auths as auth >
    ${auth.name()} = "${auth.name()}"<#if auth?has_next>,</#if>
</#list>
}

