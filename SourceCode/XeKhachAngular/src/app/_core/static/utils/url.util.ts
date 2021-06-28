import {UrlPattern} from "../model/url-pattern";
import {XeUrl} from "../url.declare";
import {Type} from "@angular/core";
import {NotFoundComponent} from "../../../demo-pages/miscellaneous/not-found/not-found.component";

export class UrlUtil {
  static buildUrl = (parent: UrlPattern, urls: any, keys: string[], type: string) => {
    keys.forEach(key => {
      if (key === 'index') return;
      const newUrls = urls[key];
      if (typeof newUrls === 'string') {
        parent[key] = new UrlPattern(parent, key, type, newUrls);

      } else if (typeof newUrls === 'object') {
        parent[key] = new UrlPattern(parent, key, type, newUrls.index);
        UrlUtil.buildUrl(parent[key], newUrls, Object.keys(newUrls), type);
      }
    });
  }

  static isPublicApi = (url: string) => {
    return XeUrl.publicApi.includes(url);
  }

  static startBuildUrl = () => {
    const api = new UrlPattern(null, 'HOST', 'api', XeUrl.short.api.HOST );
    UrlUtil.buildUrl(api, XeUrl.short.api, Object.keys(XeUrl.short.api), 'api');

    const app = new UrlPattern(null, 'HOST', 'app', XeUrl.short.app.HOST, );
    UrlUtil.buildUrl(app, XeUrl.short.app, Object.keys(XeUrl.short.app), 'app');
  }

  static buildShortPath(types: Type<any>[]) {
    const result = [];
    types.forEach(type => {
      const url = type.name.slice(0, -9).split(/(?=[A-Z])/).join('-').toLowerCase();
      result.push({
        path: url, component: type
      });
    });
    if (result.length > 0) {
      result.push({path: '', redirectTo: result[0].path, pathMatch: 'full'});
      result.push({path: '**', component: NotFoundComponent});
    }
    return result;
  }
}
