import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Url} from "../../framework/url/url.declare";
import {StringUtil} from "../../framework/util/string.util";
import {XeEntity} from "../entities/XeEntity";

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
  oneParamIdValue = (id, className) => {
    const idName = id ? id[StringUtil.classNameToIdName(className)] : "0";
    return idName ? idName : "0";
  }
  commonPath = (className) => {
    const result = Url.API_HOST + "/common-update/" + className;
    console.log(result);
    return result;
  }

  commonMultiPath = (className) => {
    const result = Url.API_HOST + "/common-update/Multi" + className;
    console.log(result);
    return result;
  }

  oneParamIdPath = (id, className) => {
    const result = this.commonPath(className) + "/" + this.oneParamIdValue(id, className);
    console.log(result);
    return result;
  }
  manyParamIdPath = (ids, className) => {
    const result = this.commonPath(className) + "/" + this.manyParamIdValue(ids, className);
    console.log(result);
    return result;
  }

  manyParamIdValue = (ids, className) => {
    const mainIdName = StringUtil.classNameToIdName(className);
    const mainIdValue = this.oneParamIdValue(ids, className);
    const joiner = [];
    joiner.push(mainIdValue);

    const remainIdsSorted = Object.keys(ids).sort((a, b) => a.localeCompare(b))
                                          .filter(s => s !== mainIdName)
                                          .map(keySorted => ids[keySorted])
                                          .map(s => !s ? "0" : s);
    remainIdsSorted.forEach(s => joiner.push(s));
    return joiner.join("/");
  }
  profileImagePath = (className) => this.commonPath(className) + "-profile-image";

  updateProfileImage(formData: FormData, className): Observable<any> {
    return this.http.post<string>(this.profileImagePath(className), formData);
  }
  update<T>(data, className): Observable<T> {
    return this.http.post<T>(this.commonPath(className), data);
  }
  insert<T>(data, className): Observable<T> {
    return this.http.put<T>(this.commonPath(className), data);
  }
  insertMulti<T>(data: any[], className): Observable<T[]> {
    return this.http.put<T[]>(this.commonMultiPath(className), data);
  }
  delete<T>(data, className): Observable<T> {
    return this.http.delete<T>(this.oneParamIdPath(data, className));
  }
  getOne<T>(data, className): Observable<T> {
    return this.http.get<T>(this.oneParamIdPath(data, className));
  }
  public getAll<T>(ids: {}, className: string): Observable<T[]> {
    return this.http.get<T[]>(this.manyParamIdPath(ids, className));
  }
  deleteAll(selected: any[], className: string) {
    const id = StringUtil.lowercaseFirstLetter(className) + "Id";
    const params =  selected.map(s => s[id]).join(",");
    const url = this.commonPath(className) + "/" + params;
    return this.http.delete(url);
  }
}
