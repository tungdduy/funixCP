package generator.ts.xe.enums;


import generator.abstracts.render.AbstractRender;

public class EnumStatusTsRender extends AbstractRender<EnumStatusTsModel> {

    @Override
    protected void handleModel(EnumStatusTsModel model) {
        model.enums.add(TsEnum.name("InputMode")
                .setOptions(
                        Option.name("input"),
                        Option.name("disabled"),
                        Option.name("inputNoTitle").setProperties(
                                Property.name("input"),
                                Property.name("hideTitle")
                        ),
                        Option.name("text").setProperties(
                                Property.name("forbidChange")),
                        Option.name("html").setProperties(
                                Property.name("forbidChange")),
                        Option.name("htmlTitle").setProperties(
                                Property.name("html"),
                                Property.name("forbidChange"),
                                Property.name("showTitle")),
                        Option.name("textTitle").setProperties(
                                Property.name("forbidChange"),
                                Property.name("text"),
                                Property.name("showTitle"))
                )
        );

        model.enums.add(
                TsEnum.name("InputTemplate")
                        .setOptions(
                                Option.name("shortInput").setProperties(
                                        Property.name("type").stringValue("shortInput")),
                                Option.name("booleanToggle").setProperties(
                                        Property.name("display").stringValue("custom"),
                                        Property.name("type").stringValue("booleanToggle")),
                                Option.name("selectOneMenu").setProperties(
                                        Property.name("type").stringValue("selectOneMenu")),
                                Option.name("date").setProperties(
                                        Property.name("type").stringValue("date"),
                                        Property.name("pipe").value("XeDatePipe").postFix(".instance").type("XePipe")),
                                Option.name("time").setProperties(
                                        Property.name("type").stringValue("time"),
                                        Property.name("pipe").value("XeTimePipe").postFix(".instance").type("XePipe")),
                                Option.name("phone").setProperties(
                                        Property.name("type").stringValue("shortInput"),
                                        Property.name("pipe").value("PhonePipe").postFix(".instance").type("XePipe")),
                                Option.name("money").setProperties(
                                        Property.name("type").stringValue("shortInput"),
                                        Property.name("pipe").value("MoneyPipe").postFix(".instance").type("XePipe")),
                                Option.name("weekDays").setProperties(
                                        Property.name("type").stringValue("multiOption"),
                                        Property.name("display").stringValue("custom"),
                                        Property.name("options").value("WeekDays").type("Options")),
                                Option.name("tableOrder"),
                                Option.name("pathPointSearch")
                                        .setProperties(
                                                Property.name("type").stringValue("searchTable"),
                                                Property.name("pipe").value("PathPointPipe").postFix(".instance").type("XePipe"),
                                                Property.name("display").stringValue("custom"))
                                        .setManualProperties(
                                                Property.name("tableData").value("XeTableData<any>")),
                                Option.name("pathSearch")
                                        .setProperties(
                                                Property.name("type").stringValue("searchTable"),
                                                Property.name("pipe").value("PathPipe").postFix(".instance").type("XePipe"),
                                                Property.name("display").stringValue("custom"))
                                        .setManualProperties(
                                                Property.name("tableData").value("XeTableData<any>")),
                                Option.name("locationSearch")
                                        .setProperties(
                                                Property.name("type").stringValue("searchTable"),
                                                Property.name("pipe").value("LocationPipe").postFix(".instance").type("XePipe"),
                                                Property.name("display").stringValue("custom"))
                                        .setManualProperties(
                                                Property.name("tableData").value("XeTableData<any>"))

                        )


        );
    }


    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    public static void main(String[] args) {
        new EnumStatusTsRender().singleRender();
    }
}
