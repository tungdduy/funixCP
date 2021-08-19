<?xml version="1.0" encoding="UTF-8"?>
<java version="1.8.0_291" class="java.beans.XMLDecoder">
    <object class="java.util.ArrayList">

        <#list root.profileConfigs?keys as key>
            <#list root.profileConfigs[key] as config>
                <void method="add">
                    <object class="workbench.db.ConnectionProfile">
                        <void property="driverName">
                            <string>${config.driverName}</string>
                        </void>
                        <void property="driverclass">
                            <string>${config.driverClass}</string>
                        </void>
                        <void property="group">
                            <string>${key}</string>
                        </void>
                        <void property="name">
                            <string>${config.name}</string>
                        </void>
                        <void property="password">
                            <string>${config.password}</string>
                        </void>
                        <void property="storeExplorerSchema">
                            <boolean>true</boolean>
                        </void>
                        <void property="url">
                            <string>${config.urlPrefix}${config.host}:${config.port}/${config.name}</string>
                        </void>
                        <void property="useSeparateConnectionPerTab">
                            <boolean>true</boolean>
                        </void>
                        <void property="username">
                            <string>${config.username}</string>
                        </void>
                    </object>
                </void>
            </#list>
        </#list>

    </object>
</java>
