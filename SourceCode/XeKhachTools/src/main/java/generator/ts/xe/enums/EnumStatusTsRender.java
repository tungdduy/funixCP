package generator.ts.xe.enums;


import generator.abstracts.render.AbstractRender;

public class EnumStatusTsRender extends AbstractRender<EnumStatusTsModel> {

    @Override
    protected void handleModel(EnumStatusTsModel model) {
        model.enums.add(TsEnum.name("InputMode")
                .optionsWithoutProperty("disabled", "input", "bareTextOnly"));

        model.enums.add(
                TsEnum.name("InputTemplate")
                        .setOptions(
                                Option.name("shortInput").setProperties(
                                        Property.name("type").stringValue("shortInput")),
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
                                        Property.name("options").value("WeekDays").type("Options"))
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
