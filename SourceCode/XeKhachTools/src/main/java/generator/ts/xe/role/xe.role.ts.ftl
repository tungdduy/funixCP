export enum Role {
<#list root.roles as role >
  ${role.name()} = "${role.name()}",
</#list>
}

export class XeRole {
<#list root.roles as role >
  static ${role.name()} = {
  ${""?left_pad(2)}roles: [<#list role.roleList as r>Role.${r.name()}<#if r?has_next>, </#if></#list>],
  };
</#list>
}


