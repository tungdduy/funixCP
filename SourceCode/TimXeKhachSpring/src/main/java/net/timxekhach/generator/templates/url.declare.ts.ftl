${root.contentBeforeImport}
<#lt>${root.IMPORT_SEPARATOR}
<#assign count=4 step=2>
    <#macro tree urls>
        <#list urls as url>
            <#lt>${""?left_pad(count)}${url.key}: <#if url.children?size == 0>${url.config}<#else>{
            <#lt>${""?left_pad(count+step)}__self: ${url.config},
            <#assign count = count+step>
            <#lt><@tree url.children />
            <#assign count = count-step>
            <#lt>${""?left_pad(count)}}</#if>,
        </#list>
    </#macro>
    <@tree root.apiUrls />
  },
  app: {
<#assign count=4>
    <#macro tree urls>
        <#list urls as url>
            <#lt>${""?left_pad(count)}${url.key}: <#if url.children?size == 0>${url.config}<#else>{
            <#lt>${""?left_pad(count+step)}__self: ${url.config},
            <#assign count = count+step>
            <#lt><@tree url.children />
            <#assign count = count-step>
            <#lt>${""?left_pad(count)}}</#if>,
        </#list>
    </#macro>
    <@tree root.appUrls />
  }
};


