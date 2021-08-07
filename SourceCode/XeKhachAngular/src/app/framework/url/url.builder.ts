import {UrlConfig} from "./url.config";
import {Url} from "./url.declare";
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
    if (config.flatRoles.length > 0) {
      const activateProvider = {
        provide: config.keyChane,
        useValue: () => {
           if (!AuthUtil.instance.isAllow(config.flatRoles)) {
             Url.app.CHECK_IN.LOGIN.go();
             return false;
           }
           return true;
        }

      };
      config.parent._activateProviders.push(activateProvider);
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
