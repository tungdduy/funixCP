import {Authority} from "./auth.enum";

export class XeRole {
<#list root.roles as role >
  static ${role.name()} = {
  ${""?left_pad(2)}roles: [<#list role.roleList as r>XeRole.${r.name()}<#if r?has_next>, </#if></#list>],
  };
</#list>
}


