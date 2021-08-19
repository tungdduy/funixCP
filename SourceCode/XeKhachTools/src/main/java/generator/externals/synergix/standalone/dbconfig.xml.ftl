<databases>
    <!-- TOMMY DBS -->
    <#list root.db2s as db2>
        <database schema="db2admin" password="${db2.password}" user="${db2.username}" url="jdbc:db2://${db2.host}:${db2.port}/${db2.name}" type="db2" alias="${db2.name}" category="${db2.ctrlOrMain}"/>
    </#list>
    <#list root.postgres as pg>
        <database schema="public" password="${pg.password}" user="${pg.username}" url="jdbc:postgresql://${pg.host}:${pg.port}/${pg.name}" type="postgresql" alias="${pg.name}" category="${pg.ctrlOrMain}"/>
    </#list>
    <!-- TOMMY DBS -->
</databases>
