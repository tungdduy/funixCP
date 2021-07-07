export enum Authority {
<#list auths as auth >
    ${auth.name()} = "${auth.name()}"<#if auth?has_next>,</#if>
</#list>
}

