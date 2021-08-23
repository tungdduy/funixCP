export class MultiOptionUtil {
  static has(source, element) {
    return source && element && source.includes("#" + element + "#");
  }
  static add(all: string[], source: string, element: string) {
    if (!source) source = '';
    if (all.includes(element)  && !source.includes(this.format(element))) {
      const sourceArr = source.split("#");
      sourceArr.push(element);
      const resultArr = sourceArr.filter(e => all.includes(e));
      return this.format(resultArr.join("#"));
    }
    return source;
  }
  static remove(all, source: string, element: string) {
    if (all.includes(element)  && source && source.includes(this.format(element))) {
      const sourceArr = source.split("#");
      const resultArr = sourceArr.filter(s => all.includes(s) && s !== element);
      return resultArr.length === 0 ? '' : this.format(resultArr.join("#"));
    }
    return '';
  }
  static toggle(all, source, element) {
    return this.has(source, element) ? this.remove(all, source, element) : this.add(all, source, element);
  }
  private static format(element) {
    return "#" + element + "#";
  }
}

export interface Options {
  NAME: string;
  ALL: string[];
  ALL_STRING: string;
}

export const WeekDays: Options = {
  NAME: 'WEEK_DAYS',
  ALL: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"],
  ALL_STRING: "#SUN#MON#TUE#WED#THU#FRI#SAT#"
};



