${root.contentBeforeAuthorization}<#compress>
</#compress>${root.urlAuthorizationSplitter}
<#list root.authorities as auth>
                .antMatchers("${auth.url}")${auth.authorities}
</#list>
            ${root.urlAuthorizationSplitter}<#compress>
</#compress>${root.contentAfterAuthorization}