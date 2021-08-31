import {AuthUtil} from "../auth/auth.util";
import {XeRouter} from "../../business/service/xe-router";
import {Role} from "../../business/xe.role";

export class UrlConfig {
  private _public: boolean;

  private _short: string;

  get short(): string {
    return this._short;
  }

  set short(value: string) {
    this._short = value;
  }

  private _parent: UrlConfig;

  get parent(): UrlConfig {
    return this._parent;
  }

  set parent(value: UrlConfig) {
    this._parent = value;
  }

  private _children: UrlConfig[] = [];

  public get children() {
    return this._children;
  }

  private _full: string;

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

  private _noHost: string;

  get noHost(): string {
    if (!this._noHost) {
      this._noHost = '/' + this.full.substring(this._root._short.length + 1);
    }
    return this._noHost;
  }

  private _root: UrlConfig;

  get root(): UrlConfig {
    return this._root;
  }

  private _key: string;

  get key(): string {
    return this._key;
  }

  set key(value: string) {
    this._key = value;
  }

  private _keyChane: string;

  get keyChane(): string {
    return this._keyChane;
  }

  set keyChane(value: string) {
    this._keyChane = value;
  }

  _activateProviders: {}[] = [];

  public get activateProviders(): any[] {
    return this._activateProviders;
  }

  private _roles: Role[] = [];

  public get roles(): Role[] {
    return this._roles;
  }

  private _flatRoles: Role[] = [];

  public get flatRoles() {
    if (this._flatRoles.length === 0) {
      this.buildFlatRoles();
    }
    return this._flatRoles;
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

  buildFlatRoles() {
    let iter: UrlConfig = this;
    this._flatRoles = this._roles;
    while (iter.parent) {
      iter = iter.parent;
      this._flatRoles = this._flatRoles.concat(iter.roles);
    }
  }
}

