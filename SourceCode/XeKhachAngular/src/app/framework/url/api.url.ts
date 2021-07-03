import {Url} from "../../business/url.declare";
export const Api = {
  url: Object.assign({}, Url.api),
  HOST: Url.API_HOST,
  publicUrls: [],
  isPublicUrl: (url: string) => {
    return Api.publicUrls.includes(url);
  }
};
