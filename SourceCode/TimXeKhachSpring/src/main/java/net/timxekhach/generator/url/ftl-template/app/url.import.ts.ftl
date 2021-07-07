export const UrlImport = {
<#list urlImports as import>
    "${import.key}": () => ${import.content},
</#list>
};
