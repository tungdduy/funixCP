import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Url} from "../../framework/url/url.declare";
import {XeEntity, XeEntityClass} from "../entities/XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {EntityUtil} from "../../framework/util/entity.util";

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

  oneParamIdValue = (entity, clazz: XeEntityClass<any>) => {
    const idName = entity ? entity[clazz.mainIdName] : "0";
    return idName ? idName : "0";
  }
  commonPath = (clazz: XeEntityClass<any>) => {
    const result = Url.API_HOST + "/common-update/" + clazz.className;
    console.log(result);
    return result;
  }

  commonMultiPath = (clazz: XeEntityClass<any>) => {
    const result = Url.API_HOST + "/common-update/Multi" + clazz.className;
    console.log(result);
    return result;
  }

  oneParamIdPath = (entity, clazz: XeEntityClass<any>) => {
    const result = this.commonPath(clazz) + "/" + this.oneParamIdValue(entity, clazz);
    console.log(result);
    return result;
  }
  manyParamIdPath = (entity, clazz: XeEntityClass<any>) => {
    const result = this.commonPath(clazz) + "/" + this.manyParamIdValue(entity, clazz);
    console.log(result);
    return result;
  }
  manyParamIdValue = (entity, clazz: XeEntityClass<any>) => {
    let mainIdValue = entity[clazz.mainIdName];
    if (!mainIdValue) mainIdValue = "0";
    const otherPksSorted = clazz.otherMainIdNames
      .sort((a, b) => a.localeCompare(b))
      .map(idName => entity[idName]).map(s => !s ? "0" : s);
    otherPksSorted.unshift(mainIdValue);
    return otherPksSorted.join("/");
  }

  profileImagePath = (clazz: XeEntityClass<any>) => this.commonPath(clazz) + "-profile-image";

  updateProfileImage(formData: FormData, clazz: XeEntityClass<any>): Observable<any> {
    return this.http.post<string>(this.profileImagePath(clazz), formData);
  }

  update<T extends XeEntity>(data, clazz: XeEntityClass<T>): Observable<T> {
    return this.http.post<T>(this.commonPath(clazz), data);
  }
  updateMulti<T extends XeEntity>(data, clazz: XeEntityClass<T>): Observable<T> {
    return this.http.post<T>(this.commonMultiPath(clazz), data);
  }

  insert<T extends XeEntity>(entity: T, clazz: XeEntityClass<T>): Observable<T> {
    return this.http.put<T>(this.commonPath(clazz), entity);
  }

  insertMulti<T extends XeEntity>(data: any[], clazz: XeEntityClass<T>): Observable<T[]> {
    return this.http.put<T[]>(this.commonMultiPath(clazz), data);
  }

  delete<T extends XeEntity>(data, clazz: XeEntityClass<T>): Observable<T> {
    return this.http.delete<T>(this.oneParamIdPath(data, clazz));
  }

  getOne<T extends XeEntity>(data, clazz: XeEntityClass<T>): Observable<T> {
    return this.http.get<T>(this.oneParamIdPath(data, clazz));
  }

  public findByEntityIdentifier<T extends XeEntity>(identifier: EntityIdentifier<T>): Observable<T[]> {
    const bareIdNames = {};
    identifier.idFields().forEach(idField => {
      const bareIdName = idField.name.substring(idField.name.lastIndexOf(".") + 1);
      if (idField.value) bareIdNames[bareIdName] = idField.value;
    });
    let mainId = bareIdNames[identifier.clazz.mainIdName];
    if (!mainId) mainId = "0";
    const otherIds = identifier.clazz.otherMainIdNames
      .sort((a, b) => a.localeCompare(b))
      .map(pk => bareIdNames[pk])
      .map(pkValue => !pkValue ? "0" : pkValue);
    otherIds.unshift(mainId);
    const url = this.commonPath(identifier.clazz) + "/" + otherIds.join("/");
    return this.http.get<T[]>(url);
  }

  public findAll<T extends XeEntity>(clazz: XeEntityClass<T>): Observable<T[]> {
    const findAllZeroIdPath = this.commonPath(clazz) + "/0" + "/0".repeat(clazz.otherMainIdNames.length);
    return this.http.get<T[]>(findAllZeroIdPath);
  }

  deleteAll(selected: any[], clazz: XeEntityClass<any>) {
    const params = selected.map(s => s[clazz.mainIdName]).join(",");
    const url = this.commonPath(clazz) + "/" + params;
    return this.http.delete(url);
  }
}
