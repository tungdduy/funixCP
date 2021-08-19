// ____________________ ::HEADER_SEPARATOR:: ____________________ //
import {EntityField, EntityIdentifier} from "../model/XeFormData";
import {StringUtil} from "./string.util";
import {ObjectUtil} from "./object.util";
import {XeEntity, XeEntityClass} from "../../business/entities/XeEntity";
import {Employee} from "../../business/entities/Employee";
import {User} from "../../business/entities/User";
import {TripUserSeat} from "../../business/entities/TripUserSeat";
import {Company} from "../../business/entities/Company";
import {BussSchedule} from "../../business/entities/BussSchedule";
import {Location} from "../../business/entities/Location";
import {BussEmployee} from "../../business/entities/BussEmployee";
import {Buss} from "../../business/entities/Buss";
import {TripUser} from "../../business/entities/TripUser";
import {SeatGroup} from "../../business/entities/SeatGroup";
import {BussSchedulePrice} from "../../business/entities/BussSchedulePrice";
import {BussType} from "../../business/entities/BussType";
import {BussPoint} from "../../business/entities/BussPoint";
import {Trip} from "../../business/entities/Trip";
import {BussSchedulePoint} from "../../business/entities/BussSchedulePoint";
import {Observable} from "rxjs";
import {CommonUpdateService} from "../../business/service/common-update.service";


export class EntityUtil {

  static getClassByClassName(className: string): XeEntityClass<any> {
// ____________________ ::HEADER_SEPARATOR:: ____________________ //
    if (StringUtil.equalsIgnoreCase(className, 'User')) return User;
    if (StringUtil.equalsIgnoreCase(className, 'TripUserSeat')) return TripUserSeat;
    if (StringUtil.equalsIgnoreCase(className, 'Employee')) return Employee;
    if (StringUtil.equalsIgnoreCase(className, 'Company')) return Company;
    if (StringUtil.equalsIgnoreCase(className, 'BussSchedule')) return BussSchedule;
    if (StringUtil.equalsIgnoreCase(className, 'BussEmployee')) return BussEmployee;
    if (StringUtil.equalsIgnoreCase(className, 'Buss')) return Buss;
    if (StringUtil.equalsIgnoreCase(className, 'Location')) return Location;
    if (StringUtil.equalsIgnoreCase(className, 'TripUser')) return TripUser;
    if (StringUtil.equalsIgnoreCase(className, 'SeatGroup')) return SeatGroup;
    if (StringUtil.equalsIgnoreCase(className, 'BussSchedulePrice')) return BussSchedulePrice;
    if (StringUtil.equalsIgnoreCase(className, 'BussType')) return BussType;
    if (StringUtil.equalsIgnoreCase(className, 'BussPoint')) return BussPoint;
    if (StringUtil.equalsIgnoreCase(className, 'Trip')) return Trip;
    if (StringUtil.equalsIgnoreCase(className, 'BussSchedulePoint')) return BussSchedulePoint;
// ____________________ ::GET_CLASS_BY_CLASS_NAME_SEPARATOR:: ____________________ //
  }

  static newByEntityDefine(entityDefine: EntityIdentifier<any>): any {
    const entity = entityDefine.clazz.new();
    entityDefine.idFields().forEach(idField => {
      const entityField = this.getEntityWithField(entity, idField);
      entityField.entity[entityField.property] = idField.value;
      entity[entityField.property] = idField.value;
    });
    return entity;
  }

