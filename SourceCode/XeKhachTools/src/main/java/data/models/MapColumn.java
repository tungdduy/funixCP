package data.models;

import data.entities.abstracts.AbstractEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MapColumn<E extends AbstractEntity> {
    E entity;
    Column<E> column;
}
