export class XeEntity {
  constructor() {
    this.profileImageUrl = "http://robohash.org/xekhach/" + this.constructor.name + "/0";
  }
  profileImageUrl?: string;

  static isIdValid(entity: any, idColumns: {}): boolean {
    if (!idColumns) return false;
    let id = 0;
    for (const key of Object.keys(idColumns)) {
      id = Number.parseInt(entity[key], 10);
      if (!isNaN(id) && id > 0)
        return true;
    }
    return false;
  }

  static isMatchingId(idColumns: {}, entity1, entity2): boolean {

    for (const key of Object.keys(idColumns)) {
      if (entity1[key] !== entity2[key]) {
        return false;
      }
    }
    return true;
  }
}
