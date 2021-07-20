package net.timxekhach.operation.data.mapped.abstracts;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class XePk implements Serializable {
    private HashMap<String, Object> getColumns() {
        final HashMap<String, Object> columns = new HashMap<String, Object>();

        final Field[] fields = this.getClass().getDeclaredFields();

        for (final Field f : fields) {
            if (f.getAnnotation(javax.persistence.Column.class) != null) {
                try {
                    f.setAccessible(true);
                    final Object column = f.get(this);
                    columns.put(f.getName(), column);
                }
                catch (final IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return columns;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        final Map<String, Object> columns = this.getColumns();
        for (final Object column : columns.values()) {
            result = prime * result + (column == null ? 0 : column.hashCode());
        }
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        final Class<?> thisClass = this.getClass();
        final Class<?> otherClass = other.getClass();

        if (!thisClass.isAssignableFrom(otherClass) && !otherClass.isAssignableFrom(thisClass)) {
            return false;
        }

        final Map<String, Object> thisColumns = this.getColumns();
        final Map<String, Object> otherColumns = ((XePk) other).getColumns();

        for (final String name : thisColumns.keySet()) {
            final Object thisColumn = thisColumns.get(name);
            final Object otherColumn = otherColumns.get(name);

            if (!(Objects.equals(thisColumn, otherColumn))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("(");

        final Field[] fields = this.getClass().getDeclaredFields();

        for (final Field f : fields) {
            if (f.getAnnotation(javax.persistence.Column.class) != null) {
                try {
                    f.setAccessible(true);
                    s.append(f.getName()).append("=").append(f.get(this)).append(";");
                }
                catch (final IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return s + ")";
    }
}
