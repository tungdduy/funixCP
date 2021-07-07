import {Authority} from "./auth.enum";

export class XeRole {
<#list roles as role >
  static ${role.name()} = {
  ${""?left_pad(2)}roles: [<#list role.roles as r>XeRole.${r.name()}<#if r?has_next>, </#if></#list>],
  ${""?left_pad(2)}authorities: [<#list role.auths as a>Authority.${a.name()}<#if a?has_next>, </#if></#list>]
  };
</#list>
}


