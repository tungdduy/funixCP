import {UrlConfig} from "./url.config";
import {UrlImport} from "./url.import";
import {AuthUtil} from "../auth/auth.util";
import {Role} from "../../business/xe.role";

export class RouterBuilder {

  static build(module: object) {
    if (!Object.keys(module).includes("_self")) {
      return;
    }
    const config: UrlConfig = module['_self'];
    const roles: Role[] = AuthUtil.instance.roles;
    const routes = {
      path: '', component: RouterBuilder.requireComponent(config)
    };
    const children = [];
    config.children.forEach(child => {
      children.push(RouterBuilder.buildRoutes(child, roles));
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

  private static buildRoutes(config: UrlConfig, roles: Role[]) {
    const path = {path: config.short};
    if (config.isModule()) {
      path['loadChildren'] = RouterBuilder.importModule(config);
    } else {
      path['component'] = RouterBuilder.requireComponent(config);

      if (config.roles.length > 0) {
        path['canActivate'] = [config.keyChane];
      }
    }

    return path;
  }

}
