import {Authority} from "../../business/constant/auth.enum";
import {StorageUtil} from "../util/storage.util";
import {NotFoundComponent} from "../../business/pages/demo-pages/miscellaneous/not-found/not-found.component";
import {UrlConfig} from "./url.config";
import {UrlImport} from "./url.import";

export class RouterBuilder {

  static build(module: object) {
    if (!Object.keys(module).includes("__self")) {
      return;
    }
    const config = module['__self'];
    const userAuthorities: Authority[] = StorageUtil.getAuthorities();
    const routes = {
      path: '', component:  RouterBuilder.requireComponent(config)
    };
    const children = [];
    config.__children.forEach(_child => {
      children.push(RouterBuilder.buildRoutes(_child, userAuthorities));
    });

    if (children.length > 0) {
      children.push({path: '', redirectTo: children[0].path, pathMatch: 'full'});
    }

    children.push({path: '**', component: NotFoundComponent});

    routes['children'] = children;
    return [routes];
  }

  private static requireComponent(config: UrlConfig) {
    return UrlImport[config.__keyChane + "-component"]();
  }

  private static importModule(config: UrlConfig) {
    return UrlImport[config.__keyChane + "-module"]();
  }

  private static buildRoutes(config: UrlConfig, userAuthorities: Authority[]) {
    const path = {path: config.__short};
    if (config.isModule()) {
      path['loadChildren'] = RouterBuilder.importModule(config);
    } else {
      path['component'] = RouterBuilder.requireComponent(config);

      if (config.__authorities.length > 0) {
        path['canActivate'] = [config.__keyChane];
      }
    }

    return path;
  }

}