  static isIdValid(entity: any, entityDefine: EntityIdentifier<any>): boolean {
    if (!entityDefine) return false;
    const idFields = entityDefine.idFields();
    for (const idField of idFields) {
      const idNo = Number.parseInt(this.getReadableFieldValue(entity, idField), 10);
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

  static getEntityWithField(entity: any, field: EntityField) {
    const allSubNames = field.name.split(".");
    const fieldNameOnly = allSubNames.pop();
    let traceEntity = entity;
    for (const name of allSubNames) {
      if (!traceEntity[name]) {
        traceEntity[name] = {};
      }
      traceEntity = traceEntity[name];
    }
    return {entity: traceEntity, property: fieldNameOnly};
  }

  static getReadableFieldValue(entity: any, field: EntityField) {
    if (!field || !entity) return undefined;
    let result = entity[field.name];
    if (this.hasSubEntity(field)) {
      const allSubNames = field.name.split(".");
      const fieldNameOnly = allSubNames.pop();
      for (const name of allSubNames) {
        entity = ObjectUtil.isObject(entity[name]) ? entity[name] : entity;
      }
      result = entity[fieldNameOnly];
    }
    return field.template?.hasPipe ? field.template.pipe.toReadableString(result) : result;
  }

  static getProfileImageUrl(oriEntity: any, otherField: EntityField): string {
    if (!oriEntity || !otherField) return undefined;
    const entityField = this.getEntityWithField(oriEntity, otherField);
    return entityField.entity.profileImageUrl;
  }

  static getFieldOwnerMainId(field: EntityField, entityDefine: EntityIdentifier<any>, entity: any) {
    const ownerId = {};
    const owner = this.getFieldOwnerClassName(field, entityDefine);
    const ownerIdName = StringUtil.lowercaseFirstLetter(owner) + "Id";
    ownerId[ownerIdName] = entity[ownerIdName];
    return ownerId;
  }

  /** @WARNING: error if no sub entity */
  static getLastSubEntityName(field: EntityField) {
    const allSubNames = field.name.split(".");
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

  static getFieldOwnerClassName(field: EntityField, entityDefine: EntityIdentifier<any>) {
    return this.hasSubEntity(field)
      ? StringUtil.upperFirstLetter(this.getLastSubEntityName(field))
      : entityDefine.clazz.className;
  }

  static flatThenSaveEntity(entity: any, clazz: XeEntityClass<any>): Observable<any> {
    this.flatId(entity, clazz);
    Object.keys(entity).forEach(key => {
      if (ObjectUtil.isObject(entity[key])) {
        delete entity[key];
      }
    });
    return CommonUpdateService.instance.insert(entity, clazz);
  }

  static flatId(rootEntity: any, lastClass: XeEntityClass<any>, lastEntity = {}) {
    lastClass.pkMapFieldNames.forEach(pkFieldName => {
      const fieldEntity = rootEntity[pkFieldName];
      if (fieldEntity) {
        const fieldIdName = pkFieldName + "Id";
        if (!fieldEntity[fieldIdName]) {
          return;
        }
        rootEntity[fieldIdName] = fieldEntity[fieldIdName];
        this.flatId(rootEntity, this.getClassByClassName(pkFieldName), lastEntity);
      }
    });
    console.log(rootEntity);
  }

  static getAllPossibleId(entity: any, entityDefine: EntityIdentifier<any>) {
    const result = {};
    entityDefine.idFields().forEach(idField => {
      const fieldValue = this.getReadableFieldValue(entity, idField);
      result[idField.name] = fieldValue;
      if (!result[this.getLastFieldName(idField)]) {
        result[this.getLastFieldName(idField)] = fieldValue;
      }
      if (idField.newIfNull) {
        const nameChain = idField.name.split(".");
        let nameChainString = "";
        if (nameChain.length > 2) {
          nameChain.pop(); // remove field Name;
          nameChain.pop(); // remove field Owner;
          nameChainString = nameChain.join(".") + ".";
        }
        result[nameChainString + "new" + idField.newIfNull.className + "IfNull"] = true;
      }
    });
    return result;
  }

  static getMainPkValue(entity: any, className: string) {
    const idName = StringUtil.classNameToIdName(className);
    return entity[idName];
  }

  static entityCache = {};

  static cache(entity, holders: EntityHolder[]) {
    holders.forEach(holder => {
      if (ObjectUtil.isNumberGreaterThanZero(entity[holder.fieldName])) {
        entity[holder.fieldName] = this.entityCache[holder.fieldClassName][entity[holder.fieldName]];
      } else if (!ObjectUtil.isObject(entity[holder.fieldName])) {
        return;
      } else {
        if (!this.entityCache.hasOwnProperty(holder.fieldClassName)) {
          this.entityCache[holder.fieldClassName] = {};
        }
        const fieldId = EntityUtil.getMainPkValue(entity[holder.fieldName], holder.fieldClassName);
        this.entityCache[holder.entityClassName][fieldId] = entity[holder.fieldName];
      }
      if (holder.children) {
        this.cache(entity[holder.fieldName], holder.children);
      }
    });
  }

  static cachePk<E extends XeEntity>(clazz: XeEntityClass<E>, result: E[], filterCondition: (entity: any) => boolean = () => true) {
    const cache = {};
    return result.filter((entity) => {
      EntityUtil.cachePkEntities(entity, clazz.pkMapFieldNames, cache);
      return filterCondition(entity);
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

  static cachePkFromParent(parent: any, parentClass: XeEntityClass<any>, childrenFieldName: string, childrenClass: XeEntityClass<any>) {
    const copyParent = Object.assign({}, parent);
    copyParent[childrenFieldName] = copyParent[childrenFieldName].map(child => child[childrenClass.mainIdName]);
    const cache = {};
    cache[parentClass.camelName] = {};
    cache[parentClass.camelName][parent[parentClass.mainIdName]] = copyParent;
    parent[childrenFieldName].forEach(child => {
      this.cachePkEntities(child, [parentClass.camelName], cache);
    });
    return parent[childrenFieldName];
  }
}

class EntityCache {
  fieldName?: string;
  className?: string;
  children?: EntityCache[];
}

class EntityHolder {
  entityClassName: string;
  fieldName: string;
  fieldClassName: string;
  children?: EntityHolder[];
}
// ____________________ ::GET_CLASS_BY_CLASS_NAME_SEPARATOR:: ____________________ //
