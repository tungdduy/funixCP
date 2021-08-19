package synergix.th6.business.util.persistence;

public class JndiDeclare {
    public static String[] db2s = {<#list root.db2Profiles as db2>"${db2}"<#if db2_has_next>, </#if></#list>};
    public static String[] postgres = {<#list root.postgresProfiles as pg>"${pg}"<#if pg_has_next>, </#if></#list>};
}
