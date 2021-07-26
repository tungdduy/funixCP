export class RegexUtil {
  static emailPattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  static phonePattern = /(03|05|07|08|09)+\d{8,10}/;
  static isValidEmail(email): boolean {
    return RegexUtil.emailPattern.test(String(email).toLowerCase());
  }

  static isValidPhone(phone): boolean {
    return RegexUtil.phonePattern.test(String(phone).toLowerCase());
  }

}
