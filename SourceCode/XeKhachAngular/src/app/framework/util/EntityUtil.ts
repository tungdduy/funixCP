// ____________________ ::TOP_SEPARATOR:: ____________________ //
import {EntityField, EntityIdentifier} from "../model/XeFormData";
import {StringUtil} from "./string.util";
import {ObjectUtil} from "./object.util";
import {ClassMeta, XeEntity} from "../../business/entities/XeEntity";
import {TableColumn} from "../model/XeTableData";


export class EntityUtil {

  static metas = {
// ____________________ ::TOP_SEPARATOR:: ____________________ //
    User: {
      capName: 'User',
      camelName: 'user',
      pkMetas: () => [],
      mainIdName: 'userId',
    } as ClassMeta,
    Employee: {
      capName: 'Employee',
      camelName: 'employee',
      pkMetas: () => [EntityUtil.metas.Company, EntityUtil.metas.User],
      mainIdName: 'employeeId',
    } as ClassMeta,
    PathPoint: {
      capName: 'PathPoint',
      camelName: 'pathPoint',
      pkMetas: () => [EntityUtil.metas.Location, EntityUtil.metas.Path],
      mainIdName: 'pathPointId',
    } as ClassMeta,
    Company: {
      capName: 'Company',
      camelName: 'company',
      pkMetas: () => [],
      mainIdName: 'companyId',
    } as ClassMeta,
    BussSchedule: {
      capName: 'BussSchedule',
      camelName: 'bussSchedule',
      pkMetas: () => [EntityUtil.metas.Buss],
      mainIdName: 'bussScheduleId',
    } as ClassMeta,
    BussEmployee: {
      capName: 'BussEmployee',
      camelName: 'bussEmployee',
      pkMetas: () => [EntityUtil.metas.Buss, EntityUtil.metas.Employee],
      mainIdName: 'bussEmployeeId',
    } as ClassMeta,
    Buss: {
      capName: 'Buss',
      camelName: 'buss',
      pkMetas: () => [EntityUtil.metas.BussType, EntityUtil.metas.Company],
      mainIdName: 'bussId',
    } as ClassMeta,
    Location: {
      capName: 'Location',
      camelName: 'location',
      pkMetas: () => [],
      mainIdName: 'locationId',
    } as ClassMeta,
    TripUser: {
      capName: 'TripUser',
      camelName: 'tripUser',
      pkMetas: () => [EntityUtil.metas.Trip],
      mainIdName: 'tripUserId',
    } as ClassMeta,
    SeatGroup: {
      capName: 'SeatGroup',
      camelName: 'seatGroup',
      pkMetas: () => [EntityUtil.metas.BussType],
      mainIdName: 'seatGroupId',
    } as ClassMeta,
    BussType: {
      capName: 'BussType',
      camelName: 'bussType',
      pkMetas: () => [],
      mainIdName: 'bussTypeId',
    } as ClassMeta,
    Trip: {
      capName: 'Trip',
      camelName: 'trip',
      pkMetas: () => [EntityUtil.metas.BussSchedule],
      mainIdName: 'tripId',
    } as ClassMeta,
    Path: {
      capName: 'Path',
      camelName: 'path',
      pkMetas: () => [EntityUtil.metas.Company],
      mainIdName: 'pathId',
    } as ClassMeta,
    BussSchedulePoint: {
      capName: 'BussSchedulePoint',
      camelName: 'bussSchedulePoint',
      pkMetas: () => [EntityUtil.metas.BussSchedule, EntityUtil.metas.PathPoint],
      mainIdName: 'bussSchedulePointId',
    } as ClassMeta
  };

