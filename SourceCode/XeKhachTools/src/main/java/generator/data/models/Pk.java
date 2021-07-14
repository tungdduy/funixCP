package generator.data.models;

public class Pk extends Column {
    public Pk(Class<?> dataType) {
        super(dataType);
        this.isPk = true;
        this.isNullable = false;
    }
}
