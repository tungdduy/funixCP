${root.contentBeforeAuthorization}<#compress>
</#compress>${root.urlAuthorizationSeparator}
<#list root.authorities as auth>
                .antMatchers("${auth.url}")${auth.authorities}
</#list>
            ${root.urlAuthorizationSeparator}<#compress>
</#compress>${root.contentAfterAuthorization}