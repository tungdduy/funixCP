import {AfterViewInit, Component, Input} from '@angular/core';
import {XeLbl} from "../../../business/i18n";
import {Message} from "../../model/message.model";

@Component({
  selector: 'xe-btn',
  templateUrl: './xe-btn.component.html',
  styleUrls: ['./xe-btn.component.scss']
})
export class XeBtnComponent implements AfterViewInit {
  @Input() lbl;

  @Input() disabled: boolean = false;
  @Input() icon: string;
  private _label: string;

  @Input() center?;
  @Input() left?;
  @Input() right?;

  @Input() type: 'save' | 'submit' | 'cancel' | 'edit' | 'close';

  _types = {
    save: {
      icon: 'save',
      state: 'primary',
      size: 'sm',
      label: 'save',
      type: 'submit'
    },
    submit: {
      icon: undefined,
      state: 'primary',
      size: 'sm',
      label: 'submit',
      type: 'submit'
    },
    cancel: {
      icon: 'window-close',
      state: 'secondary',
      label: 'cancel',
      size: 'sm',
      type: 'button'
    },
    close: {
      icon: 'window-close',
      state: 'secondary',
      label: 'close',
      size: 'sm',
      type: 'button'
    },
    edit: {
      icon: 'pen',
      label: 'edit',
      state: 'info',
      size: 'sm',
      type: 'button'
    },
    userEdit: {
      icon: 'user-edit',
      label: 'edit',
      state: 'warning',
      size: 'sm',
      type: 'button'
    },
    undefined: {
      icon: 'hand-point-right',
      state: 'primary',
      type: 'submit'
    }
  };

  constructor() {
  }

  private _state;

  get state() {
    return this._state;
  }

  private _btnSize;
  get btnSize() {
    return this._btnSize;
  }

  get posClass() {
    const pos = this.center === '' ? 'center'
        : this.left === '' ? 'left'
        : this.right === '' ? 'right' : undefined;
    return pos ? 'd-block text-' + pos : 'd-inline';
  }

  get label(): string {
    if (!this._label) {
      this._label = XeLbl(this.lbl);
    }
    return this._label;
  }

  btnType;

  ngAfterViewInit(): void {
    setTimeout(() => {
      const btnType = this._types[this.type];
      this._state = btnType.state;
      const size = btnType.size;
      this._btnSize = size ? 'btn-' + size : '';
      this.lbl = this.lbl ? this.lbl : btnType.label;
      this.icon = this.icon ? this.icon : btnType.icon;
      this.btnType = btnType.type;
    }, 0);
  }
}
