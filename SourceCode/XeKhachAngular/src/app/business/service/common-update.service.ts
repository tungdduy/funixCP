import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Url} from "../../framework/url/url.declare";
import {StringUtil} from "../../framework/util/string.util";

@Injectable({
  providedIn: 'root'
})
export class CommonUpdateService {
  constructor(private http: HttpClient) {}

  commonPath = (className) => Url.API_HOST + "/common-update/" + className;
  paramIdPath = (data, className) => this.commonPath(className) + "/" + this.getParamValue(data, className);
  getParamValue = (data, className) => !data ? '0' : data[StringUtil.lowercaseFirstLetter(className) + "Id"];
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
  delete<T>(data, className): Observable<T> {
    return this.http.delete<T>(this.paramIdPath(data, className));
  }
  getOne<T>(data, className): Observable<T> {
    return this.http.get<T>(this.paramIdPath(data, className));
  }
  public getAll<T>(data, className): Observable<T[]> {
    return this.http.get<T[]>(this.paramIdPath(data, className));
  }

  deleteAll(selected: any[], className: string) {
    const id = StringUtil.lowercaseFirstLetter(className) + "Id";
    const params =  selected.map(s => s[id]).join(",");
    const url = this.commonPath(className) + "/" + params;
    return this.http.delete(url);
  }
}
