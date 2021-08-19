import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';
import {Location} from "../entities/Location";
import {Url} from "../../framework/url/url.declare";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) {
  }

  public searchLocation(searchTerm: string): Observable<Location[]> {
    return this.http.get<Location[]>(Url.API_HOST + "/trip/searchLocation/" + searchTerm);
  }
}
