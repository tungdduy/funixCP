import {Directive, OnDestroy} from "@angular/core";

@Directive()
export class XeSubscriber implements OnDestroy {
  subscriptions = [];
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
