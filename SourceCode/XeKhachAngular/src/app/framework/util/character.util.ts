export class CharacterUtil {

  static removeAccents(str) {
    return str.normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/đ/g, 'd').replace(/Đ/g, 'D');
  }

  static anyMatch(standardRepo, rawInput): boolean {
    return rawInput && standardRepo.toLowerCase().includes(CharacterUtil.removeAccents(rawInput).toLowerCase());
  }

  static rawAnyMatch(rawRepo, rawInput): boolean {
    return rawRepo && rawInput && CharacterUtil.removeAccents(rawRepo).includes(CharacterUtil.removeAccents(rawInput));
  }

}
