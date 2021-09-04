package generator.externals.synergix.standalone;

import generator.abstracts.models.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
public class StandaloneModel extends AbstractModel {
    @Getter @Setter
    public static class DbConfig {
        String jndiName, host, port, loginName, username, password, profileName, ctrlOrMain;
        Boolean isRaw, isCtrl;

        public DbConfig(List<DbConfig> dbConfigs) {
            dbConfigs.add(this);
        }

        String urlPrefix, driverClass, driverName;
        private DbConfig profile(Profile profile) {
            this.profileName = profile.profileName;
            this.host = profile.host;
            this.username = profile.username;
            this.password = profile.password;
            this.port = profile.port;
            this.isRaw = profile.isRaw;

            this.urlPrefix = profile.urlPrefix;
            this.driverClass = profile.driverClass;
            this.driverName = profile.driverName;
            return this;
        }
        private DbConfig ctrl(Profile profile) {
            this.jndiName = "ctrlDataSource_" + profile.profileName;
            this.loginName = profile.ctrlName;
            this.ctrlOrMain = "ctrl";
            this.isCtrl = true;
            return this;
        }
        private DbConfig main(Profile profile, MainDb mainDb) {
            this.ctrlOrMain = "main";
            this.isCtrl = false;
            this.jndiName  = mainDb.jndiName + "_" + profile.profileName;
            this.loginName = mainDb.loginName;
            return this;
        }
    }
    @Getter @Setter @AllArgsConstructor
    public static class MainDb {
        String loginName, jndiName;
    }
    private List<DbConfig> db2s = new ArrayList<>();
    private List<DbConfig> postgres = new ArrayList<>();
    private List<String> db2Profiles = new ArrayList<>();
    private List<String> postgresProfiles = new ArrayList<>();
    Map<String, List<DbConfig>> profileConfigs = new HashMap<>();

    public static class Profile {
        String profileName, ctrlName, host, port, username, password;
        boolean isDb2, isRaw;

        String urlPrefix, driverClass, driverName;

        List<MainDb> mains = new ArrayList<>();
        Profile(String name, ProfileEnum type) {
            this.profileName = name;
            switch (type) {
                case DB2:
                    this.isDb2 = true;
                    this.driverName = "IBM DB2";
                    this.driverClass = "com.ibm.db2.jcc.DB2Driver";
                    this.urlPrefix = "jdbc:db2://";
                    break;
                case MARIA:
                    this.isDb2 = false;
                    this.driverName = "MariaDB";
                    this.driverClass = "org.mariadb.jdbc.Driver";
                    this.urlPrefix = "jdbc:mariadb://";
                    break;
                case POSTGRES:
                    this.urlPrefix = "jdbc:postgresql://";
                    this.driverClass = "org.postgresql.Driver";
                    this.driverName = "PostgreSQL";
                    break;

            }
        }

        Profile ctrlName(String name) {
            this.ctrlName = name;
            return this;
        }
        Profile host(String host) {
            this.host = host;
            return this;
        }
        Profile port(String port) {
            this.port = port;
            return this;
        }
        Profile username(String username) {
            this.username = username;
            return this;
        }
        Profile password(String password) {
            this.password = password;
            return this;
        }
        Profile isDb2(boolean isDb2) {
            this.isDb2 = isDb2;
            return this;
        }
        Profile main(String loginName, String jdbcName) {
            this.mains.add(new MainDb(loginName, jdbcName));
            return this;
        }
        Profile mains(String... mains) {
            for (String main : mains) {
                this.mains.add(new MainDb(main, main));
            }
            return this;
        }

        Profile raw() {
            this.isRaw = true;
            return this;
        }
    }

    private List<Profile> profile;

    public void addProfile(Profile profile) {
        List<String> profileNameToAdd = profile.isDb2 ? this.db2Profiles : this.postgresProfiles;
        profileNameToAdd.add(profile.profileName);

        profileConfigs.put(profile.profileName, new ArrayList<>());

        List<DbConfig> dbToAdd = profile.isDb2 ? this.db2s : this.postgres;
        dbToAdd.add(new DbConfig(profileConfigs.get(profile.profileName)).profile(profile).ctrl(profile));
        profile.mains.forEach(main -> dbToAdd.add(new DbConfig(profileConfigs.get(profile.profileName)).profile(profile).main(profile, main)));
    }

    static Function<String, String> splitterBuilder = name -> "<!-- ###############=====..." + StringUtil.toUPPER_UNDERLINE(name) + "...====#############-->";
    @Override
    public void prepareSeparator() {
        separator("header").splitterBuilder(splitterBuilder);
        separator("footer").splitterBuilder(splitterBuilder);
    }

    protected Supplier<String> renderFilePathSupplier;
    @Override
    public String buildRenderFilePath() {
        return this.renderFilePathSupplier != null ? this.renderFilePathSupplier.get() : "D:\\wildfly-15.0.1.Final\\TH6-1.3\\configuration\\TOMMY_standalone_1.3.xml";
    }

}
