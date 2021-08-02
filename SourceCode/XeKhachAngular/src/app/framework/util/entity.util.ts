import {Company} from "../../business/model/company";
import {User} from "../../business/model/user";

export class EntityUtil {
  static newByName(name: string) {
    if (name === 'Company') {
      return new Company();
    } else if (name === 'User') {
      return new User();
    }
  }
}
