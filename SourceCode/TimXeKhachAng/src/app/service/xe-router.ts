import {Compiler, Injectable, Injector, NgModuleFactoryLoader, Type} from '@angular/core';
import {ChildrenOutletContexts, Router, Routes, UrlSerializer} from "@angular/router";
import {Location} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class XeRouter {
  constructor(private router: Router) {
  }

  public navigateNow(url: string): void {
    this.router.navigateByUrl(url).then(r => {});
  }
}
