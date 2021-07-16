package data.entities.abstracts;

import data.models.Column;
import lombok.Getter;

import static util.StringUtil.EMAIL_REGEX;
import static util.StringUtil.PHONE_REGEX;

@Getter
public enum DataType {

    DESCRIPTION(new Column().maxLen(255)),
    REQUIRE_SHORT_STRING(new Column().minLen(3).maxLen(25).notNull()),
    PHONE(new Column().regex(PHONE_REGEX)),
    EMAIL(new Column().regex(EMAIL_REGEX))
    ;


    DataType(Column column) {
        this.column = column;
    }
    Column column;
}
