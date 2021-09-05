import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Url} from "../../framework/url/url.declare";
import {ClassMeta, XeEntity} from "../entities/XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {EntityUtil} from "../../framework/util/EntityUtil";
import {Location} from "../entities/Location";
import {BussSchedule} from "../entities/BussSchedule";
import {XeDatePipe} from "../../framework/components/pipes/date.pipe";
import {BussScheduleCriteria} from "../pages/admin/my-trip/my-trip.component";
import {StringUtil} from "../../framework/util/string.util";
import {Trip} from "../entities/Trip";
import {TripUser} from "../entities/TripUser";
import {User} from "../entities/User";
import {AuthUtil} from "../../framework/auth/auth.util";

@Injectable({
  providedIn: 'root'
})
export class CommonUpdateService {
  private static _instance: CommonUpdateService;
  static get instance() {
    return CommonUpdateService._instance;
  }

  constructor(private http: HttpClient) {
    CommonUpdateService._instance = this;
  }

  oneParamIdValue = (entity, meta: ClassMeta) => {
    const pkIds = meta.pkMetas().map(pk => "0");
    const idName = entity ? entity[meta.mainIdName] : "0";
    const mainId = idName ? [idName] : ["0"];
    return mainId.concat(pkIds).join("/");
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
    console.log(this.commonPath(meta));
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
    return this.http.get<Location[]>(Url.API_HOST + "/trip/search-location/" + searchTerm);
  }

  findScheduledLocations(criteria: BussScheduleCriteria): Observable<Location[]> {
    if (StringUtil.isBlank(criteria.inputText)) return of([]);
    const fromId = criteria.inputName === 'locationFrom' ? 0 : criteria.locationFrom?.locationId || 0;
    const toId = criteria.inputName === 'locationTo' ? 0 : criteria.locationTo?.locationId || 0;
    const mark0ForStart = criteria.inputName === 'locationFrom' ? 0 : 1;
    const searchLocationUrl = Url.API_HOST + "/trip/find-scheduled-locations/" + mark0ForStart + "/" + fromId + "/" + toId + "/" + criteria.inputText;
    console.log("search location url:", searchLocationUrl);
    return CommonUpdateService.instance.http.get<Location[]>(searchLocationUrl);
  }

  findBussSchedules(data: BussScheduleCriteria): Observable<BussSchedule[]> {
    const url = Url.API_HOST + "/trip/find-buss-schedules/"
      + XeDatePipe.instance.singleToSubmitFormat(data.launchDate) + "/"
      + data.locationFrom.locationId + "/"
      + data.locationTo.locationId;
    console.log(url);
    return this.http.get<BussSchedule[]>(url);
  }

  getTripWithPreparedTripUser(tripId: number, tripUserId: number): Observable<Trip> {
    const url = Url.API_HOST + "/trip/get-trip-with-prepared-trip-users/" + tripId + "/" + tripUserId;
    return this.http.get<Trip>(url);
  }

  findTripUsers(userId, phones, emails): Observable<TripUser[]> {
    userId = !userId ? 0 : userId;
    const url = Url.API_HOST + "/trip/get-trip-users?userId=" + userId + "&phones=" + phones + "&emails=" + emails;
    console.log(url);
    return this.http.get<TripUser[]>(url);
  }

  refreshCurrentUser() {
    this.getOne<User>(AuthUtil.instance.user, User.meta).subscribe(
      user => {
        AuthUtil.instance.setRepoUser(user[0]);
      }
    );
  }

  findTripByCompanyId(companyId: number): Observable<Trip[]> {
    companyId = !companyId ? 0 : companyId;
    const url = Url.API_HOST + "/trip/get-trip-by-company-id/" + companyId;
    console.log(url);
    return this.http.get<Trip[]>(url);
  }

  findBussSchedulesByCompanyId(companyId: number): Observable<BussSchedule[]> {
    companyId = !companyId ? 0 : companyId;
    const url = Url.API_HOST + "/trip/get-buss-schedules-by-company-id/" + companyId;
    console.log(url);
    return this.http.get<BussSchedule[]>(url);
  }


}
