// ____________________ ::TOP_SEPARATOR:: ____________________ //
import {EntityField, EntityIdentifier} from "../model/XeFormData";
import {StringUtil} from "./string.util";
import {ObjectUtil} from "./object.util";
import {ClassMeta, XeEntity} from "../../business/entities/XeEntity";
import {EntityFilter, TableColumn} from "../model/XeTableData";


export class EntityUtil {

  static metas = {
// ____________________ ::TOP_SEPARATOR:: ____________________ //
    User: {
        capName: 'User',
        camelName: 'user',
        pkMetas: () => [],
        mainIdName: 'userId',
        mapFields: () => [{name: 'allMyTrips', meta: EntityUtil.metas.TripUser}, {name: 'employee', meta: EntityUtil.metas.Employee}],
    } as ClassMeta,
    Employee: {
        capName: 'Employee',
        camelName: 'employee',
        pkMetas: () => [EntityUtil.metas.Company, EntityUtil.metas.User],
        mainIdName: 'employeeId',
        mapFields: () => [],
    } as ClassMeta,
    PathPoint: {
        capName: 'PathPoint',
        camelName: 'pathPoint',
        pkMetas: () => [EntityUtil.metas.Location, EntityUtil.metas.Path],
        mainIdName: 'pathPointId',
        mapFields: () => [],
    } as ClassMeta,
    Company: {
        capName: 'Company',
        camelName: 'company',
        pkMetas: () => [],
        mainIdName: 'companyId',
        mapFields: () => [],
    } as ClassMeta,
    BussSchedule: {
        capName: 'BussSchedule',
        camelName: 'bussSchedule',
        pkMetas: () => [EntityUtil.metas.Buss],
        mainIdName: 'bussScheduleId',
        mapFields: () => [{name: 'path', meta: EntityUtil.metas.Path}, {name: 'bussSchedulePoints', meta: EntityUtil.metas.BussSchedulePoint}, {name: 'startPoint', meta: EntityUtil.metas.PathPoint}, {name: 'endPoint', meta: EntityUtil.metas.PathPoint}],
    } as ClassMeta,
    BussEmployee: {
        capName: 'BussEmployee',
        camelName: 'bussEmployee',
        pkMetas: () => [EntityUtil.metas.Buss, EntityUtil.metas.Employee],
        mainIdName: 'bussEmployeeId',
        mapFields: () => [],
    } as ClassMeta,
    Buss: {
        capName: 'Buss',
        camelName: 'buss',
        pkMetas: () => [EntityUtil.metas.BussType, EntityUtil.metas.Company],
        mainIdName: 'bussId',
        mapFields: () => [],
    } as ClassMeta,
    Location: {
        capName: 'Location',
        camelName: 'location',
        pkMetas: () => [],
        mainIdName: 'locationId',
        mapFields: () => [{name: 'parent', meta: EntityUtil.metas.Location}],
    } as ClassMeta,
    TripUser: {
        capName: 'TripUser',
        camelName: 'tripUser',
        pkMetas: () => [EntityUtil.metas.Trip, EntityUtil.metas.User],
        mainIdName: 'tripUserId',
        mapFields: () => [{name: 'confirmedBy', meta: EntityUtil.metas.Employee}],
    } as ClassMeta,
    SeatGroup: {
        capName: 'SeatGroup',
        camelName: 'seatGroup',
        pkMetas: () => [EntityUtil.metas.BussType],
        mainIdName: 'seatGroupId',
        mapFields: () => [],
    } as ClassMeta,
    BussType: {
        capName: 'BussType',
        camelName: 'bussType',
        pkMetas: () => [],
        mainIdName: 'bussTypeId',
        mapFields: () => [{name: 'seatGroups', meta: EntityUtil.metas.SeatGroup}],
    } as ClassMeta,
    Trip: {
        capName: 'Trip',
        camelName: 'trip',
        pkMetas: () => [EntityUtil.metas.BussSchedule],
        mainIdName: 'tripId',
        mapFields: () => [{name: 'tripUsers', meta: EntityUtil.metas.TripUser}],
    } as ClassMeta,
    Path: {
        capName: 'Path',
        camelName: 'path',
        pkMetas: () => [EntityUtil.metas.Company],
        mainIdName: 'pathId',
        mapFields: () => [{name: 'pathPoints', meta: EntityUtil.metas.PathPoint}],
    } as ClassMeta,
    BussSchedulePoint: {
        capName: 'BussSchedulePoint',
        camelName: 'bussSchedulePoint',
        pkMetas: () => [EntityUtil.metas.BussSchedule, EntityUtil.metas.PathPoint],
        mainIdName: 'bussSchedulePointId',
        mapFields: () => [],
    } as ClassMeta
  };

