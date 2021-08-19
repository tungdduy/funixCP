<#list root.db2s as db2>
java -jar SuperModel.jar --sync --db=${db2.name}
</#list>
<#list root.postgres as pg>
java -jar SuperModel.jar --sync --db=${pg.name}
</#list>
