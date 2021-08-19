package generator.externals.synergix.standalone;

import generator.abstracts.render.AbstractRender;

public class StandaloneRender extends AbstractRender<StandaloneModel> {

    @Override
    protected void handleModel(StandaloneModel model) {
        model.addProfile(new StandaloneModel.Profile("HANS", ProfileEnum.POSTGRES)
                .ctrlName("HANCTRL")
                .host("172.18.0.22")
                .port("5432")
                .username("postgres")
                .password("Taskhub1")
                .mains("HANS", "HANSCC", "HANICC", "HANIFB", "HANSFB", "MTSING")
        );
        model.addProfile(new StandaloneModel.Profile("BUNCHA_PG", ProfileEnum.POSTGRES)
                .ctrlName("mcd")
                .host("172.18.5.29")
                .port("5432")
                .username("postgres")
                .password("x")
                .main("mcfries", "mainDataSource")
        );
        model.addProfile(new StandaloneModel.Profile("SA3", ProfileEnum.POSTGRES)
                .ctrlName("sa3ctrl")
                .host("172.16.0.70")
                .port("5432")
                .username("postgres")
                .password("x")
                .main("sa3main1", "main1")
                .main("sa3main2", "main2")
                .main("sa3main3", "main3")
                .main("sa3main4", "main4")
                .main("sa3main5", "main5")
        );
        model.addProfile(new StandaloneModel.Profile("HAPPY", ProfileEnum.DB2)
                .ctrlName("HAPPY")
                .host("172.18.0.25")
                .port("50000")
                .username("db2admin")
                .password("Taskhub1")
                .mains("FUNNY", "HARMONY")
                .raw()
        );
        model.addProfile(new StandaloneModel.Profile("BUNCHA_DB2", ProfileEnum.DB2)
                .ctrlName("MCD")
                .host("172.18.5.29")
                .port("50000")
                .username("Db2admin")
                .password("silvergoldplatinum")
                .main("MCFRIES", "mainDataSource")
                .main("MCFRIES2", "main2DataSource")
        );
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    public static void main(String[] args) {

        StandaloneRender render = new StandaloneRender();
        render.executeRenders();
//
//        render.templatePathSupplier = () -> render.thisRenderPath() + "jndiHolder.ftl";
//        render.getModelFiles().get(0).renderFilePathSupplier = () -> "D:\\synergix-workspace\\TH6-Intel\\TH6\\src\\main\\java\\synergix\\th6\\business\\util\\persistence\\JndiDeclare.java";
//        render.executeRenders();
//
//        render.templatePathSupplier = () -> render.thisRenderPath() + "dbconfig.xml.ftl";
//        render.getModelFiles().get(0).renderFilePathSupplier = () -> "C:\\SupperModel\\dist\\dbconfig.xml";
//        render.executeRenders();
//
//        render.templatePathSupplier = () -> render.thisRenderPath() + "SyncTommyDbs.bat.ftl";
//        render.getModelFiles().get(0).renderFilePathSupplier = () -> "C:\\SupperModel\\dist\\_syncTommyDbs.bat";
//        render.executeRenders();

//        render.templatePathSupplier = () -> render.thisRenderPath() + "Th5Standalone.xml.ftl";
//        render.getModelFiles().get(0).renderFilePathSupplier = () -> "D:\\wildfly-15.0.1.Final\\TH5\\configuration\\standalone.xml";
//        render.executeRenders();

//        render.templatePathSupplier = () -> render.thisRenderPath() + "RUNNING_PROFILE.xml.ftl";
//        render.getModelFiles().get(0).renderFilePathSupplier = () -> "D:\\synergix-workspace\\TH6-Intel\\TH6\\notes\\tommy_note\\workbench_setup\\TOMMY_RUNING_PROFILE.xml";
//        render.executeRenders();

    }
}