  static declaredMapFields = {
    User: {
      employee: EntityUtil.metas.Employee
    },
    Employee: {
      company: EntityUtil.metas.Company,
      user: EntityUtil.metas.User,
    },
    PathPoint: {
      location: EntityUtil.metas.Location,
      path: EntityUtil.metas.Path,
    },
    Company: {},
    BussSchedule: {
      buss: EntityUtil.metas.Buss,
      path: EntityUtil.metas.Path,
      bussSchedulePoints: EntityUtil.metas.BussSchedulePoint,
      startPoint: EntityUtil.metas.PathPoint,
      endPoint: EntityUtil.metas.PathPoint
    },
    BussEmployee: {
      buss: EntityUtil.metas.Buss,
      employee: EntityUtil.metas.Employee,
    },
    Buss: {
      bussType: EntityUtil.metas.BussType,
      company: EntityUtil.metas.Company,
    },
    Location: {
      parent: EntityUtil.metas.Location
    },
    TripUser: {
      trip: EntityUtil.metas.Trip,
      user: EntityUtil.metas.User,
      confirmedBy: EntityUtil.metas.Employee
    },
    SeatGroup: {
      bussType: EntityUtil.metas.BussType,
    },
    BussType: {
      seatGroups: EntityUtil.metas.SeatGroup
    },
    Trip: {
      bussSchedule: EntityUtil.metas.BussSchedule,
      tripUsers: EntityUtil.metas.TripUser
    },
    Path: {
      company: EntityUtil.metas.Company,
      pathPoints: EntityUtil.metas.PathPoint
    },
    BussSchedulePoint: {
      bussSchedule: EntityUtil.metas.BussSchedule,
      pathPoint: EntityUtil.metas.PathPoint,
    }
  };
// ____________________ ::BOTTOM_SEPARATOR:: ____________________ //
  static manualMapFields = {
    Path: {},
    Trip: {preparedTripUser: EntityUtil.metas.TripUser},
    Location: {},
    PathPoint: {},
    BussEmployee: {},
    Buss: {},
    SeatGroup: {},
    BussType: {},
    Company: {},
    TripUser: {
      endPoint: EntityUtil.metas.PathPoint,
      tripUserPoints: EntityUtil.metas.PathPoint,
      trip: EntityUtil.metas.Trip,
      startPoint: EntityUtil.metas.PathPoint
    },
    BussSchedulePoint: {},
    BussSchedule: {preparedTrip: EntityUtil.metas.Trip, sortedBussSchedulePoints: EntityUtil.metas.BussSchedulePoint},
    Employee: {},
    User: {}
  };
  static entityCache = []; // cache.ClassName.id = entity -- cached.className.id has Value

  private static _mapFields;
  static get mapFields() {
    if (!this._mapFields) {
      this._mapFields = Object.assign({}, this.declaredMapFields);
      Object.keys(this.manualMapFields).forEach(className => {
        Object.keys(this.manualMapFields[className]).forEach(fieldName => {
          this._mapFields[className][fieldName] = this.manualMapFields[className][fieldName];
        });
      });
    }
    return this._mapFields;
  }

  static getMeta(name: string) {
    if (StringUtil.equalsIgnoreCase(name, 'User')) return this.metas.User;
    if (StringUtil.equalsIgnoreCase(name, 'Employee')) return this.metas.Employee;
    if (StringUtil.equalsIgnoreCase(name, 'PathPoint')) return this.metas.PathPoint;
    if (StringUtil.equalsIgnoreCase(name, 'Company')) return this.metas.Company;
    if (StringUtil.equalsIgnoreCase(name, 'BussSchedule')) return this.metas.BussSchedule;
    if (StringUtil.equalsIgnoreCase(name, 'BussEmployee')) return this.metas.BussEmployee;
    if (StringUtil.equalsIgnoreCase(name, 'Buss')) return this.metas.Buss;
    if (StringUtil.equalsIgnoreCase(name, 'Location')) return this.metas.Location;
    if (StringUtil.equalsIgnoreCase(name, 'TripUser')) return this.metas.TripUser;
    if (StringUtil.equalsIgnoreCase(name, 'SeatGroup')) return this.metas.SeatGroup;
    if (StringUtil.equalsIgnoreCase(name, 'BussType')) return this.metas.BussType;
    if (StringUtil.equalsIgnoreCase(name, 'Trip')) return this.metas.Trip;
    if (StringUtil.equalsIgnoreCase(name, 'Path')) return this.metas.Path;
    if (StringUtil.equalsIgnoreCase(name, 'BussSchedulePoint')) return this.metas.BussSchedulePoint;
  }

  static newByEntityDefine(entityDefine: EntityIdentifier<any>): any {
    const entity = entityDefine.clazz.new();
    const templateEntity = entityDefine.entity;
    entityDefine.idFields.forEach(idField => {
      if (idField.name !== entityDefine.clazz.meta.mainIdName) {
        const entityField = this.getEntityWithField(entity, entityDefine.clazz.meta, idField);
        const templateField = this.getEntityWithField(templateEntity, entityDefine.clazz.meta, idField);
        entityField.entity[entityField.lastFieldName] = templateField.value;
        entity[entityField.lastFieldName] = templateField.value;
      }
    });
    entityDefine.clazz.meta.pkMetas().forEach(pkMeta => {
      entity[pkMeta.camelName] = templateEntity[pkMeta.camelName];
    });
    return entity;
  }

