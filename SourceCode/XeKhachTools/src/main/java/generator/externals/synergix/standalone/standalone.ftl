<#assign dollaSign = "$">
<?xml version='1.0' encoding='UTF-8'?>
<server xmlns="urn:jboss:domain:9.0">
    <extensions>
        <extension module="org.jboss.as.clustering.infinispan"/>
        <extension module="org.jboss.as.connector"/>
        <extension module="org.jboss.as.deployment-scanner"/>
        <extension module="org.jboss.as.ee"/>
        <extension module="org.jboss.as.ejb3"/>
        <extension module="org.jboss.as.jaxrs"/>
        <extension module="org.jboss.as.jmx"/>
        <extension module="org.jboss.as.jpa"/>
        <extension module="org.jboss.as.jsf"/>
        <extension module="org.jboss.as.logging"/>
        <extension module="org.jboss.as.mail"/>
        <extension module="org.jboss.as.naming"/>
        <extension module="org.jboss.as.security"/>
        <extension module="org.jboss.as.transactions"/>
        <extension module="org.jboss.as.weld"/>
        <extension module="org.wildfly.extension.bean-validation"/>
        <extension module="org.wildfly.extension.io"/>
        <extension module="org.wildfly.extension.request-controller"/>
        <extension module="org.wildfly.extension.undertow"/>
    </extensions>
    <system-properties>
        <property name="jboss.as.management.blocking.timeout" value="9000"/>
    </system-properties>
    <management>
        <security-realms>
            <security-realm name="ManagementRealm">
                <authentication>
                    <local default-user="${dollaSign}local" skip-group-loading="true"/>
                    <properties path="mgmt-users.properties" relative-to="jboss.server.config.dir"/>
                </authentication>
                <authorization map-groups-to-roles="false">
                    <properties path="mgmt-groups.properties" relative-to="jboss.server.config.dir"/>
                </authorization>
            </security-realm>
            <security-realm name="ApplicationRealm">
                <authentication>
                    <local default-user="${dollaSign}local" allowed-users="*" skip-group-loading="true"/>
                    <properties path="application-users.properties" relative-to="jboss.server.config.dir"/>
                </authentication>
                <authorization>
                    <properties path="application-roles.properties" relative-to="jboss.server.config.dir"/>
                </authorization>
            </security-realm>
        </security-realms>
        <audit-log>
            <formatters>
                <json-formatter name="json-formatter"/>
            </formatters>
            <handlers>
                <file-handler name="file" formatter="json-formatter" path="audit-log.log" relative-to="jboss.server.data.dir"/>
            </handlers>
            <logger log-boot="true" log-read-only="false" enabled="false">
                <handlers>
                    <handler name="file"/>
                </handlers>
            </logger>
        </audit-log>
        <management-interfaces>
            <http-interface security-realm="ManagementRealm">
                <http-upgrade enabled="true"/>
                <socket-binding http="management-http"/>
            </http-interface>
        </management-interfaces>
        <access-control provider="simple">
            <role-mapping>
                <role name="SuperUser">
                    <include>
                        <user name="${dollaSign}local"/>
                    </include>
                </role>
            </role-mapping>
        </access-control>
    </management>
    <profile>
        <subsystem xmlns="urn:jboss:domain:logging:6.0">
            <console-handler name="CONSOLE">
                <level name="ALL"/>
                <formatter>
                    <named-formatter name="COLOR-PATTERN"/>
                </formatter>
            </console-handler>
            <size-rotating-file-handler name="FILE" autoflush="true">
                <formatter>
                    <named-formatter name="PATTERN"/>
                </formatter>
                <file relative-to="jboss.server.log.dir" path="server.log"/>
                <rotate-size value="100m"/>
                <max-backup-index value="50"/>
                <append value="true"/>
                <suffix value=".yyyy-MM-dd"/>
            </size-rotating-file-handler>
            <size-rotating-file-handler name="RSLogging" autoflush="true">
                <level name="INFO"/>
                <formatter>
                    <named-formatter name="PATTERN"/>
                </formatter>
                <file relative-to="jboss.server.log.dir" path="rs-data.log"/>
                <rotate-size value="10m"/>
                <max-backup-index value="300"/>
                <append value="true"/>
                <suffix value=".yyyy-MM-dd"/>
            </size-rotating-file-handler>
            <logger category="synergix.th6.business.action.jaxrs" use-parent-handlers="false">
                <level name="INFO"/>
                <handlers>
                    <handler name="RSLogging"/>
                </handlers>
            </logger>
            <logger category="org.jboss.as.config">
                <level name="DEBUG"/>
            </logger>
            <logger category="synergix">
                <level name="DEBUG"/>
            </logger>
            <logger category="org.jboss.seam">
                <level name="CONFIG"/>
            </logger>
            <logger category="openjpa.jdbc.SQL">
                <level name="TRACE"/>
            </logger>
            <logger category="org.hibernate.SQL">
                <level name="DEBUG"/>
            </logger>
            <root-logger>
                <level name="INFO"/>
                <handlers>
                    <handler name="CONSOLE"/>
                    <handler name="FILE"/>
                </handlers>
            </root-logger>
            <formatter name="PATTERN">
                <pattern-formatter pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n"/>
            </formatter>
            <formatter name="COLOR-PATTERN">
                <pattern-formatter pattern="%K{level}%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n"/>
            </formatter>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:bean-validation:1.0"/>
        <subsystem xmlns="urn:jboss:domain:datasources:5.0">

            <datasources>

                <!-- START OF TOMMY DATASOURCE-->

                <#list root.db2s as db2>
                    <xa-datasource jndi-name="java:/jdbc/${db2.jndiName}" pool-name="${db2.jndiName}" statistics-enabled="true">
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
                            ${db2.loginName}
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
                    <!-- ========  End of ${db2.profileName}: ${db2.loginName}  ======== -->
                    <!-- ############################################################ -->
                </#list>
                <!-- ############################################################ -->
                <!-- ========================  End of DB2  ====================== -->
                <!-- ############################################################ -->
                <#list root.postgres as pg>
                    <xa-datasource jndi-name="java:/jdbc/${pg.jndiName}" pool-name="${pg.jndiName}" statistics-enabled="false">
                        <xa-datasource-property name="ServerName">
                            ${pg.host}
                        </xa-datasource-property>
                        <xa-datasource-property name="PortNumber">
                            ${pg.port}
                        </xa-datasource-property>
                        <xa-datasource-property name="DatabaseName">
                            ${pg.loginName}
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
                    <!-- ========  End of ${pg.profileName}: ${pg.loginName}  ======== -->
                    <!-- ############################################################ -->
                </#list>
                <!-- ############################################################ -->
                <!-- =====================  End of POSTGRES  ==================== -->
                <!-- ############################################################ -->

                <!-- END OF TOMMY DATASOURCE-->

                <drivers>
                    <driver name="db2jcc" module="com.ibm.db2jcc">
                        <xa-datasource-class>com.ibm.db2.jcc.DB2XADataSource</xa-datasource-class>
                    </driver>
                    <driver name="postgresql" module="org.postgresql">
                        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
                    </driver>
                </drivers>
            </datasources>


        </subsystem>
        <subsystem xmlns="urn:jboss:domain:deployment-scanner:2.0">
            <deployment-scanner path="deployments" relative-to="jboss.server.base.dir" scan-interval="5000" runtime-failure-causes-rollback="${dollaSign}{jboss.deployment.scanner.rollback.on.failure:false}"/>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:ee:4.0"/>
        <subsystem xmlns="urn:jboss:domain:ejb3:5.0">
            <session-bean>
                <stateful default-access-timeout="5000" cache-ref="simple" passivation-disabled-cache-ref="simple"/>
            </session-bean>
            <caches>
                <cache name="simple"/>
            </caches>
            <timer-service thread-pool-name="default" default-data-store="default-file-store">
                <data-stores>
                    <file-data-store name="default-file-store" path="timer-service-data" relative-to="jboss.server.data.dir"/>
                </data-stores>
            </timer-service>
            <thread-pools>
                <thread-pool name="default">
                    <max-threads count="10"/>
                </thread-pool>
            </thread-pools>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:infinispan:7.0">
            <cache-container name="hibernate" default-cache="local-query" module="org.hibernate.infinispan">
                <local-cache name="entity">
                    <transaction mode="NON_XA"/>
                    <object-memory size="10000"/>
                    <expiration max-idle="100000"/>
                </local-cache>
                <local-cache name="local-query">
                    <object-memory size="10000"/>
                    <expiration max-idle="100000"/>
                </local-cache>
                <local-cache name="timestamps"/>
            </cache-container>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:io:3.0">
            <worker name="default"/>
            <worker name="th5workers"/>
            <worker name="th6workers"/>
            <buffer-pool name="default"/>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:jaxrs:1.0"/>
        <subsystem xmlns="urn:jboss:domain:jca:5.0">
            <archive-validation enabled="true" fail-on-error="true" fail-on-warn="false"/>
            <bean-validation enabled="true"/>
            <default-workmanager>
                <short-running-threads>
                    <core-threads count="50"/>
                    <queue-length count="50"/>
                    <max-threads count="50"/>
                    <keepalive-time time="10" unit="seconds"/>
                </short-running-threads>
                <long-running-threads>
                    <core-threads count="50"/>
                    <queue-length count="50"/>
                    <max-threads count="50"/>
                    <keepalive-time time="10" unit="seconds"/>
                </long-running-threads>
            </default-workmanager>
            <cached-connection-manager/>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:jmx:1.3">
            <expose-resolved-model/>
            <expose-expression-model/>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:jpa:1.1">
            <jpa default-datasource="" default-extended-persistence-inheritance="DEEP"/>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:jsf:1.1"/>
        <subsystem xmlns="urn:jboss:domain:mail:3.0"/>
        <subsystem xmlns="urn:jboss:domain:naming:2.0">
            <bindings>
                <simple name="java:/env/jsf/ProjectStage" value="Production"/>
            </bindings>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:request-controller:1.0"/>
        <subsystem xmlns="urn:jboss:domain:security:2.0">
            <security-domains>
                <security-domain name="other" cache-type="default">
                    <authentication>
                        <login-module code="RealmDirect" flag="required">
                            <module-option name="password-stacking" value="useFirstPass"/>
                        </login-module>
                    </authentication>
                </security-domain>
            </security-domains>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:transactions:5.0">
            <core-environment>
                <process-id>
                    <uuid/>
                </process-id>
            </core-environment>
            <recovery-environment socket-binding="txn-recovery-environment" status-socket-binding="txn-status-manager"/>
            <coordinator-environment default-timeout="3000"/>
            <object-store relative-to="jboss.server.data.dir"/>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:undertow:8.0">
            <buffer-cache name="default"/>
            <server name="default-server">
                <http-listener name="default" socket-binding="http" max-post-size="52428800" max-parameters="10000" redirect-socket="https"/>
                <host name="default-host" alias="localhost">
                    <access-log pattern="%h %l %u %t %r %s %b"/>
                    <filter-ref name="server-header"/>
                    <filter-ref name="x-powered-by-header"/>
                    <filter-ref name="connection-close"/>
                </host>
            </server>
            <servlet-container name="default">
                <jsp-config/>
                <websockets/>
            </servlet-container>
            <filters>
                <response-header name="server-header" header-name="Server" header-value="WildFly/9"/>
                <response-header name="x-powered-by-header" header-name="X-Powered-By" header-value="Undertow/1"/>
                <response-header name="connection-close" header-name="Connection" header-value="close"/>
            </filters>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:weld:4.0"/>
    </profile>
    <interfaces>
        <interface name="management">
            <inet-address value="${dollaSign}{jboss.bind.address.management:127.0.0.1}"/>
        </interface>
        <interface name="public">
            <inet-address value="0.0.0.0"/>
        </interface>
    </interfaces>
    <socket-binding-group name="standard-sockets" default-interface="public" port-offset="${dollaSign}{jboss.socket.binding.port-offset:4}">
        <socket-binding name="management-http" interface="management" port="${dollaSign}{jboss.management.http.port:9991}"/>
        <socket-binding name="management-https" interface="management" port="${dollaSign}{jboss.management.https.port:9993}"/>
        <socket-binding name="http" port="${dollaSign}{jboss.http.port:8080}"/>
        <socket-binding name="https" port="${dollaSign}{jboss.https.port:8443}"/>
        <socket-binding name="txn-recovery-environment" port="8712"/>
        <socket-binding name="txn-status-manager" port="8713"/>
    </socket-binding-group>
</server>

