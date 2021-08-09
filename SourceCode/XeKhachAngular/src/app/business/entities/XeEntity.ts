export abstract class XeEntity {
  profileImageUrl: string;
  initProfileImage() {
    return "http://robohash.org/xekhach/" + this.constructor.name + "/0";
  }

}