  static isIdValid(entity: any, entityDefine: EntityIdentifier<any>): boolean {
    if (!entityDefine) return false;
    const idFields = entityDefine.idFields;
    for (const idField of idFields) {
      const lastFieldIdName = this.getLastFieldName(idField);
      const idNo = Number.parseInt(this.valueAsInlineString(entity, entityDefine.clazz.meta, {name: lastFieldIdName}), 10);
      if (isNaN(idNo) || idNo <= 0) {
        return false;
      }
    }
    return true;
  }

  static getLastFieldOwner(rootEntity: any, rootClass: ClassMeta, field: EntityField) {
    return this.getEntityWithField(rootEntity, rootClass, field).entity;
  }

  static hasSubEntity(field: EntityField) {
    return field.name.includes(".");
  }

  static getSubEntityNames(field: EntityField) {
    const result = field.name.split(".");
    result.pop();
    return result;
  }

  static getFromCache(className, id) {
    return className && ObjectUtil.isNumberGreaterThanZero(id) ? this.entityCache[className.toLowerCase() + "." + id] : id;
  }

  static fill(entity: XeEntity, meta: ClassMeta, deepLvl = 0) {
    if (entity?.isFilled) return entity;
    if (Array.isArray(entity)) {
      entity.forEach(e => {
        this.fill(e, meta, deepLvl);
      });
    }
    if (deepLvl > 5) return entity;
    deepLvl++;
    entity = this.getFromCache(meta.capName, entity);
    if (!entity || ObjectUtil.isNumberGreaterThanZero(entity)) return entity;
    const entityMapFields = this.mapFields[meta.capName];
    Object.keys(entityMapFields).forEach(fieldName => {
      const fieldMeta = entityMapFields[fieldName];
      entity[fieldName] = this.getFromCache(fieldMeta.capName, entity[fieldName]);
      this.fill(entity[fieldName], fieldMeta, deepLvl);
    });
    entity.isFilled = true;
    return entity;
  }

  static getEntityWithField(rootEntity: any, rootMeta: ClassMeta, field: EntityField): { entity, lastFieldName, value, fieldMeta: ClassMeta } {
    if (!rootEntity || !field || ObjectUtil.isNumberGreaterThanZero(rootEntity)) return rootEntity;
    const subNames = this.getSubEntityNames(field);
    let traceEntity = rootEntity;
    let traceMeta = rootMeta;
    for (const name of subNames) {
      traceMeta = this.mapFields[traceMeta.capName][name];
      traceEntity = this.getFromCache(traceMeta.capName, traceEntity[name]);
    }
    const lastFieldName = field.name.split(".").pop();
    const value = this.getExistValue(traceEntity, lastFieldName);
    return {entity: traceEntity, lastFieldName, value, fieldMeta: traceMeta};
  }

  static getExistValue(entity, fieldName) {
    return !entity || !fieldName ? undefined : ObjectUtil.isNumberGreaterThanZero(entity) ? entity : entity[fieldName];
  }

  static valueAsInlineString(entity: any, entityMeta: ClassMeta, field: EntityField) {
    if (!field || !entity || !entityMeta) return undefined;
    const value = this.getOriginFieldValue(entity, entityMeta, field);
    let traceValue = value === undefined ? undefined : field.template?.hasPipe ? field.template.pipe.singleToInline(value) : value;
    if (field.attachInlines?.length > 0) {
      field.attachInlines.forEach(attachName => {
        traceValue += "/" + this.valueAsInlineString(entity, entityMeta, {name: attachName});
      });
    }
    return traceValue;
  }

  static getOriginFieldValue(entity: any, entityMeta: ClassMeta, field: EntityField) {
    if (!field || !entity) return undefined;
    return this.getEntityWithField(entity, entityMeta, field).value;
  }

  static getProfileImageUrl(oriEntity: any, entityMeta: ClassMeta, otherField: EntityField): string {
    if (!oriEntity || !otherField) return undefined;
    const entityField = this.getEntityWithField(oriEntity, entityMeta, otherField);
    return entityField.entity.profileImageUrl;
  }

