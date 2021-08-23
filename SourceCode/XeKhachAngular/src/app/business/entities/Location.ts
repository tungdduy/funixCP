// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {CommonUpdateService} from "../service/common-update.service";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class Location extends XeEntity {
    static meta = EntityUtil.metas.Location;
    locationId: number;
    parent: Location ;
    parentLocationId: number;
    locationName: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
  private static _cachedLocationTable;
  static cachedLocationTable = (): XeTableData<Location> => {
    if (!Location._cachedLocationTable) {
      Location._cachedLocationTable = Location.tableData();
    }
    return Location._cachedLocationTable;
  }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (location: Location): EntityIdentifier<Location> => ({
    entity: location,
    clazz: Location,
    idFields: [
      {name: "locationId"},
    ]
  })

  static new(option = {}) {
    return new Location();
  }

  static tableData = (option: XeTableData<Location> = {}, location: Location = Location.new()): XeTableData<Location> => {
    const table = Location._locationTable(location);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _locationTable = (location: Location): XeTableData<Location> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      table: {
        action: {
          filters: {
            filterArray: (locations: Location[]) => {
              if (!locations) return locations;
              const parentId = [];
              return locations.filter(loc => {
                  parentId.push(loc.parent?.locationId);
                  parentId.push(loc.parent?.parent?.locationId);
                  return !parentId.includes(loc.locationId);
                }
              );
            }
          }
        },
        mode: {
          hideSelectColumn: true,
          readonly: true,
          lazySearch: (term: string) => CommonUpdateService.instance.searchLocation(term)
        },
        basicColumns: [
          { // 2
            field: {name: 'locationName'}, type: "string", hiddenClass: 'd-none',
            display: {row: {css: 'd-block text-info'}},
          },
          {
            field: {name: 'parent.locationName'}, hiddenClass: 'd-none', type: 'string'
          },
          {
            field: {name: 'parent.parent.locationName'}, hiddenClass: 'd-none', type: 'string',
          }
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
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

