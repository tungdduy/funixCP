import {AuthUtil} from "../auth/auth.util";
import {XeRouter} from "../../business/service/xe-router";
import {Role} from "../../business/xe.role";

export class UrlConfig {
  private _short: string;
  private _parent: UrlConfig;
  private _children: UrlConfig[] = [];
  private _full: string;
  private _noHost: string;
  private _public: boolean;
  private _root: UrlConfig;
  private _key: string;
  private _keyChane: string;
  _activateProviders: {}[] = [];
  private _roles: Role[] = [];
  private _flatRoles: Role[] = [];
  public get roles(): Role[] {
    return this._roles;
  }

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
      this._noHost = '/' + this.full.substring(this._root._short.length + 1);
    }
    return this._noHost;
  }


  public hasPermission() {
    return AuthUtil.instance.isAllow(this.flatRoles);
  }
  public forbidden() {
    return !this.hasPermission();
  }

  isModule() {
      return this._children.length > 0;
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

  public go() {
    XeRouter.navigate(this.noHost);
  }

  public setRoles(roles: Role[]) {
    this._roles = roles;
    return this;
  }

  public get flatRoles() {
    if (this._flatRoles.length === 0) {
      this.buildFlatRoles();
    }
    return this._flatRoles;
  }

  buildFlatRoles() {
    let iter: UrlConfig = this;
    this._flatRoles = this._roles;
    while (iter.parent) {
      iter = iter.parent;
      this._flatRoles = this._flatRoles.concat(iter.roles);
    }
  }
}

