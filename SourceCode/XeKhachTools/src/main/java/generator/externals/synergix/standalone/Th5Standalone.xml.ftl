<?xml version='1.0' encoding='UTF-8'?>

<server xmlns="urn:jboss:domain:9.0">
    ${root.separators.header.all}
    <#list root.db2s as db2>
        <#if db2.isRaw>
            <xa-datasource jndi-name="java:/jdbc/<#if db2.isCtrl>ctrlDataSource<#else>${db2.name}</#if>" pool-name="<#if db2.isCtrl>ctrlDataSource<#else>${db2.name}</#if>" statistics-enabled="true">
                <xa-datasource-property name="DriverType">
                    4
                </xa-datasource-property>
                <xa-datasource-property name="ServerName">
                    ${db2.host}
                </xa-datasource-property>
                <xa-datasource-property name="PortNumber">
                    ${db2.port}
                </xa-datasource-property>
                <xa-datasource-property name="DatabaseName">
                    ${db2.name}
                </xa-datasource-property>
                <driver>db2jcc</driver>
                <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
                <xa-pool>
                    <min-pool-size>0</min-pool-size>
                    <max-pool-size>100</max-pool-size>
                    <prefill>false</prefill>
                    <use-strict-min>false</use-strict-min>
                    <flush-strategy>FailingConnectionOnly</flush-strategy>
                </xa-pool>
                <security>
                    <user-name>${db2.username}</user-name>
                    <password>${db2.password}</password>
                </security>
                <validation>
                    <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.db2.DB2ValidConnectionChecker"/>
                    <validate-on-match>true</validate-on-match>
                    <background-validation>false</background-validation>
                    <stale-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.db2.DB2StaleConnectionChecker"/>
                    <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.db2.DB2ExceptionSorter"/>
                </validation>
                <timeout>
                    <idle-timeout-minutes>60</idle-timeout-minutes>
                    <use-try-lock>60</use-try-lock>
                </timeout>
            </xa-datasource>
            <!-- ========  End of ${db2.profileName}: ${db2.name}  ======== -->
            <!-- ############################################################ -->
        </#if>
    </#list>
    <!-- ############################################################ -->
    <!-- ========================  End of DB2  ====================== -->
    <!-- ############################################################ -->
    <#list root.postgres as pg>
        <#if pg.isRaw>
            <xa-datasource jndi-name="java:/jdbc/<#if pg.isCtrl>ctrlDataSource<#else>${pg.name}</#if>" pool-name="<#if pg.isCtrl>ctrlDataSource<#else>${pg.name}</#if>" statistics-enabled="false">
                <xa-datasource-property name="ServerName">
                    ${pg.host}
                </xa-datasource-property>
                <xa-datasource-property name="PortNumber">
                    ${pg.port}
                </xa-datasource-property>
                <xa-datasource-property name="DatabaseName">
                    ${pg.name}
                </xa-datasource-property>
                <driver>postgresql</driver>
                <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
                <xa-pool>
                    <min-pool-size>0</min-pool-size>
                    <max-pool-size>100</max-pool-size>
                    <prefill>false</prefill>
                    <use-strict-min>false</use-strict-min>
                    <flush-strategy>FailingConnectionOnly</flush-strategy>
                </xa-pool>
                <security>
                    <user-name>${pg.username}</user-name>
                    <password>${pg.password}</password>
                </security>
                <validation>
                    <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
                    <validate-on-match>true</validate-on-match>
                    <background-validation>false</background-validation>
                    <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
                </validation>
                <timeout>
                    <idle-timeout-minutes>60</idle-timeout-minutes>
                    <use-try-lock>60</use-try-lock>
                </timeout>
            </xa-datasource>
            <!-- ========  End of ${pg.profileName}: ${pg.name}  ======== -->
            <!-- ############################################################ -->
        </#if>
    </#list>
    <!-- ############################################################ -->
    <!-- =====================  End of POSTGRES  ==================== -->
    <!-- ############################################################ -->
    ${root.separators.footer.all}
</server>
