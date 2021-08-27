import {Directive, OnDestroy} from "@angular/core";
import {CommonUpdateService} from "../../business/service/common-update.service";
import {Notifier} from "../notify/notify.service";
import {Observable} from "rxjs";
import {ClassMeta, XeEntityClass} from "../../business/entities/XeEntity";
import {AbstractXe} from "./AbstractXe";
import {EntityUtil} from "../util/EntityUtil";
import {ObjectUtil} from "../util/object.util";
import {Meta} from "@angular/platform-browser";

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
          EntityUtil.cache(arrayResult, clazz.meta);
          Object.keys(entity).forEach(key => delete entity[key]);
          Object.assign(entity, arrayResult[0]);
        }
      },
      httpError => Notifier.httpErrorResponse(httpError)
    ));
  }

  refresh$(entity, clazz: XeEntityClass<any>): Observable<any> {
    return CommonUpdateService.instance.getOne<any>(entity, clazz.meta);
  }

  updateMulti$(entities: any[], meta: ClassMeta) {
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
    return CommonUpdateService.instance.updateMulti(prepareData, meta);
  }

  update(entities: any[], clazz: XeEntityClass<any>) {
    this.updateMulti$(entities, clazz.meta)
    .subscribe(
      returnedArray => {
        EntityUtil.cache(returnedArray, clazz.meta);
      }
    );
  }
  updateSingle$(entity: any, meta: ClassMeta): Observable<any> {
    return CommonUpdateService.instance.update(entity, meta);
  }
}
