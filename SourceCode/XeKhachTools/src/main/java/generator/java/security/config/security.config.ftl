${root.separators.beforeAuthorization.all}

<#list root.authorities as auth>
                .antMatchers("${auth.url}")${auth.authorities}
</#list>

${root.separators.afterAuthorization.all}