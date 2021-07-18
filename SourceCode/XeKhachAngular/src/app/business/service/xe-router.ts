import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {UrlConfig} from "../../framework/url/url.config";
import {ObjectUtil} from "../../framework/util/object.util";

@Injectable({
  providedIn: 'root'
})
export class XeRouter {
  constructor(private router: Router) {
  }

  public navigate(url: any): void {
    if (typeof url === "string") {
      this.router.navigateByUrl(url).then(r => {});
    } else if (url.hasOwnProperty("_self")) {
      this.router.navigateByUrl(url._self.full).then(r => {});
    } else if (url.hasOwnProperty("_full")) {
      this.router.navigateByUrl(url.full).then(r => {});
    }
  }

}
