// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class Location extends XeEntity {
    static className = 'Location';
    static camelName = 'location';
    static otherMainIdNames = [];
    static mainIdName = 'locationId';
    static pkMapFieldNames = [];
    locationId: number;
    parent: Location ;
    parentLocationId: number;
    locationName: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (location: Location): EntityIdentifier<Location> => ({
    entity: location,
    clazz: Location,
    idFields: () => [
      {name: "locationId", value: location.locationId},
    ]
  })

  static new(option = {}) {
    return new Location();
  }

  static tableData = (option: XeTableData<Location> = {}, location: Location = Location.new()): XeTableData<Location> => {
    const table = Location._locationTable(location);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _locationTable = (location: Location): XeTableData<Location> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    table: {
      basicColumns: [
        { // 2
          field: {name: 'name'}, type: "string",
          subColumns: [{
            field: {name: 'parent.name'}, type: 'string',
            display: {row: {css: 'd-block text-info'}}
          }]
        },
      ],
    },
    formData: {
      entityIdentifier: Location.entityIdentifier(location),
      share: {entity: new Location()},
      header: {
        titleField: {name: 'name'},
        descField: {name: 'parent.name'},
      },
      fields: [
        {name: "name", required: true},
      ]
    }
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}

