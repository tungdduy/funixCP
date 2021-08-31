import {ClassMeta} from "../../business/entities/XeEntity";
import {EntityUtil} from "../util/EntityUtil";
import {Observable} from "rxjs";
import {CommonUpdateService} from "../../business/service/common-update.service";
import {ObjectUtil} from "../util/object.util";
import {Notifier} from "../notify/notify.service";

export class Xe {
  static fill(entity, meta: ClassMeta) {
    EntityUtil.fill(entity, meta);
    return entity;
  }

  static get(entity, meta: ClassMeta, fieldChain) {
    return EntityUtil.getEntityWithField(entity, meta, {name: fieldChain}).value;
  }

  static refresh$(entity, meta: ClassMeta): Observable<any> {
    return CommonUpdateService.instance.getOne<any>(entity, meta);
  }

  static update$(entities: any, meta: ClassMeta) {
    if (!Array.isArray(entities)) return this.updateSingle$(entities, meta);
    const prepareData = [];
    entities.forEach(entity => {
      prepareData.push(this.getPrimitiveValues(entity, meta));
    });
    return CommonUpdateService.instance.updateMulti(prepareData, meta);
  }

  static getPrimitiveValues(entity, meta: ClassMeta) {
    const convertedEntity = {};
    Object.keys(entity).forEach(key => {
      switch (typeof entity[key]) {
        case "string":
        case "boolean":
        case "number":
        case "bigint":
          convertedEntity[key] = entity[key];
          break;
      }
      if (!entity[key]) {
        convertedEntity[key] = null;
      } else if (typeof entity[key]['getMonth'] === 'function') {
        convertedEntity[key] = entity[key];
      }
    });
    const fieldMetas = EntityUtil.mapFields[meta.capName];
    Object.keys(fieldMetas).forEach(fieldName => {
      const fieldMeta = fieldMetas[fieldName] as ClassMeta;
      const fieldValue = entity[fieldName];
      if (fieldValue && !Array.isArray(fieldValue) && !ObjectUtil.isNumberGreaterThanZero(fieldValue)) {
        convertedEntity[fieldName] = entity[fieldMeta.mainIdName];
      }
    });
    console.log('converted Entity to updating: ', convertedEntity);
    return convertedEntity;
  }

  static updateFields(entities: any, fields: string[] | {}, meta: ClassMeta, callBack: (e) => any = null) {
    if (Array.isArray(entities)) {
      if (entities.length > 0 && entities[0][meta.mainIdName] <= 0) return;
      const content = [];
      entities.forEach(entity => {
        content.push(this.getEntityWithFields(meta, entity, fields));
      });
      this.update(content, meta, callBack);
    } else {
      const contentUpdate = this.getEntityWithFields(meta, entities, fields);
      this.update(contentUpdate, meta, callBack);
    }
  }

  static update(entities: any, meta: ClassMeta, callBack: (e) => any = null) {
    console.log('updating', entities);
    if (Array.isArray(entities)) {
      if (entities.length > 0 && entities[0][meta.mainIdName] <= 0) return;
    }
    if (entities[meta.mainIdName] <= 0) return;
    this.update$(entities, meta)
      .subscribe(
        result => {
          console.log("updated", result);
          if (callBack) {
            callBack(result);
          } else {
            this.assignResult(result, meta, entities);
          }
          this.postUpdate(result, meta);
        },
        error => Notifier.httpErrorResponse(error)
      );
  }

  static assignResult(newResult, meta: ClassMeta, originEntities: any) {
    EntityUtil.cache(newResult, meta);
    Xe.fill(newResult, meta);
    Object.keys(originEntities).forEach(key => {
      delete originEntities[key];
    });
    Object.assign(originEntities, newResult);
  }

  static postUpdate(entities: any, meta: ClassMeta) {
  }

  static updateSingle$(entity: any, meta: ClassMeta): Observable<any> {
    return CommonUpdateService.instance.update(this.getPrimitiveValues(entity, meta), meta);
  }

  static refresh(entity, meta: ClassMeta, callback: (result) => any = null) {
    this.refresh$(entity, meta).subscribe(
      arrayResult => {
        if (callback !== null) {
          callback(arrayResult[0]);
        } else {
          EntityUtil.cache(arrayResult, meta);
          Object.keys(entity).forEach(key => delete entity[key]);
          Object.assign(entity, arrayResult[0]);
        }
      },
      httpError => Notifier.httpErrorResponse(httpError)
    );
  }

  private static getEntityWithFields(meta: ClassMeta, entities: any, fields: string[] | {}) {
    const contentUpdate = {};
    contentUpdate[meta.mainIdName] = entities[meta.mainIdName];
    if (Array.isArray(fields)) {
      fields.forEach(fieldName => {
        contentUpdate[fieldName] = entities[fieldName];
      });
    } else {
      Object.keys(fields).forEach(key => {
        contentUpdate[key] = fields[key];
      });
    }
    return contentUpdate;
  }
}
