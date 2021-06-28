import {XeUrl} from "../url.declare";

export class UrlPattern {
  url: string;
  parent: UrlPattern;
  keyChain: string[];
  key: string;

  constructor(parent: UrlPattern, key: string, type: string, url: string | string[]) {
    this.parent = parent;
    if (typeof url === 'string') {
      this.url = url;
      this.key = key;
      if (parent !== null) {
        this.keyChain = Object.assign({}, this.parent.keyChain);
        this.keyChain.push(this.key);
      } else {
        this.keyChain = [];
      }

      let noHostProp = XeUrl.noHost[type];
      let fullProp = XeUrl.full[type];
      this.keyChain.forEach(k => {
        noHostProp = noHostProp[k];
        fullProp = fullProp[k];
      });
      noHostProp = this.noHost;
      fullProp = this.full;
    }
  }

  private rootParent: UrlPattern;
  private _full: string | undefined;

  get full() {
    if (!this._full) {
      this.rootParent = this;
      const urls: string[] = [this.url];
      while (this.rootParent.parent !== null) {
        this.rootParent = this.rootParent.parent;
        urls.push(this.rootParent.url);
      }
      this._full = urls.reverse().join("/");
    }
    return this._full;
  }

  get short() {
    return this.url;
  }

  private _host: string | undefined;
  get noHost() {
    if (!this._host) {
      this._host = this.full.replace(this.rootParent.url, "");
    }
    return this._host;
  }
}
