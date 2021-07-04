import {Authority} from "../../business/auth.enum";
import {ObjectUtil} from "../util/object.util";
import {Role} from "../auth/role.model";
import {ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {StorageUtil} from "../util/storage.util";
import {NotFoundComponent} from "../../business/pages/demo-pages/miscellaneous/not-found/not-found.component";
import {UrlImport} from "./url.import";


export class UrlConfig {
  __short: string;
  __parent: UrlConfig;
  __children: UrlConfig[] = [];
  private __full: string;
  private __noHost: string;
  private __authorities: Authority[] = [];
  private isPublic: boolean;
  private __root: UrlConfig;
  private __key: string;
  private __keyChane: string;

  get full() {
    if (!this.__full) {
      this.__root = this;
      const urls: string[] = [this.__short];
      while (!!this.__root.__parent) {
        this.__root = this.__root.__parent;
        urls.push(this.__root.__short);
      }
      this.__full = urls.reverse().join("/");
    }
    return this.__full;
  }

  get noHost(): string {
    if (!this.__noHost) {
      this.__noHost = this.full.substring(this.__root.__short.length + 1);
    }
    return this.__noHost;
  }

  static buildUrlConfig = (parent: UrlConfig, urls: {}) => {
    Object.entries(urls).forEach(([key, value]) => {
      if (key === '__self') return;

      if (value instanceof UrlConfig) {
        UrlConfig.updateConfig(value, parent, key);
      } else {
        UrlConfig.updateConfig(value['__self'], parent, key);
        UrlConfig.buildUrlConfig(value['__self'], value as UrlConfig);
      }
    });
  }

  private static updateConfig(config: UrlConfig, parent: UrlConfig, key: string) {
    config.__parent = parent;
    parent.__children.push(config);
    config.__key = key;
    config.__keyChane = parent.__keyChane + "." + key;
    config.__short = key.replace(/_/g, "-").toLowerCase();
  }

  static root(url: string) {
    const config = new UrlConfig();
    config.__short = url;
    return config;
  }

  auths(authAndRoles: any[]) {
    if (authAndRoles.length > 0) {
      const auths: Set<Authority> = new Set<Authority>();
      this.fetchAuths(auths, authAndRoles);
      this.__authorities = Array.from(auths);
    }
    return this;
  }

  public public() {
    this.isPublic = true;
    return this;
  }

  private requireComponent() {
    return UrlImport[this.__keyChane + "-component"];
  }

  private importModule() {
    return UrlImport[this.__keyChane + "-module"];
  }

  private isModule() {
      return this.__children.length > 0;
  }

  public buildConfig() {
    const userAuthorities: Authority[] = StorageUtil.getAuthorities();
    const routes = {
      path: '', component:  this.requireComponent()
    };
    const children = [];
    this.__children.forEach(_child => {
      children.push(_child.buildPath(userAuthorities));
    });

    if (children.length > 0) {
      children.push({path: '', redirectTo: children[0].path, pathMatch: 'full'});
    }

    children.push({path: '**', component: NotFoundComponent});

    routes['children'] = children;
    return [routes];
  }

  private fetchAuths(auths: Set<Authority>, authAndRoles: string[] | any[]): void {
    authAndRoles.forEach(ar => {
      if (ObjectUtil.isString(ar)) {
        auths.add(ar as Authority);
      } else if (ObjectUtil.isObject(ar)) {
        const role = ar as unknown as Role;
        role.authorities.forEach(auth => auths.add(auth));
        this.fetchAuths(auths, role.roles);
      }
    });
  }

  private buildPath = (userAuthorities: Authority[]): {} => {
    const path = {path: this.__short};
    if (this.isModule()) {
      path['loadChildren'] = this.importModule();
    } else {
      path['component'] = this.requireComponent();

      if (this.__authorities.length > 0) {
        path['canActivate'] = [
          (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) =>
            this.__authorities.every(auth => userAuthorities.includes(auth))
        ];
      }

    }

    return path;
  }

}

