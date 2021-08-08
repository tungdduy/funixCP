import {Company} from "../../business/entities/company";
import {User} from "../../business/entities/user";
import {Employee} from "../../business/entities/employee";
import {EntityField, EntityIdentifier} from "../../business/abstract/XeFormData";
import {StringUtil} from "./string.util";
import {TripUser} from "../../business/entities/trip-user";
import {Trip} from "../../business/entities/trip";

export class EntityUtil {

  static newByEntityDefine(entityDefine: EntityIdentifier) {
    let entity = {};
    if (entityDefine.className.toLowerCase() === 'company') {
      entity = new Company();
    } else if (entityDefine.className.toLowerCase() === 'user') {
      entity = new User();
      (entity as User).employee = new Employee();
    } else if (entityDefine.className.toLowerCase() === 'employee') {
      entity = new Employee();
      (entity as Employee).user = new User();
      (entity as Employee).company = new Company();
    } else if (entityDefine.className.toLowerCase() === 'tripuser') {
      entity = new TripUser();
      (entity as TripUser).user = new User();
      (entity as TripUser).trip = new Trip();
    }
    entityDefine.idFields().forEach(idField => {
      const entityField = this.getEntityWithField(entity, idField);
      entityField.entity[entityField.property] = idField.value;
    });
    return entity;
  }

  static isIdValid(entity: any, entityDefine: EntityIdentifier): boolean {
    if (!entityDefine) return false;
    const idFields = entityDefine.idFields();
    for (const idField of idFields) {
      const idNo = Number.parseInt(this.getFieldValue(entity, idField), 10);
      console.log(idNo);
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

  static getProfileImageUrl(oriEntity: any, otherField: EntityField) {
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

  static fetchAndFlatAllPossibleId(entity: any, entityDefine: EntityIdentifier) {
    const result = {};
    entityDefine.idFields().forEach(idField => {
      const fieldValue = this.getFieldValue(entity, idField);
      result[idField.name] = fieldValue;
      result[this.getLastFieldName(idField)] = fieldValue;
    });
    return result;
  }

  static getIdFromIdentifier(entityDefine: EntityIdentifier) {
    const result = {};
    entityDefine.idFields().forEach(idField => {
      result[this.getLastFieldName(idField)] = idField.value;
    });
    return result;
  }
}
