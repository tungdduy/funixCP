import {Directive, OnDestroy} from "@angular/core";
import {StringUtil} from "../util/string.util";
import {CommonUpdateService} from "../../business/service/common-update.service";
import {ObjectUtil} from "../util/object.util";
import {Notifier} from "../notify/notify.service";

@Directive()
export class XeSubscriber implements OnDestroy {
  subscriptions = [];
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  refresh(entity, className: string) {
    const idKey = StringUtil.lowercaseFirstLetter(className) + "Id";
    this.subscriptions.push(CommonUpdateService.instance.getOne<any>(entity[idKey], className).subscribe(
      arrayResult => ObjectUtil.recursiveAssignValueOnly(arrayResult[0], entity),
      httpError => Notifier.httpErrorResponse(httpError)
    ));
  }
}
