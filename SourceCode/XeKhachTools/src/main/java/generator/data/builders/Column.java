package generator.data.builders;

import generator.data.entities.abstracts.AbstractEntityBuilder;

@SuppressWarnings("all")
public abstract class Column<E> {
    private E dataType;
    private boolean nullable = true;
    private E defaultValue;

    public static Column of(Class<?> dataType) {
        if (Number.class.isAssignableFrom(dataType)) {
            return new ColumnNumber();
        }
        if (String.class == dataType) {
            return new ColumnString();
        }
        if(dataType.isAssignableFrom(AbstractEntityBuilder.class)) {
            return new ColumnEntity();
        }
        return new ColumnObject();
    }

    public Column notNull() {
        this.nullable = false;
        return this;
    }

}
