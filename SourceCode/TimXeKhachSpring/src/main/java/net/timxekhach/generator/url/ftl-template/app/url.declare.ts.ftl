import {environment} from "../../../environments/environment";
import {Authority} from "../../business/auth.enum";
import {XeRole} from "../../business/xe.role";
import {UrlConfig} from "./url.config";

export const config = () => {
  return new UrlConfig();
};
const r = XeRole;
const a = Authority;

export const Url = {
  publicApi: [],
  API_HOST: environment.apiHost,
  APP_HOST: environment.appHost,
  getPublicApi: (apiUrls: UrlConfig[]) => {
    apiUrls.forEach(apiUrl => {
      if (apiUrl instanceof UrlConfig) {
        if (apiUrl.isPublic()) {
          Url.publicApi.push(apiUrl.full);
        }
      } else {
        Url.getPublicApi(Object.values(apiUrl));
      }
    });
  },
  isPublicApi: (url: string) => {
    if (Url.publicApi.length === 0) {
      Url.getPublicApi(Object.values(Url.api));
    }
    return Url.publicApi.includes(url);
  },
  DEFAULT_URL_AFTER_LOGIN: (): string => {
    return Url.app.CHECK_IN.FORGOT_PASSWORD.full;
  },
  api: {
<#assign count=4 step=2>
    <#macro tree apiUrls>
        <#list apiUrls as url>
            <#lt>${""?left_pad(count)}${url.key}: <#if url.children?size == 0>${url.config}<#else>{
            <#lt>${""?left_pad(count+step)}__self: ${url.config},
            <#assign count = count+step>
            <#lt><@tree url.children />
            <#assign count = count-step>
            <#lt>${""?left_pad(count)}}</#if>,
        </#list>
    </#macro>
    <@tree apiUrls />
  },
  app: {
<#assign count=4>
    <#macro tree appUrls>
        <#list appUrls as url>
            <#lt>${""?left_pad(count)}${url.key}: <#if url.children?size == 0>${url.config}<#else>{
            <#lt>${""?left_pad(count+step)}__self: ${url.config},
            <#assign count = count+step>
            <#lt><@tree url.children />
            <#assign count = count-step>
            <#lt>${""?left_pad(count)}}</#if>,
        </#list>
    </#macro>
    <@tree appUrls />
  },

};


