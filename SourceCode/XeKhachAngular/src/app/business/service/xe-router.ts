import {Injectable} from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class XeRouter {
  private static router;
  constructor(private router: Router) {
    XeRouter.router = router;
  }

  private static go(url: string): void {
    XeRouter.router.navigateByUrl(url).then(r => {

    });
  }

  public static navigate(url: any): void {
    console.log("navigate...");
    if (typeof url === "string") {
      XeRouter.go(url);
    } else if (url.hasOwnProperty("_self")) {
      XeRouter.go(url._self.noHost);
    } else if (url.hasOwnProperty("_full")) {
      XeRouter.go(url.noHost);
    }
  }

}
