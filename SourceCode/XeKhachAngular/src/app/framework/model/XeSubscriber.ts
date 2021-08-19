import {Directive, OnDestroy} from "@angular/core";
import {CommonUpdateService} from "../../business/service/common-update.service";
import {ObjectUtil} from "../util/object.util";
import {Notifier} from "../notify/notify.service";
import {Observable} from "rxjs";
import {XeEntityClass} from "../../business/entities/XeEntity";
import {EntityUtil} from "../util/entity.util";
import {AbstractXe} from "./AbstractXe";

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
          EntityUtil.cachePk(clazz, [entity]);
        }
      },
      httpError => Notifier.httpErrorResponse(httpError)
    ));
  }

  refresh$(entity, clazz: XeEntityClass<any>): Observable<any> {
    return CommonUpdateService.instance.getOne<any>(entity, clazz);
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
    CommonUpdateService.instance.updateMulti(prepareData, clazz).subscribe(
      returnedArray => {
        EntityUtil.cachePk(clazz, returnedArray, returned => {
          entities.forEach(entity => {
            if (entity[clazz.mainIdName] === returned[clazz.mainIdName]) {
              ObjectUtil.eraserAndDeepCopyForRestore(returned, entity);
            }
          });
          return true;
        });
      }
    );
  }
}
