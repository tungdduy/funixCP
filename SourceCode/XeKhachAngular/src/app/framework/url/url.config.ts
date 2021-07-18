import {Authority} from "../../business/constant/auth.enum";
import {ObjectUtil} from "../util/object.util";
import {Role} from "../model/role.model";


export class UrlConfig {
  private _short: string;
  private _parent: UrlConfig;
  private _children: UrlConfig[] = [];
  private _full: string;
  private _noHost: string;
  private _authorities: Authority[] = [];
  private _public: boolean;
  private _root: UrlConfig;
  private _key: string;
  private _keyChane: string;
  private _activateProviders: {}[] = [];

  get full() {
    if (!this._full) {
      this._root = this;
      const urls: string[] = [this._short];
      while (!!this._root._parent) {
        this._root = this._root._parent;
        urls.push(this._root._short);
      }
      this._full = urls.reverse().join("/");
    }
    return this._full;
  }

  get noHost(): string {
    if (!this._noHost) {
      this._noHost = this.full.substring(this._root._short.length + 1);
    }
    return this._noHost;
  }

  public auths(authAndRoles: any[]) {
    if (authAndRoles.length > 0) {
      const auths: Set<Authority> = new Set<Authority>();
      this.fetchAuths(auths, authAndRoles);
      this._authorities = Array.from(auths);
    }
    return this;
  }

  isModule() {
      return this._children.length > 0;
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



  set parent(value: UrlConfig) {
    this._parent = value;
  }

  get parent(): UrlConfig {
    return this._parent;
  }

  get root(): UrlConfig {
    return this._root;
  }

  get key(): string {
    return this._key;
  }

  set key(value: string) {
    this._key = value;
  }

  get short(): string {
    return this._short;
  }

  set short(value: string) {
    this._short = value;
  }

  get keyChane(): string {
    return this._keyChane;
  }

  set keyChane(value: string) {
    this._keyChane = value;
  }
  public get children() {
    return this._children;
  }

  public get authorities() {
    return this._authorities;
  }

  public get activateProviders(): any[] {
    return this._activateProviders;
  }

  public public() {
    this._public = true;
    return this;
  }

  public isPublic() {
    return this._public;
  }


}

