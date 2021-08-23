import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Url} from "../../framework/url/url.declare";
import {ClassMeta, XeEntity} from "../entities/XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {Location} from "../entities/Location";

@Injectable({
  providedIn: 'root'
})
export class CommonUpdateService {
  static _instance: CommonUpdateService;
  static get instance() {
    return CommonUpdateService._instance;
  }

  constructor(private http: HttpClient) {
    CommonUpdateService._instance = this;
  }

  oneParamIdValue = (entity, meta: ClassMeta) => {
    const idName = entity ? entity[meta.mainIdName] : "0";
    return idName ? idName : "0";
  }
  commonPath = (meta: ClassMeta) => Url.API_HOST + "/common-update/" + meta.capName;
  commonMultiPath = (meta: ClassMeta) => Url.API_HOST + "/common-update/Multi" + meta.capName;

  oneParamIdPath = (entity, meta: ClassMeta) => {
    const result = this.commonPath(meta) + "/" + this.oneParamIdValue(entity, meta);
    console.log(result);
    return result;
  }
  manyParamIdPath = (entity, meta: ClassMeta) => this.commonPath(meta) + "/" + this.manyParamIdValue(entity, meta);
  manyParamIdValue = (entity, meta: ClassMeta) => {
    let mainIdValue = entity[meta.mainIdName];
    if (!mainIdValue) mainIdValue = "0";
    const otherPksSorted = meta.pkMetas().map(pkMeta => pkMeta.mainIdName)
      .sort((a, b) => a.localeCompare(b))
      .map(idName => entity[idName]).map(s => !s ? "0" : s);
    otherPksSorted.unshift(mainIdValue);
    return otherPksSorted.join("/");
  }

  profileImagePath = (meta: ClassMeta) => this.commonPath(meta) + "-profile-image";

  updateProfileImage(formData: FormData, meta: ClassMeta): Observable<any> {
    return this.http.post<string>(this.profileImagePath(meta), formData);
  }

  update<T extends XeEntity>(data, meta: ClassMeta): Observable<T> {
    return this.http.post<T>(this.commonPath(meta), data);
  }
  updateMulti<T extends XeEntity>(data, meta: ClassMeta): Observable<T[]> {
    return this.http.post<T[]>(this.commonMultiPath(meta), data);
  }

  insert<T extends XeEntity>(entity: T, meta: ClassMeta): Observable<T> {
    return this.http.put<T>(this.commonPath(meta), entity);
  }

  insertMulti<T extends XeEntity>(data: any[], meta: ClassMeta): Observable<T[]> {
    return this.http.put<T[]>(this.commonMultiPath(meta), data);
  }

  delete<T extends XeEntity>(data, meta: ClassMeta): Observable<T> {
    return this.http.delete<T>(this.oneParamIdPath(data, meta));
  }

  getOne<T extends XeEntity>(data, meta: ClassMeta): Observable<T> {
    return this.http.get<T>(this.oneParamIdPath(data, meta));
  }

  public findByEntityIdentifier<T extends XeEntity>(identifier: EntityIdentifier<T>): Observable<T[]> {
    const bareIdNames = {};
    const allIds = EntityUtil.getAllPossibleId(identifier.entity, identifier);
    Object.keys(allIds).forEach(idChain => {
      if (!idChain.includes(".")) {
        bareIdNames[idChain] = allIds[idChain];
      }
    });
    let mainId = bareIdNames[identifier.clazz.meta.mainIdName];
    if (!mainId) mainId = "0";
    const otherIds = identifier.clazz.meta.pkMetas().map(pkMeta => pkMeta.mainIdName)
      .sort((a, b) => a.localeCompare(b))
      .map(pk => bareIdNames[pk])
      .map(pkValue => !pkValue ? "0" : pkValue);
    otherIds.unshift(mainId);
    const url = this.commonPath(identifier.clazz.meta) + "/" + otherIds.join("/");
    console.log(url);
    return this.http.get<T[]>(url);
  }

  public findAll<T extends XeEntity>(meta: ClassMeta): Observable<T[]> {
    const findAllZeroIdPath = this.commonPath(meta) + "/0" + "/0".repeat(meta.pkMetas().length);
    return this.http.get<T[]>(findAllZeroIdPath);
  }

  deleteAll(selected: any[], meta: ClassMeta) {
    const params = selected.map(s => s[meta.mainIdName]).join(",");
    const url = this.commonPath(meta) + "/" + params;
    return this.http.delete(url);
  }

  searchLocation(searchTerm: string): Observable<Location[]> {
    return this.http.get<Location[]>(Url.API_HOST + "/trip/searchLocation/" + searchTerm);
  }
}
