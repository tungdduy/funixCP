package data.entities.abstracts;

import data.models.Column;
import lombok.Getter;

import java.util.function.Supplier;

import static util.StringUtil.PHONE_REGEX;

@Getter
public enum DataType {
    DESCRIPTION(() -> new Column().maxLen(255)),
    COMMA_SEPARATOR(() -> new Column()),
    LONG_TEXT(() -> new Column()),
    CODE(() -> new Column().maxLen(30).upperOnly()),
    QUANTITY(() -> new Column(Integer.class).defaultValue(0)),
    MONEY(() -> new Column(Long.class).defaultValue(0L)),
    MAP_COUNT(() -> new Column(Integer.class).ignoreManualUpdate()),
    REQUIRE_SHORT_STRING(() -> new Column().minLen(3).maxLen(30).notNull()),
    PHONE(() -> new Column().regex(PHONE_REGEX)),
    EMAIL(() -> new Column().email()),
    FALSE(() -> new Column(Boolean.class).defaultValue(false)),
    TRUE(() -> new Column(Boolean.class).defaultValue(true)),
    SECRET_TOKEN(() -> new Column().maxLen(255).jsonIgnore()),
    TIME_ONLY(() -> new Column(java.util.Date.class).parseExpression("XeDateUtils.timeAppToApi(value)").packageImports("net.timxekhach.utility.XeDateUtils")),
    DATE_ONLY(() -> new Column(java.util.Date.class).parseExpression("XeDateUtils.dateAppToApi(value)").packageImports("net.timxekhach.utility.XeDateUtils")),
    DATE_TIME(() -> new Column(java.util.Date.class).parseExpression("XeDateUtils.dateTimeAppToApi(value)").packageImports("net.timxekhach.utility.XeDateUtils")),
    ;


    DataType(Supplier<Column> column) {
        this.column = column;
    }
    Supplier<Column> column;
}
