import {UrlConfig} from "./url.config";
import {ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {Url} from "./url.declare";
import {XeRouter} from "../../business/service/xe-router";
import {AuthUtil} from "../auth/auth.util";

export class UrlBuilder {
  static buildUrlConfig = (parent: UrlConfig, urls: {}) => {
    Object.entries(urls).forEach(([key, value]) => {
      if (key === '_self') return;

      if (value instanceof UrlConfig) {
        UrlBuilder.updateConfig(value, parent, key);
      } else {
        UrlBuilder.updateConfig(value['_self'], parent, key);
        UrlBuilder.buildUrlConfig(value['_self'], value as UrlConfig);
      }
    });
  }

  private static updateConfig(config: UrlConfig, parent: UrlConfig, key: string) {
    config.parent = parent;
    parent.children.push(config);
    config.key = key;
    config.keyChane = !!parent.keyChane ? parent.keyChane + "." + key : key;
    config.short = key.replace(/_/g, "-").toLowerCase();
    if (config.roles.length > 0) {
      const activateProvider = {
        provide: config.keyChane,
        useValue: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
           if (!AuthUtil.instance.isAllow(config.roles)) {
             XeRouter.navigate(Url.app.CHECK_IN.LOGIN.noHost);
             return false;
           }
           return true;
        }

      };
      config.parent.activateProviders.push(activateProvider);
    }
  }

  static start() {
    UrlBuilder.buildUrlConfig(UrlBuilder.root(Url.APP_HOST), Url.app);
    UrlBuilder.buildUrlConfig(UrlBuilder.root(Url.API_HOST), Url.api);
  }

  static root(url: string) {
    const config = new UrlConfig();
    config.short = url;
    return config;
  }
}
