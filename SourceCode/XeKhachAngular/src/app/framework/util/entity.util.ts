// ____________________ ::NEW_ENTITY_BY_DEFINER_SEPARATOR:: ____________________ //
import {Company} from "../../business/entities/Company";
import {User} from "../../business/entities/User";
import {Employee} from "../../business/entities/Employee";
import {EntityField, EntityIdentifier} from "../model/XeFormData";
import {StringUtil} from "./string.util";
import {TripUser} from "../../business/entities/TripUser";
import {Trip} from "../../business/entities/Trip";
import {BussType} from "../../business/entities/BussType";
import {Buss} from "../../business/entities/Buss";
import {ObjectUtil} from "./object.util";
import {BussEmployee} from "../../business/entities/BussEmployee";
import {TripUserSeat} from "../../business/entities/TripUserSeat";
import {XeLocation} from "../../business/entities/XeLocation";
import {BussSchedule} from "../../business/entities/BussSchedule";
import {BussSchedulePrice} from "../../business/entities/BussSchedulePrice";
import {BussPoint} from "../../business/entities/BussPoint";
import {BussSchedulePoint} from "../../business/entities/BussSchedulePoint";
import {CommonUpdateService} from "../../business/service/common-update.service";
import {Notifier} from "../notify/notify.service";


export class EntityUtil {

  static newByEntityDefine(entityDefine: EntityIdentifier) {
    let entity = {};
// ____________________ ::NEW_ENTITY_BY_DEFINER_SEPARATOR:: ____________________ //
    if (entityDefine.className === 'User') {
        entity = new User();
    }
    if (entityDefine.className === 'TripUserSeat') {
        entity = new TripUserSeat();
        entity['tripUser'] = new TripUser();
        entity['tripUser']['trip'] = new Trip();
        entity['tripUser']['user'] = new User();
    }
    if (entityDefine.className === 'Employee') {
        entity = new Employee();
        entity['company'] = new Company();
        entity['user'] = new User();
    }
    if (entityDefine.className === 'Company') {
        entity = new Company();
    }
    if (entityDefine.className === 'XeLocation') {
        entity = new XeLocation();
    }
    if (entityDefine.className === 'BussSchedule') {
        entity = new BussSchedule();
        entity['buss'] = new Buss();
        entity['buss']['bussType'] = new BussType();
        entity['buss']['company'] = new Company();
        entity['company'] = new Company();
    }
    if (entityDefine.className === 'BussEmployee') {
        entity = new BussEmployee();
        entity['buss'] = new Buss();
        entity['buss']['bussType'] = new BussType();
        entity['buss']['company'] = new Company();
        entity['employee'] = new Employee();
        entity['employee']['company'] = new Company();
        entity['employee']['user'] = new User();
    }
    if (entityDefine.className === 'Buss') {
        entity = new Buss();
        entity['bussType'] = new BussType();
        entity['company'] = new Company();
    }
    if (entityDefine.className === 'TripUser') {
        entity = new TripUser();
        entity['trip'] = new Trip();
        entity['trip']['bussSchedule'] = new BussSchedule();
        entity['user'] = new User();
    }
    if (entityDefine.className === 'BussSchedulePrice') {
        entity = new BussSchedulePrice();
        entity['bussSchedule'] = new BussSchedule();
        entity['bussSchedule']['buss'] = new Buss();
        entity['bussSchedule']['company'] = new Company();
    }
    if (entityDefine.className === 'BussType') {
        entity = new BussType();
    }
    if (entityDefine.className === 'BussPoint') {
        entity = new BussPoint();
        entity['company'] = new Company();
        entity['xeLocation'] = new XeLocation();
    }
    if (entityDefine.className === 'Trip') {
        entity = new Trip();
        entity['bussSchedule'] = new BussSchedule();
        entity['bussSchedule']['buss'] = new Buss();
        entity['bussSchedule']['company'] = new Company();
    }
    if (entityDefine.className === 'BussSchedulePoint') {
        entity = new BussSchedulePoint();
        entity['bussPoint'] = new BussPoint();
        entity['bussPoint']['company'] = new Company();
        entity['bussPoint']['xeLocation'] = new XeLocation();
        entity['bussSchedule'] = new BussSchedule();
        entity['bussSchedule']['buss'] = new Buss();
        entity['bussSchedule']['company'] = new Company();
    }
// ____________________ ::ABOVE_MAIN_ENTITY_ID_SEPARATOR:: ____________________ //
    entityDefine.idFields().forEach(idField => {
      const entityField = this.getEntityWithField(entity, idField);
      entityField.entity[entityField.property] = idField.value;
      entity[entityField.property] = idField.value;
    });
    return entity;
  }

  static mainEntityIdByClassName(className: string) {
// ____________________ ::ABOVE_MAIN_ENTITY_ID_SEPARATOR:: ____________________ //
    if (className === 'User') return ['userId'];
    if (className === 'TripUserSeat') return ['tripUserSeatId', 'tripUserId'];
    if (className === 'Employee') return ['employeeId', 'companyId', 'userId'];
    if (className === 'Company') return ['companyId'];
    if (className === 'XeLocation') return ['xeLocationId'];
    if (className === 'BussSchedule') return ['bussScheduleId', 'bussId', 'companyId'];
    if (className === 'BussEmployee') return ['bussEmployeeId', 'bussId', 'employeeId'];
    if (className === 'Buss') return ['bussId', 'bussTypeId', 'companyId'];
    if (className === 'TripUser') return ['tripUserId', 'tripId', 'userId'];
    if (className === 'BussSchedulePrice') return ['bussSchedulePriceId', 'bussScheduleId'];
    if (className === 'BussType') return ['bussTypeId'];
    if (className === 'BussPoint') return ['bussPointId', 'companyId', 'xeLocationId'];
    if (className === 'Trip') return ['tripId', 'bussScheduleId'];
    if (className === 'BussSchedulePoint') return ['bussSchedulePointId', 'bussPointId', 'bussScheduleId'];
// ____________________ ::BELOW_MAIN_ENTITY_ID_SEPARATOR:: ____________________ //
  }

