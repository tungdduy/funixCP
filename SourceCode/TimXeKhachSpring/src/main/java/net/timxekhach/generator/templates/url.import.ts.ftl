export const UrlImport = {
<#list root.urlImports as import>
    "${import.key}": () => ${import.value},
</#list>
};
