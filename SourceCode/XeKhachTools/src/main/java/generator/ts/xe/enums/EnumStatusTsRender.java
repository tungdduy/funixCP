package generator.ts.xe.enums;


import generator.abstracts.render.AbstractRender;

public class EnumStatusTsRender extends AbstractRender<EnumStatusTsModel> {

    @Override
    protected void handleModel(EnumStatusTsModel model) {
        model.enums.add(TsEnum.name("InputMode")
                .setOptions(
                        Option.name("input"),
                        Option.name("disabled"),
                        Option.name("readonly"),
                        Option.name("selectOnly").setProperties(
                                Property.name("input"),
                                Property.name("readonly")
                        ),
                        Option.name("acceptAnyString"),
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

        model.enums.add(TsEnum.name("BussSchemeMode")
                .setOptions(
                        Option.name("edit"),
                        Option.name("bussAdmin"),
                        Option.name("readonly").setProperties(
                                Property.name("constantStatus")
                        ),
                        Option.name("ordering").setProperties(
                                Property.name("dynamicStatus")
                        ),
                        Option.name("tripAdmin").setProperties(
                                Property.name("dynamicStatus")
                        )
                )
        );

        model.enums.add(TsEnum.name("TripUserStatus")
                .setOptions(
                        Option.name("PENDING").setProperties(
                                Property.name("status").stringValue("PENDING")
                        ),
                        Option.name("CONFIRMED").setProperties(
                                Property.name("status").stringValue("CONFIRMED")
                        ),
                        Option.name("DELETED").setProperties(
                                Property.name("status").stringValue("DELETED")
                        )
                ).buildSelectMenu()
        );

        model.enums.add(TsEnum.name("EditOnRow")
                .setOptions(
                        Option.name("always").setProperties(
                                Property.name("enabled"),
                                Property.name("editing").stringValue("yes")
                        ),
                        Option.name("onClick").setProperties(
                                Property.name("enabled"),
                                Property.name("editing").stringValue("no")
                        ),
                        Option.name("disabled")
                ));

        model.enums.add(TsEnum.name("SeatStatus")
                .setOptions(
                        Option.name("locked").setProperties(
                                Property.name("classes").stringValue("seat-locked")
                        ),
                        Option.name("lockedByBuss").setProperties(
                                Property.name("classes").stringValue("seat-locked-by-buss")
                        ),
                        Option.name("lockedByTrip").setProperties(
                                Property.name("classes").stringValue("seat-locked-by-trip")
                        ),
                        Option.name("hidden").setProperties(
                                Property.name("classes").stringValue("seat-hidden")
                        ),
                        Option.name("available").setProperties(
                                Property.name("classes").stringValue("seat-available")
                        ),
                        Option.name("booked").setProperties(
                                Property.name("classes").stringValue("seat-booked")
                        ),
                        Option.name("selected").setProperties(
                                Property.name("classes").stringValue("seat-selected")
                        ),
                        Option.name("confirmed").setProperties(
                                Property.name("classes").stringValue("seat-confirmed")
                        )
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
                                Option.name("tripUserStatus").setProperties(
                                        Property.name("type").stringValue("selectOneMenu"),
                                        Property.name("display").stringValue("custom"),
                                        Property.name("swap").stringValue("on"),
                                        Property.name("boardMenu"),
                                        Property.name("pipe").value("TripStatusPipe").postFix(".instance").type("XePipe"),
                                        Property.name("defaultSelectMenu$").value("of(TripUserStatus.selectMenu)").type("Observable<SelectItem<any>[]>")
                                ),
                                Option.name("selectOneMenu").setProperties(
                                        Property.name("swap").stringValue("off"),
                                        Property.name("type").stringValue("selectOneMenu")
                                ).setManualProperties(
                                        Property.name("selectMenu$").value("Observable<SelectItem<any>[]>")
                                ),
                                Option.name("date").setProperties(
                                        Property.name("type").stringValue("date"),
                                        Property.name("pipe").value("XeDatePipe").postFix(".instance").type("XePipe")),
                                Option.name("time").setProperties(
                                        Property.name("triggerOnClick"),
                                        Property.name("autoInput"),
                                        Property.name("type").stringValue("time"),
                                        Property.name("pipe").value("XeTimePipe").postFix(".instance").type("XePipe"))
                                        .setManualProperties(
                                                Property.name("observable").value("(inputValue, criteria?) => Observable<any>")),
                                Option.name("scheduledLocation")
                                        .setProperties(
                                                Property.name("pipe").value("LocationPipe").postFix(".instance").type("XePipe"),
                                                Property.name("autoInput"))
                                        .setManualProperties(
                                                Property.name("observable").value("(inputValue, criteria?) => Observable<any>"),
                                                Property.name("criteria").value("any"),
                                                Property.name("preChange").value("(inputValue, criteria?) => any"),
                                                Property.name("postChange").value("(inputValue, criteria?) => any")),

                                Option.name("phone").setProperties(
                                        Property.name("type").stringValue("shortInput"),
                                        Property.name("pipe").value("PhonePipe").postFix(".instance").type("XePipe")),
                                Option.name("seats").setProperties(
                                        Property.name("type").stringValue("html"),
                                        Property.name("display").stringValue("custom"),
                                        Property.name("pipe").value("SeatPipe").postFix(".instance").type("XePipe")),
                                Option.name("money").setProperties(
                                        Property.name("type").stringValue("shortInput"),
                                        Property.name("pipe").value("MoneyPipe").postFix(".instance").type("XePipe")),
                                Option.name("weekDays").setProperties(
                                        Property.name("type").stringValue("multiOption"),
                                        Property.name("display").stringValue("custom"),
                                        Property.name("options").value("WeekDays").type("Options")),
                                Option.name("tableOrder"),
                                Option.name("bussSchedulePoint")
                                        .setProperties(
                                                Property.name("type").stringValue("searchTable"),
                                                Property.name("pipe").value("BussSchedulePointPipe").postFix(".instance").type("XePipe"),
                                                Property.name("display").stringValue("custom")),
                                Option.name("pathPoint")
                                        .setProperties(
                                                Property.name("type").stringValue("searchTable"),
                                                Property.name("pipe").value("PathPointPipe").postFix(".instance").type("XePipe"),
                                                Property.name("display").stringValue("custom"))
                                        .setManualProperties(
                                                Property.name("tableData").value("XeTableData<any>")),
                                Option.name("path")
                                        .setProperties(
                                                Property.name("type").stringValue("searchTable"),
                                                Property.name("pipe").value("PathPipe").postFix(".instance").type("XePipe"),
                                                Property.name("display").stringValue("custom"))
                                        .setManualProperties(
                                                Property.name("tableData").value("XeTableData<any>")),
                                Option.name("location")
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
