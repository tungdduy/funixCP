import {Injectable} from "@angular/core";
import {User} from "../static/model/user";
import {constant} from "../static/constant";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  get token() {
    return localStorage.getItem(constant.TOKEN);
  }

  set token(token: string | null) {
    if (typeof token === "string") {
      localStorage.setItem(constant.TOKEN, token);
    }
  }

  set user(user: User | null) {
    localStorage.setItem(constant.USER, JSON.stringify(user));
  }

}
