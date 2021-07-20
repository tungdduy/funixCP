import {XeRole} from "../../business/constant/xe.role";

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
  private _activateProviders: {}[] = [];
  private _roles: XeRole[] = [];
  public get roles(): XeRole[] {
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

  public setRoles(roles: XeRole[]) {
    this._roles = roles;
    return this;
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

}

