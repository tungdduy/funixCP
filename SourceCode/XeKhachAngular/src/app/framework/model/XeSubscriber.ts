import {Directive, OnDestroy} from "@angular/core";
import {CommonUpdateService} from "../../business/service/common-update.service";
import {Notifier} from "../notify/notify.service";
import {Observable} from "rxjs";
import {ClassMeta, XeEntityClass} from "../../business/entities/XeEntity";
import {AbstractXe} from "./AbstractXe";
import {EntityUtil} from "../util/EntityUtil";
import {ObjectUtil} from "../util/object.util";

@Directive()
export class XeSubscriber extends AbstractXe implements OnDestroy {
  subscriptions = [];

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  refresh(entity, clazz: XeEntityClass<any>, callback: (result) => any = null) {
    this.subscriptions.push(this.refresh$(entity, clazz).subscribe(
      arrayResult => {
        if (callback !== null) {
          callback(arrayResult[0]);
        } else {
          Object.keys(entity).forEach(key => delete entity[key]);
          Object.assign(entity, arrayResult[0]);
          EntityUtil.cache(entity, clazz.meta);
        }
      },
      httpError => Notifier.httpErrorResponse(httpError)
    ));
  }

  refresh$(entity, clazz: XeEntityClass<any>): Observable<any> {
    return CommonUpdateService.instance.getOne<any>(entity, clazz.meta);
  }

  update(entities: any[], clazz: XeEntityClass<any>) {
    const prepareData = [];
    entities.forEach(entity => {
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
      });
      prepareData.push(convertedEntity);
    });
    CommonUpdateService.instance.updateMulti(prepareData, clazz.meta).subscribe(
      returnedArray => EntityUtil.cacheMulti(returnedArray, clazz.meta, {
        filterSingle: (filterCondition) => {
          entities.forEach(entity => {
            if (entity[clazz.meta.mainIdName] === filterCondition[clazz.meta.mainIdName]) {
              ObjectUtil.eraserAndDeepCopyForRestore(filterCondition, entity);
            }
          });
          return true;
        }
      })
    );
  }
  updateSingle$(entity: any, meta: ClassMeta): Observable<any> {
    return CommonUpdateService.instance.update(entity, meta);
  }
}
