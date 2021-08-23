// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {Location} from "./Location";
import {Path} from "./Path";
import {Company} from "./Company";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {InputMode, InputTemplate} from "../../framework/model/EnumStatus";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class PathPoint extends XeEntity {
    static meta = EntityUtil.metas.PathPoint;
    pathPointId: number;
    pathId: number;
    locationId: number;
    companyId: number;
    location: Location;
    path: Path;
    pointName: string;
    pointDesc: string;
    pointOrder: number;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (pathPoint: PathPoint): EntityIdentifier<PathPoint> => ({
    entity: pathPoint,
    clazz: PathPoint,
    idFields: [
      {name: "pathPointId"},
      {name: "location.locationId"},
      {name: "path.pathId"},
      {name: "path.company.companyId"}
    ]
  })

  static new(option = {}) {
    const pathPoint = new PathPoint();
    pathPoint.location = new Location();
    pathPoint.path = new Path();
    pathPoint.path.company = new Company();
    EntityUtil.assignEntity(option, pathPoint);
    return pathPoint;
  }

  static tableData = (option: XeTableData<PathPoint> = {}, pathPoint: PathPoint = PathPoint.new()): XeTableData<PathPoint> => {
    const table = PathPoint._pathPointTable(pathPoint);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _pathPointTable = (pathPoint: PathPoint): XeTableData<PathPoint> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      display: {
        fullScreenForm: true
      },
      table: {
        basicColumns: [
          {field: {name: 'pointOrder', template: InputTemplate.tableOrder}},
          {field: {name: 'path', template: InputTemplate.pathSearch, mode: InputMode.html}},
          {/*0*/
            field: {name: 'pointName', template: InputTemplate.shortInput},
            subColumns: [
              {field: {name: 'pointDesc', template: InputTemplate.shortInput}, display: {row: {css: 'text-info'}}}
            ]
          },
          {
            field: {name: 'location', template: InputTemplate.locationSearch, required: true}
          }
        ]
      },
      formData: {
        display: {
          columnNumber: 2
        },
        entityIdentifier: PathPoint.entityIdentifier(pathPoint),
        share: {entity: PathPoint.new()},
        fields: [
          {name: 'path', template: InputTemplate.pathSearch, mode: InputMode.textTitle},
          {name: "location", template: InputTemplate.locationSearch._tableData(Location.cachedLocationTable()), required: true},
          {name: "pointName", template: InputTemplate.shortInput, required: true},
          {name: "pointDesc", template: InputTemplate.shortInput, required: true}
        ]
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

