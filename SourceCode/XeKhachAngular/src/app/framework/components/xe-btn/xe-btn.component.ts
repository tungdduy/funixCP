import {AfterViewInit, Component, HostBinding, Input} from '@angular/core';
import {XeLbl} from "../../../business/i18n";
import {Message} from "../../model/message.model";
import {AbstractXe} from "../../model/AbstractXe";

@Component({
  selector: 'xe-btn',
  templateUrl: './xe-btn.component.html',
  styleUrls: ['./xe-btn.component.scss']
})
export class XeBtnComponent extends AbstractXe implements AfterViewInit {
  @Input() btnText;

  @Input() disabled: boolean = false;
  @HostBinding('style.pointer-events') get pEvents(): string {
    if (this.disabled) {
      return 'none';
    }
    return 'auto';
  }

  @Input() icon: string;
  private _label: string;

  @Input("state") _state: 'success' | 'primary' | 'secondary' | 'warning';

  @Input() center?;
  @Input() left?;
  @Input() right?;

  @Input() template: 'save' | 'submit' | 'cancel' | 'edit' | 'close' | 'add' | 'delete' | 'ok' | 'back' | 'dangerDelete' | 'selectFromList' | 'default' | 'blank' | 'eraser' | 'orderTrip';

  @Input() hideText;
  get showText() {
    return this.hideText === undefined
      && (this._label !== undefined || this.btnText !== undefined);
  }


  _types = {
    eraser: {
      state: 'secondary',
      size: 'sm',
      type: 'button',
      label: 'eraser',
      icon: 'eraser'
    },
    default: {
      state: 'primary',
      size: 'sm',
      type: 'button'
    },
    blank: {
      state: 'blank',
      size: 'xs',
      type: 'button'
    },
    secondary: {
      state: 'secondary',
      size: 'sm',
      type: 'button'
    },
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
    add: {
      icon: 'plus',
      label: 'add',
      state: 'primary',
      size: 'sm',
      type: 'submit'
    },
    ok: {
      icon: 'thumbs-up',
      label: 'ok',
      state: 'primary',
      size: 'sm',
      type: 'button'
    },
    orderTrip: {
      icon: 'luggage-cart',
      label: 'orderNow',
      state: 'primary',
      size: 'lg',
      type: 'button'
    },
    selectFromList: {
      icon: 'search-plus',
      label: 'select-from-list',
      state: 'primary',
      size: 'sm',
      type: 'button'
    },
    back: {
      icon: 'arrow-circle-left',
      label: 'back',
      state: 'secondary',
      size: 'sm',
      type: 'button'
    },
    delete: {
      icon: 'trash',
      label: 'delete',
      state: 'secondary',
      size: 'sm',
      type: 'button'
    },
    dangerDelete: {
      icon: 'trash',
      label: 'delete',
      state: 'danger',
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

  btnType;
  btn;

  ngAfterViewInit(): void {
    this.updateBtn();
  }
  updateBtn() {
    setTimeout(() => {
      this.btn = this._types[this.template] || this._types.default;
      this._btnSize = this.btn?.size ? 'btn-' + this.btn.size : '';
      this.btnText = this.btnText ? this.btnText : this.xeLabel[this.btn.label];
      this.icon = this.icon ? this.icon : this.btn.icon;
      this.btnType = this.btn.type;
      if (this.state === undefined) {
        this._state = this.btn.state;
      }
    }, 0);
  }
}
