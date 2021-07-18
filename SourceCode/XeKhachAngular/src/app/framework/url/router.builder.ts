import {Authority} from "../../business/constant/auth.enum";
import {StorageUtil} from "../util/storage.util";
import {UrlConfig} from "./url.config";
import {UrlImport} from "./url.import";

export class RouterBuilder {

  static build(module: object) {
    if (!Object.keys(module).includes("_self")) {
      return;
    }
    const config = module['_self'];
    const userAuthorities: Authority[] = StorageUtil.getAuthorities();
    const routes = {
      path: '', component: RouterBuilder.requireComponent(config)
    };
    const children = [];
    config.children.forEach(child => {
      children.push(RouterBuilder.buildRoutes(child, userAuthorities));
    });

    if (children.length > 0) {
      children.push({path: '', redirectTo: children[0].path, pathMatch: 'full'});
    }

    routes['children'] = children;
    return [routes];
  }

  private static requireComponent(config: UrlConfig) {
    return UrlImport[config.keyChane + "-component"]();
  }

  private static importModule(config: UrlConfig) {
    return UrlImport[config.keyChane + "-module"]();
  }

  private static buildRoutes(config: UrlConfig, userAuthorities: Authority[]) {
    const path = {path: config.short};
    if (config.isModule()) {
      path['loadChildren'] = RouterBuilder.importModule(config);
    } else {
      path['component'] = RouterBuilder.requireComponent(config);

      if (config.authorities.length > 0) {
        path['canActivate'] = [config.keyChane];
      }
    }

    return path;
  }

}
