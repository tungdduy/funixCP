export class SelectItem<E> {
  constructor(private _label: string, private _value) {
  }

  get label() {
    return this._label;
  }

  get value(): E {
    return this._value as E;
  }
}
