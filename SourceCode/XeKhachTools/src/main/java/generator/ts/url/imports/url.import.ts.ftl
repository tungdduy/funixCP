export const UrlImport = {
<#list root.urlImports as import>
    // @ts-ignore
    "${import.key}": () => ${import.value},
</#list>
};
