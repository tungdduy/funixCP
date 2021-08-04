import {Company} from "../../business/entities/company";
import {User} from "../../business/entities/user";
import {Employee} from "../../business/entities/employee";
import {EntityIdentifier, EntityField} from "../../business/abstract/XeFormData";
import {StringUtil} from "./string.util";

export class EntityUtil {

  static newByEntityDefine(entityDefine: EntityIdentifier) {
    let entity = {};
    if (entityDefine.className.toLowerCase() === 'company') {
      entity = new Company();
    } else if (entityDefine.className.toLowerCase() === 'user') {
      entity = new User();
    } else if (entityDefine.className.toLowerCase() === 'employee') {
      entity = new Employee();
    }
    entityDefine.idFields().forEach(field => {
      this.getFieldOwnerEntity(entity, field)[field.name] = field.value;
    });
    return entity;
  }

  static isIdValid(entity: any, entityDefine: EntityIdentifier): boolean {
    if (!entityDefine) return false;
    const idFields = entityDefine.idFields();
    for (const idField of idFields) {
      const id = Number.parseInt(entity[idField.name], 10);
      if (isNaN(id) || id <= 0) {
        return false;
      }
    }
    return true;
  }

  static isMatchingId(entityDefine: EntityIdentifier, entity1, entity2): boolean {
    for (const idField of entityDefine.idFields()) {
      if (entity1[idField.name] !== entity2[idField.name]) {
        return false;
      }
    }
    return true;
  }

  static getFieldOwnerEntity(oriEntity: any, field: EntityField) {
    if (!field.subEntities) {
      return oriEntity;
    }
    let finalEntity = oriEntity;
    for (const subEntity of field.subEntities) {
      finalEntity = finalEntity[subEntity];
    }
    return finalEntity;
  }

  static getFieldsChain(field: EntityField) {
    const chains = field.subEntities ? Object.assign([], field.subEntities) : [];
    chains.push(field.name);
    return chains.join(".");
  }

  static getFieldValue(oriEntity: any, field: EntityField) {
    return this.getFieldOwnerEntity(oriEntity, field)[field.name];
  }

  static getProfileImageUrl(oriEntity: any, field: EntityField) {
    if (field.subEntities) {
      field.subEntities.forEach(subEntity => {
        oriEntity = oriEntity[subEntity];
      });
    }
    return oriEntity.profileImageUrl;
  }

  static getFieldOwnerMainId(field: EntityField, entityDefine: EntityIdentifier, entity: any) {
    const ownerId = {};
    const owner = this.getFieldOwnerClassName(field, entityDefine);
    const ownerIdName = StringUtil.lowercaseFirstLetter(owner) + "Id";
    ownerId[ownerIdName] = entity[ownerIdName];
    console.log(ownerId);
    return ownerId;
  }

  static getFieldOwnerClassName(field: EntityField, entityDefine: EntityIdentifier) {
    return field.subEntities
      ? StringUtil.upperFirstLetter(field.subEntities[field.subEntities.length - 1])
      : entityDefine.className;
  }

  static fetchAndFlatAllPossibleId(entity: any, entityDefine: EntityIdentifier) {
    const result = {};
    entityDefine.idFields().forEach(idField => {
      result[idField.name] = this.getFieldOwnerEntity(entity, idField)[idField.name];
      result[this.getFieldsChain(idField)] = this.getFieldOwnerEntity(entity, idField)[idField.name];
    });
    return result;
  }

  static getIdFromIdentifier(entityDefine: EntityIdentifier) {
    const result = {};
    entityDefine.idFields().forEach(idField => {
      result[idField.name] = idField.value;
    });
    return result;
  }
}
