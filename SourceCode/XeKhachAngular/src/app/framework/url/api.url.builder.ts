import {Api} from "./api.url";
import {ObjectUtil} from "../util/object.util";

export class ApiUrlBuilder {
  static start = () => {
    ApiUrlBuilder.buildApiUrl(Api.HOST.concat("/"), Api.url);
  }

  static buildApiUrl(parent: string, urls: any) {
    Object.entries(urls).forEach(([key, isPublic]) => {
      if (key === '__self') {
        return;
      }
      const url = parent.concat(key.toLowerCase().replace(/_/g, "-"));
      urls[key] = url;
      if (isPublic === 'public') {
        Api.publicUrls.push(url);
      } else if (ObjectUtil.isObject(isPublic)) { // it's url, can build more
        ApiUrlBuilder.buildApiUrl(url.concat("/"), isPublic);
      }
    });
  }
}