  /** @WARNING: return field name if not exist sub Entity */
  static getLastSubEntityName(field: EntityField) {
    const allSubNames = field.name.split(".");
    if (allSubNames.length < 1) return field.name;
    const lastSubName = allSubNames.pop();
    return allSubNames.length === 0 ? lastSubName : allSubNames.pop();
  }

  static getLastFieldName(field: EntityField) {
    let result = field.name;
    if (this.hasSubEntity(field)) {
      result = field.name.split(".").pop();
    }
    return result;
  }

  static flatId(rootEntity: any, lastMeta: ClassMeta, lastEntity = {}) {
    lastMeta.pkMetas().forEach(pkMeta => {
      const fieldEntity = rootEntity[pkMeta.camelName];
      if (fieldEntity) {
        if (!fieldEntity[pkMeta.mainIdName]) {
          return;
        }
        rootEntity[pkMeta.mainIdName] = fieldEntity[pkMeta.mainIdName];
        this.flatId(rootEntity, pkMeta, lastEntity);
      }
    });
  }

  static getAllPossibleIdName(meta: ClassMeta, result = []): string[] {
    meta.pkMetas().forEach(pkMeta => {
      if (!result.includes(pkMeta.mainIdName)) result.push(pkMeta.mainIdName);
      this.getAllPossibleIdName(pkMeta, result);
    });
    return result;
  }

  static getAllPossibleId(entity: any, entityDefine: EntityIdentifier<any>) {
    const result = {};
    const criteriaEntity = entityDefine.entity;
    const newIfNulls: { meta: ClassMeta, nameChainString: string }[] = [];
    entityDefine.idFields.forEach(idField => {
      const idFromCriteria = this.getOriginFieldValue(criteriaEntity, entityDefine.clazz.meta, idField);
      const idFromEntity = this.getOriginFieldValue(entity, entityDefine.clazz.meta, idField);
      const selectedId = idFromEntity ? idFromEntity : idFromCriteria;
      if (selectedId) {
        result[idField.name] = selectedId;
      }
      if (!result[this.getLastFieldName(idField)]) {
        result[this.getLastFieldName(idField)] = selectedId;
      }
      if (idField.newIfNull) {
        const nameChain = idField.name.split(".");
        let camelFieldName;
        if (nameChain.length >= 2) {
          nameChain.pop(); // remove field Name;
          camelFieldName = nameChain.pop(); // remove field Owner;
        }
        const nameChainString = nameChain.length === 0 ? '' : nameChain.join(".") + ".";
        const capFieldName = StringUtil.upperFirstLetter(camelFieldName);
        const newIfNullMeta = EntityUtil.metas[capFieldName];
        if (newIfNullMeta) {
          newIfNulls.push({meta: newIfNullMeta, nameChainString});
        }
        result[nameChainString + "new" + capFieldName + "IfNull"] = true;
      }
    });
    if (newIfNulls.length > 0) {
      newIfNulls.forEach(newIfNull => {
        const necessaryIds = this.getAllPossibleIdName(newIfNull.meta);
        necessaryIds.forEach(newIfNullId => {
          result[newIfNull.nameChainString + newIfNull.meta.camelName + "." + newIfNullId] = result[newIfNullId];
        });
      });

    }
    return result;
  }

  static assignEntity(option: {}, entity, deepLvl = 0) {
    if (!option) return entity;
    if (deepLvl > 4) return entity;
    deepLvl++;
    Object.keys(option).forEach(key => {
      if (['basicColumns', 'subColumns', 'selectBasicColumns'].includes(key)) {
        const choices = option[key];
        if (key === 'selectBasicColumns') {
          if (option['basicColumns'] || option['subColumns']) return;
          key = 'basicColumns';
        }
        const prepareColumns = [];
        const modifiedColumns = {};
        choices.forEach(col => modifiedColumns[col?.field?.name] = col);
        const selectedColumns = option['selectBasicColumns'];
        const addedColumns: string[] = [];
        entity[key].forEach((column: TableColumn) => {
          const checkModified = modifiedColumns[column.field.name];
          if (checkModified) {
            this.assignEntity(checkModified, column, deepLvl);
          }
          if (checkModified || !selectedColumns || selectedColumns.includes(column.field.name)) {
            this.addColumnIfNotExist(addedColumns, column, prepareColumns);
          }
        });
        entity[key] = prepareColumns;
      } else if (['function', 'string', 'boolean'].includes(typeof option[key])
        || ['xeScreen', 'editOnRow', 'action', 'screen', 'parent', 'template', 'inputMode', 'observable', 'customObservable', 'manualColumns'].includes(key)) {
        entity[key] = option[key];
      } else if (option[key] === undefined) {
        delete entity[key];
      } else if (ObjectUtil.isObject(option[key])) {
        if (entity === undefined) entity = {};
        if (entity[key] === undefined) Array.isArray(option[key]) ? entity[key] = [] : entity[key] = {};
        EntityUtil.assignEntity(option[key], entity[key], deepLvl);
      } else {
        entity[key] = option[key];
      }
    });
    if (ObjectUtil.isObject(entity)) {
      Object.keys(entity).forEach(key => {
        if (Array.isArray(entity[key])) {
          entity[key] = entity[key].filter(e => e !== undefined);
        }
      });
    }
    return entity;
  }


// ENTITY CACHE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

