// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {Company} from "./Company";
import {Location} from "./Location";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussPoint extends XeEntity {
    static className = 'BussPoint';
    static camelName = 'bussPoint';
    static otherMainIdNames = ['companyId', 'locationId'];
    static mainIdName = 'bussPointId';
    static pkMapFieldNames = ['company', 'location'];
    locationId: number;
    companyId: number;
    bussPointId: number;
    company: Company;
    location: Location;
    bussPointName: string;
    bussPointDesc: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussPoint: BussPoint): EntityIdentifier<BussPoint> => ({
    entity: bussPoint,
    clazz: BussPoint,
    idFields: () => [
      {name: "bussPointId", value: bussPoint.bussPointId},
      {name: "company.companyId", value: bussPoint.company?.companyId},
      {name: "location.locationId", value: bussPoint.location?.locationId}
    ]
  })

  static new(option = {}) {
    const bussPoint = new BussPoint();
    bussPoint.company = new Company();
    bussPoint.location = new Location();
    ObjectUtil.assignEntity(option, bussPoint);
    return bussPoint;
  }

  static tableData = (option: XeTableData<BussPoint> = {}, bussPoint: BussPoint = BussPoint.new()): XeTableData<BussPoint> => {
    const table = BussPoint._bussPointTable(bussPoint);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussPointTable = (bussPoint: BussPoint): XeTableData<BussPoint> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    table: {
      basicColumns: [
        { // 2
          field: {name: 'bussPointName'}, type: "string",
          display: {header: {icon: {iconPre: 'map-marked-alt'}, inline: true}},
          subColumns: [{
            display: {header: {silence: true}, row: {css: 'd-block text-info'}},
            field: {name: 'bussPointDesc'}, type: 'string'
          }]
        },
        { // 2
          field: {name: 'location.locationName'}, type: "string",
          subColumns: [{
            display: {header: {silence: true}, row: {css: 'd-block text-info'}},
            field: {name: 'location.parent.locationName'}, type: 'string'
          }, {
            display: {
              header: {silence: true},
              row: {css: 'd-block text-info'}
            },
            field: {name: 'location.parent.parent.locationName'}, type: 'string'
          }
          ]
        },
      ],
    },
    formData: {
      entityIdentifier: BussPoint.entityIdentifier(bussPoint),
      share: {entity: BussPoint.new()},
      header: {
        titleField: {name: 'location.locationName'},
        descField: {name: 'location.parent.locationName'},
        subFields: [{name: 'location.parent.parent.locationName'}]
      },
      fields: [
        {name: "bussPointName", required: true},
        {name: "bussPointDesc", required: true},
        {name: "locationId", hidden: true},
        {name: "companyId", hidden: true}
      ]
    }
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}

