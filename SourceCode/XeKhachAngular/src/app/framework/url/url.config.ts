import {Authority} from "../../business/auth.enum";
import {ObjectUtil} from "../util/object.util";
import {Role} from "../auth/role.model";
import {ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {StorageUtil} from "../util/storage.util";
import {Type} from "@angular/core";
import {NotFoundComponent} from "../../business/pages/demo-pages/miscellaneous/not-found/not-found.component";
import {StringUtil} from "../util/string.util";


export class UrlConfig {
  __short: string;
  __parent: UrlConfig;
  __children: UrlConfig[] = [];
  private __full: string;
  private __noHost: string;
  private __authorities: Authority[] = [];
  private isPublic: boolean;
  private __root: UrlConfig;

  get full() {
    if (!!this.__full) {
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

  private getPath = () => {
    const rootImportPagesPath = '../../business/pages/';
    let p = this.__parent;
    let root = "";
    while (!!p) {
      if (!!p.__parent) {
        root = `${p.__short}/${root}`;
      }
      p = p.__parent;
    }
    // return `.`;
    return `${rootImportPagesPath}${root}${this.__short}`.toString();
    // return '../../business/pages/check-in/check-in.component';
  }

  private uurl = '../../business/pages/check-in/check-in.component';


  private importComponent() {
    // import(this.uurl).then(c => this.setComponent(c[this.componentName]));
    // import(this.uurl).then(c => this.setComponent(c[this.componentName]));
    // import(this.getComponentPath()).then(c => this.setComponent(c[this.componentName]));
  }

  private importModule() {
    return import(this.getModulePath()).then(m => m[this.moduleName]);
  }

  private getComponentPath() {
    return '../../business/pages/check-in/check-in.component';
    // return `${this.getPath()}/${this.__short}.component`;
  }

  private getModulePath() {
    return `${this.getPath()}/${this.__short}.module`;
  }

  private isModule() {
      return this.__children.length > 0;
  }

  public buildConfig() {
    const userAuthorities: Authority[] = StorageUtil.getAuthorities();
    this.importComponent();
    const routes = {
      path: '', component:  this._component
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

  private _component: Type<any>;

  get componentName(): string {
    return StringUtil.urlToCapitalLizeEachWord(`${this.__short}-component`);
  }

  get moduleName(): string {
    return StringUtil.urlToCapitalLizeEachWord(`${this.__short}-module`);
  }

  private setComponent(type: any) {
    this._component = type;
}

  private buildPath = (userAuthorities: Authority[]): {} => {
    const path = {path: this.__short};
    if (this.isModule()) {
      path['loadChildren'] = this.importModule();
    } else {
      this.importComponent();
      path['component'] = this._component;

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

