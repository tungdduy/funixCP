import {Injectable} from '@angular/core';
import {Router} from "@angular/router";

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
