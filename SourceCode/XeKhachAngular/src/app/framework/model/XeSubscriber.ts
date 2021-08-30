import {Directive, OnDestroy} from "@angular/core";
import {AbstractXe} from "./AbstractXe";

@Directive()
export class XeSubscriber extends AbstractXe implements OnDestroy {
  subscriptions = [];
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
