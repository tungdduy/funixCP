import {UrlConfig} from "./url.config";
import {ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {StorageUtil} from "../util/storage.util";
import {Url} from "./url.declare";

export class UrlBuilder {
  static buildUrlConfig = (parent: UrlConfig, urls: {}) => {
    Object.entries(urls).forEach(([key, value]) => {
      if (key === '__self') return;

      if (value instanceof UrlConfig) {
        UrlBuilder.updateConfig(value, parent, key);
      } else {
        UrlBuilder.updateConfig(value['__self'], parent, key);
        UrlBuilder.buildUrlConfig(value['__self'], value as UrlConfig);
      }
    });
  }

  private static updateConfig(config: UrlConfig, parent: UrlConfig, key: string) {
    config.__parent = parent;
    parent.__children.push(config);
    config.__key = key;
    config.__keyChane = !!parent.__keyChane ? parent.__keyChane + "." + key : key;
    config.__short = key.replace(/_/g, "-").toLowerCase();
    if (config.__authorities.length > 0) {
      const activateProvider = {
        provide: config.__keyChane,
        useValue: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) =>
          config.__authorities.every(auth => StorageUtil.getAuthorities().includes(auth))
      };
      config.__parent.activateProviders.push(activateProvider);
    }
  }

  static start() {
    UrlBuilder.buildUrlConfig(UrlBuilder.root(Url.APP_HOST), Url.app);
    UrlBuilder.buildUrlConfig(UrlBuilder.root(Url.API_HOST), Url.api);
  }

  static root(url: string) {
    const config = new UrlConfig();
    config.__short = url;
    return config;
  }
}
