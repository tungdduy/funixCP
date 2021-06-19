import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class XeRouter extends Router {
  public navigateNow(url: string): void {
    super.navigateByUrl(url).then(()=> {});
  }
}