  static getClassMeta(name: string) {
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
// ____________________ ::BOTTOM_SEPARATOR:: ____________________ //
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
      const idNo = Number.parseInt(this.valueAsInlineString(entity, entityDefine.clazz.meta, idField), 10);
      if (isNaN(idNo) || idNo <= 0) {
        return false;
      }
    }
    return true;
  }

  static getFieldOwnerEntity(oriEntity: any, field: EntityField) {
    if (!oriEntity || !field) return {};
    const subEntityNames = this.getSubEntityNames(field);
    let traceEntity = oriEntity;
    for (const name of subEntityNames) {
      traceEntity = traceEntity[name];
    }
    return traceEntity;
  }

  static hasSubEntity(field: EntityField) {
    return field.name.includes(".");
  }

  static getSubEntityNames(field: EntityField) {
    const result = field.name.split(".");
    result.pop();
    return result;
  }

  static getFromCache(name, id) {
    const capName = StringUtil.upperFirstLetter(name);
    if (!this.entityCache[capName])
      this.entityCache[capName] = {};
    return this.entityCache[capName][id];
  }

  static putToCache(name, id, value) {
    const capName = StringUtil.upperFirstLetter(name);
    if (!this.entityCache[capName])
      this.entityCache[capName] = {};
    this.entityCache[capName][id] = value;
  }

  static getEntityWithField(entity: any, entityMeta: ClassMeta, field: EntityField): { entity, lastFieldName, value, fieldMeta: ClassMeta } {
    const allSubNames = field.name.split(".");
    const lastFieldName = allSubNames.pop();
    let traceEntity = entity;
    let fieldMeta = entityMeta;
    for (const name of allSubNames) {
      if (ObjectUtil.isNumberGreaterThanZero(traceEntity)) {
        traceEntity = this.getFromCache(fieldMeta.capName, traceEntity);
        if (!traceEntity) {
          break;
        }
      }
      fieldMeta = this.traceMeta(fieldMeta, name);
      if (!traceEntity[name]) {
        traceEntity[name] = {};
      }
      traceEntity = traceEntity[name];
    }
    traceEntity = ObjectUtil.isNumberGreaterThanZero(traceEntity) ? this.getFromCache(fieldMeta.capName, traceEntity) : traceEntity;
    const value =  traceEntity ? traceEntity[lastFieldName] : undefined;
    return {entity: traceEntity, lastFieldName, value, fieldMeta};
  }

  static traceMeta = (parentMeta: ClassMeta, fieldName) => {
    if (!parentMeta) return undefined;
    for (const pkMeta of parentMeta.pkMetas()) {
      if (StringUtil.equalsIgnoreCase(pkMeta.camelName, fieldName)) {
        return pkMeta;
      }
    }
    for (const mapField of parentMeta.mapFields()) {
      if (StringUtil.equalsIgnoreCase(mapField.name, fieldName)) {
        return mapField.meta;
      }
    }
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

  static getAllPossibleId(entity: any, entityDefine: EntityIdentifier<any>) {
    const result = {};
    const criteriaEntity = entityDefine.entity;
    entityDefine.idFields.forEach(idField => {
      const idFromCriteria = this.getOriginFieldValue(criteriaEntity, entityDefine.clazz.meta, idField);
      const idFromEntity = this.getOriginFieldValue(entity, entityDefine.clazz.meta, idField);
      const selectedId = idFromEntity ? idFromEntity : idFromCriteria;
      result[idField.name] = selectedId;
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
        result[nameChainString + "new" + capFieldName + "IfNull"] = true;
      }
    });
    return result;
  }

  static assignEntity(option: {}, entity, deepLvl = 0) {
    if (!option) return entity;
    if (deepLvl > 4) return entity;
    deepLvl++;
    Object.keys(option).forEach(key => {
      if (['basicColumns', 'subColumns'].includes(key)) {
        const prepareColumns = [];
        option[key].forEach((optionColumn: TableColumn | string) => {
          entity[key].forEach((column: TableColumn, index: number) => {
            if (typeof optionColumn === 'string') {
              if (optionColumn === column.field.name) {
                prepareColumns.push(column);
              }
            } else if (column.field.name === optionColumn.field.name) {
              this.assignEntity(optionColumn, column, deepLvl);
              prepareColumns.push(column);
            }
          });
        });
        entity[key] = prepareColumns;
      } else if (['function', 'string', 'boolean'].includes(typeof option[key])
        || ['xeScreen', 'parent', 'template', 'inputMode', 'observable', 'manualColumns'].includes(key)) {
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

  static entityCache = {};

  static cacheMulti<E extends XeEntity>(entities: E[], meta: ClassMeta, filters: EntityFilter = {filterSingle: (e) => true}, rootLvl = 0) {

    rootLvl++;
    if (filters.filterArray) {
      entities = filters.filterArray(entities);
    }
    const result = entities.filter(entity => {
      if (rootLvl > 3) { // do not cache more when exceed this lvl
        return filters.filterSingle(entity);
      }
      this.cache(entity, meta, rootLvl);
      return filters.filterSingle(entity);
    });
    if (this.cacheLater.length !== 0) {
      this.cacheLater.forEach(delay => {
        delay.entity[delay.fieldName] = this.getFromCache(delay.className, delay.id);
      });
    }
    return result;
  }

  static cache(entity: XeEntity, meta: ClassMeta, rootLvl = 0) {
    const cacheFields = this.buildCacheFields(meta);
    this.privateCache(entity, entity, meta, cacheFields, rootLvl);
  }

  private static buildCacheFields(meta: ClassMeta, result = []): EntityCacheField[] {
    meta.pkMetas().forEach(pkMeta => {
      const cacheField = new EntityCacheField();
      cacheField.name = pkMeta.camelName;
      cacheField.meta = pkMeta;
      cacheField.children = this.buildCacheFields(pkMeta, []);
      result.push(cacheField);
    });
    const mapCaches = this.buildMapCacheFields(meta);
    return result.concat(mapCaches);
  }

  private static buildMapCacheFields(meta: ClassMeta, result = [], lvl = 0): EntityCacheField[] {
    lvl++;
    meta.mapFields().forEach(field => {
      const cacheField = new EntityCacheField();
      cacheField.meta = field.meta;
      cacheField.name = field.name;
      if (lvl < 3) {
        cacheField.children = this.buildMapCacheFields(cacheField.meta, [], lvl);
      }
      result.push(cacheField);
    });
    return result;
  }

  static cacheLater: { entity: {}, fieldName: string, className: string, id: any }[] = [];

  private static privateCache(parent, entity, meta: ClassMeta, cacheFields: EntityCacheField[], rootLvl) {
    if (ObjectUtil.isNumberGreaterThanZero(entity)) {
      if (!this.getFromCache(meta.capName, entity)) {
        this.cacheLater.push({entity: parent, fieldName: meta.camelName, className: meta.capName, id: entity});
      } else {
        entity = this.getFromCache(meta.capName, entity);
        parent[meta.camelName] = entity;
      }
      return;
    }
    cacheFields.forEach(field => {
      const fieldValue = entity[field.name];
      if (Array.isArray(fieldValue)) {
        this.cacheMulti(fieldValue, field.meta, {filterSingle: (e) => true}, rootLvl);
        return;
      } else if (ObjectUtil.isNumberGreaterThanZero(fieldValue)) {
        if (!this.getFromCache(field.name, fieldValue)) {
          this.cacheLater.push({entity, fieldName: field.name, className: field.meta.capName, id: fieldValue});
        } else {
          entity[field.name] = this.getFromCache(field.meta.capName, fieldValue);
        }
      } else if (!ObjectUtil.isObject(fieldValue)) {
        return;
      } else { // fieldValue is Object
        const fieldId = fieldValue[field.meta.mainIdName];
        this.putToCache(field.meta.capName, fieldId, fieldValue);
      }
      if (field.children && !!entity[field.name]) {
        this.privateCache(entity, entity[field.name], field.meta, field.children, rootLvl);
      }
    });
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

  static  cachePkFromParent(parent: any, parentMeta: ClassMeta, childrenFieldName: string, childMeta: ClassMeta) {
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

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  ENTITY CACHE
}

class EntityCacheField {
  name: string;
  meta: ClassMeta;
  children?: EntityCacheField[];
}
// ____________________ ::BOTTOM_SEPARATOR:: ____________________ //