  static isIdValid(entity: any, entityDefine: EntityIdentifier): boolean {
    if (!entityDefine) return false;
    const idFields = entityDefine.idFields();
    for (const idField of idFields) {
      const idNo = Number.parseInt(this.getFieldValue(entity, idField), 10);
      if (isNaN(idNo) || idNo <= 0) {
        return false;
      }
    }
    return true;
  }

  static isMatchingId(entityDefine: EntityIdentifier, entity1, entity2): boolean {
    for (const idField of entityDefine.idFields()) {
      if (this.getFieldValue(entity1, idField) !== this.getFieldValue(entity2, idField)) {
        return false;
      }
    }
    return true;
  }

  static getFieldOwnerEntity(oriEntity: any, field: EntityField) {
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

  static getFieldValue(entity: any, field: EntityField) {
    let result = entity[field.name];
    if (this.hasSubEntity(field)) {
      const allSubNames = field.name.split(".");
      const fieldNameOnly = allSubNames.pop();
      for (const name of allSubNames) {
        entity = entity[name];
      }
      result = entity[fieldNameOnly];
    }
    return result;
  }

  static getProfileImageUrl(oriEntity: any, otherField: EntityField): string {
    const entityField = this.getEntityWithField(oriEntity, otherField);
    return entityField.entity.profileImageUrl;
  }

  static getFieldOwnerMainId(field: EntityField, entityDefine: EntityIdentifier, entity: any) {
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

  static getFieldOwnerClassName(field: EntityField, entityDefine: EntityIdentifier) {
    return this.hasSubEntity(field)
      ? StringUtil.upperFirstLetter(this.getLastSubEntityName(field))
      : entityDefine.className;
  }

  static getIdInEntity(entity: any, entityDefine: EntityIdentifier) {
    const result = {};
    entityDefine.idFields().forEach(idField => {
      const fieldValue = this.getFieldValue(entity, idField);
      result[idField.name] = fieldValue;
      result[this.getLastFieldName(idField)] = fieldValue;
      if (idField.newIfNull) {
        const nameChain = idField.name.split(".");
        let nameChainString = "";
        if (nameChain.length > 2) {
          nameChain.pop(); // remove field Name;
          nameChain.pop(); // remove field Owner;
          nameChainString = nameChain.join(".") + ".";
        }
        result[nameChainString + "new" + idField.newIfNull + "IfNull"] = true;
      }
    });
    return result;
  }

  static getMainIdFromIdentifier(entityDefine: EntityIdentifier) {
    const mainIds = this.mainEntityIdByClassName(entityDefine.className);
    const result = {};
    entityDefine.idFields().forEach(idField => {
      const lastFieldName = this.getLastFieldName(idField);
      if (mainIds.includes(lastFieldName))
      result[lastFieldName] = idField.value;
    });
    return result;
  }

  static entityCache = {};
  static cache(entity: any, className: string) {
    const entityCaches = this.getEntityCacheByClassName(className);
    if (!entityCaches) return;
    this.cacheFromEntityCaches(entity, entityCaches);
  }
  static cacheFromEntityCaches(entity: any, entityCaches: EntityCache[]) {
    entityCaches.forEach(sub => {
      const camelName = StringUtil.lowercaseFirstLetter(sub.name);
      if (ObjectUtil.isNumberGreaterThanZero(entity[camelName])) {
        entity[camelName] = this.entityCache[camelName][entity[camelName + "Id"]];
      } else {
        if (!this.entityCache[camelName]) {
          this.entityCache[camelName] = {};
        }
        this.entityCache[camelName][entity[camelName + "Id"]] = entity[camelName];
      }
      if (sub.children) {
        EntityUtil.cacheFromEntityCaches(entity[camelName], sub.children);
      }
    });
  }
  static getEntityCacheByClassName(className: string): EntityCache[] {
// ____________________ ::BELOW_MAIN_ENTITY_ID_SEPARATOR:: ____________________ //
    if (className === 'User') return [];
    if (className === 'TripUserSeat') return [{name: 'tripUser'}];
    if (className === 'Employee') return [{name: 'company'}, {name: 'user'}];
    if (className === 'Company') return [];
    if (className === 'XeLocation') return [];
    if (className === 'BussSchedule') return [{name: 'buss'}, {name: 'company'}];
    if (className === 'BussEmployee') return [{name: 'buss'}, {name: 'employee'}];
    if (className === 'Buss') return [{name: 'bussType'}, {name: 'company'}];
    if (className === 'TripUser') return [{name: 'trip'}, {name: 'user'}];
    if (className === 'BussSchedulePrice') return [{name: 'bussSchedule'}];
    if (className === 'BussType') return [];
    if (className === 'BussPoint') return [{name: 'company'}, {name: 'xeLocation'}];
    if (className === 'Trip') return [{name: 'bussSchedule'}];
    if (className === 'BussSchedulePoint') return [{name: 'bussPoint'}, {name: 'bussSchedule'}];
// ____________________ ::ENTITY_CACHE_SEPARATOR:: ____________________ //
    return undefined;
  }
}
class EntityCache {
  name: string;
  children?: EntityCache[];
}
// ____________________ ::ENTITY_CACHE_SEPARATOR:: ____________________ //
