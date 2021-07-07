import {Authority} from "../../business/auth.enum";
import {ObjectUtil} from "../util/object.util";
import {Role} from "../model/role.model";
import {renderConstantPool} from "@angular/compiler-cli/ngcc/src/rendering/renderer";


export class UrlConfig {
  __short: string;
  __parent: UrlConfig;
  __children: UrlConfig[] = [];
  private __full: string;
  private __noHost: string;
  __authorities: Authority[] = [];
  private __public: boolean;
  private __root: UrlConfig;
  __key: string;
  __keyChane: string;
  private __activateProviders: {}[] = [];

  public get activateProviders(): any[] {
    return this.__activateProviders;
  }

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

  public auths(authAndRoles: any[]) {
    if (authAndRoles.length > 0) {
      const auths: Set<Authority> = new Set<Authority>();
      this.fetchAuths(auths, authAndRoles);
      this.__authorities = Array.from(auths);
    }
    return this;
  }

  public public() {
    this.__public = true;
    return this;
  }

  public isPublic() {
    return this.__public;
  }

  isModule() {
      return this.__children.length > 0;
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


}

