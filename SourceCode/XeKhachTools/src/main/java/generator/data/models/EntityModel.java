package generator.data.models;

import generator.data.builders.Column;
import generator.data.models.pojos.Pk;

import java.util.List;

public class EntityModel {
    List<Column> columns;
    Pk primaryKey;
}
