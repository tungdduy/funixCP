package data.entities.abstracts;

import data.models.Column;
import lombok.Getter;

import java.util.function.Supplier;

import static util.StringUtil.PHONE_REGEX;

@Getter
public enum DataType {

    DESCRIPTION(() -> new Column().maxLen(255)),
    REQUIRE_SHORT_STRING(() -> new Column().minLen(3).maxLen(30).notNull()),
    PHONE(() -> new Column().regex(PHONE_REGEX)),
    EMAIL(() -> new Column().email()),
    FALSE(() -> new Column(Boolean.class).defaultValue(false)),
    TRUE(() -> new Column(Boolean.class).defaultValue(true))
    ;


    DataType(Supplier<Column> column) {
        this.column = column;
    }
    Supplier<Column> column;
}