  public static cache(entity: any, entityMeta: ClassMeta) {
    if (Array.isArray(entity)) {
      entity.forEach(child => {
        this.privateCache(child, entityMeta);
      });
    } else {
      this.privateCache(entity, entityMeta);
    }
    this.fill(entity, entityMeta);
  }

  static cachePkEntities(entity: any, pkMapFieldNames: string[], cache: {}) {
    pkMapFieldNames.forEach(fieldName => {
      const camelId = fieldName + "Id";
      if (ObjectUtil.isNumberGreaterThanZero(entity[fieldName])) {
        entity[fieldName] = cache[fieldName][entity[camelId]];
      } else {
        if (!cache[fieldName]) {
          cache[fieldName] = {};
        }
        cache[fieldName][entity[camelId]] = entity[fieldName];
      }
    });
  }

  static cachePkFromParent(parent: any, parentMeta: ClassMeta, childrenFieldName: string, childMeta: ClassMeta) {
    const copyParent = Object.assign({}, parent);
    copyParent[childrenFieldName] = copyParent[childrenFieldName].map(child => child[childMeta.mainIdName]);
    const cache = {};
    cache[parentMeta.camelName] = {};
    cache[parentMeta.camelName][parent[parentMeta.mainIdName]] = copyParent;
    parent[childrenFieldName].forEach(child => {
      this.cachePkEntities(child, [parentMeta.camelName], cache);
    });
    return parent[childrenFieldName];
  }

  private static addColumnIfNotExist(addedColumns: string[], column: TableColumn, prepareColumns: any[]) {
    if (!addedColumns.includes(column.field.name)) {
      prepareColumns.push(column);
      addedColumns.push(column.field.name);
    }
  }

  private static privateCache(entity: any, entityMeta: ClassMeta, cached = {}) {
    if (!entity || ObjectUtil.isNumberGreaterThanZero(entity)) return;
    const eid = entityMeta.capName.toLowerCase() + "." + entity[entityMeta.mainIdName];
    if (!cached[eid]) {
      cached[eid] = "true";
      this.entityCache[eid] = entity;
      Object.keys(entity).forEach(fieldName => {
        const fieldMeta: ClassMeta = this.mapFields[entityMeta.capName][fieldName];
        if (fieldMeta) {
          const fieldValue = entity[fieldName];
          if (Array.isArray(fieldValue)) {
            fieldValue.forEach(fieldChild => {
              this.privateCache(fieldChild, fieldMeta, cached);
            });
          } else {
            if (!ObjectUtil.isNumberGreaterThanZero(fieldValue) && !!fieldValue) {
              const uniqueIdName = fieldMeta.capName.toLowerCase() + "." + fieldValue[fieldMeta.mainIdName];
              this.entityCache[uniqueIdName] = fieldValue;
              this.privateCache(fieldValue, fieldMeta, cached);
            }
          }
        }
      });
    }
  }

  private static put(obj, nameChain: string[], value) {
    nameChain.forEach((name, idx) => {
      if (!obj[name]) obj[name] = {};
      if (idx === nameChain.length - 1) {
        obj[name] = value;
      } else {
        obj = obj[name];
      }
    });
  }

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  ENTITY CACHE
}

class EntityCacheField {
  name: string;
  meta: ClassMeta;
  children?: EntityCacheField[];
}

// ____________________ ::BOTTOM_SEPARATOR:: ____________________ //
