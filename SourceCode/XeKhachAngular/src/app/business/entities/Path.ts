// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {XeTableData} from "../../framework/model/XeTableData";
import {Company} from "./Company";
import {PathPoint} from "./PathPoint";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {InputTemplate} from "../../framework/model/EnumStatus";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class Path extends XeEntity {
    static get = (path): Path => EntityUtil.getFromCache("Path", path);
    static meta = EntityUtil.metas.Path;
    static mapFields = EntityUtil.mapFields['Path'];
    pathId: number;
    companyId: number;
    company: Company;
    pathPoints: PathPoint[];
    totalPathPoints: number;
    pathName: string;
    pathDesc: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (path: Path): EntityIdentifier<Path> => ({
    entity: path,
    clazz: Path,
    idFields: [
      {name: "pathId"},
      {name: "company.companyId"}
    ]
  })

  static new(option = {}) {
    const path = new Path();
    path.company = new Company();
    EntityUtil.assignEntity(option, path);
    return path;
  }

  static tableData = (option: XeTableData<Path> = {}, path: Path = Path.new()): XeTableData<Path> => {
    const table = Path._pathTable(path);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _pathTable = (path: Path): XeTableData<Path> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      table: {
        basicColumns: [
          /*0*/ {field: {name: 'pathName', template: InputTemplate.shortInput}},
          /*1*/ {field: {name: 'pathDesc', template: InputTemplate.shortInput}},
          /*2*/ {
            field: {name: 'totalPathPoints'},
            type: "iconOption",
            display: {row: {icon: {iconAfter: "map-marker-alt"}}}
          },
        ]
      },
      formData: {
        entityIdentifier: Path.entityIdentifier(path),
        share: {entity: Path.new()},
        fields: [
          {name: "pathName", template: InputTemplate.shortInput, required: true},
          {name: "pathDesc", template: InputTemplate.shortInput, required: true},
        ]
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

