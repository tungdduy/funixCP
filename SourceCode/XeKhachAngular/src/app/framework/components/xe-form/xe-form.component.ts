import {AfterViewInit, Component, ContentChildren, Input, OnDestroy, QueryList} from '@angular/core';
import {XeInputComponent} from "../xe-input/xe-input.component";
import {FormAbstract} from "../../model/form.abstract";
import {Notifier} from "../../notify/notify.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormHandler} from "../../model/FormHandler";
import {XeLabel} from "../../../business/i18n";
import {State} from "../../model/message.model";
import {XeLabelComponent} from "../xe-label/xe-label.component";
import {XeSubscriber} from "../../model/XeSubscriber";
import {InputMode} from "../../model/EnumStatus";

@Component({
  selector: 'xe-form',
  templateUrl: './xe-form.component.html',
  styleUrls: ['./xe-form.component.scss']
})
export class XeFormComponent extends XeSubscriber implements OnDestroy, AfterViewInit {

  @Input() addToSubmit: () => {};
  @Input() onSuccess: 'update' | 'reset';
  @Input() muteOnSuccess: boolean;
  @Input() initCtrl?: () => {};
  @Input("readMode") readMode;
  @Input() class;
  @Input() name;
  @Input() uncheckChanged;
  @Input() hide;
  @ContentChildren(XeInputComponent, {descendants: true}) formControls: QueryList<XeInputComponent>;
  ctrl: FormAbstract;
  isLoading: boolean = false;
  messages = {
    success: {
      code: XeLabel.SAVED_SUCCESSFULLY,
      state: State.success,
    },
    noChange: {
      code: XeLabel.DATA_NO_CHANGE,
      state: State.info,
    },
    error: {
      code: XeLabel.ERROR_PLEASE_TRY_AGAIN,
      state: State.danger
    }
  };
  @ContentChildren('msg', {descendants: true}) _msg: QueryList<XeLabelComponent>;
  msg: XeLabelComponent;
  private _originalMute;

  get show() {
    if (this.hide === undefined) {
      return true;
    }
    return !(this.hide === '' || this.hide === true);

  }

  set show(value) {
    setTimeout(() => {
      this.hide = value;
    }, 0);
  }

  _handler: FormHandler;

  get handler() {
    if (this._handler === undefined
      && this.ctrl
      && !!this.ctrl.handlers) {
      this._handler = this.ctrl.handlers.find(s => s.name === this.name);
      if (this._handler === undefined) {
        this._handler = this.ctrl.handlers[0];
      }
    }
    return this._handler;
  }

  get isMute() {
    return this.readMode === '' || this.readMode === true;
  }

  get errorMessages(): string[] {
    return this.formControls.filter(input => input.validateFailed()).map(input => input.errorMessage);
  }

  private get isResetOnSuccess() {
    return this.onSuccess === 'reset';
  }

  notify(message: string, state: State) {
    const msg = {code: message, state};
    if (this.msg) {
      this.msg.setMessage(msg);
    } else {
      Notifier.notify(msg);
    }
  }

  public _onSubmit() {
    if (!this.handler) {
      console.log("no FormHandler found!");
      return;
    }
    let model = {};
    console.log("start submit form: " + this.name);
    let changedInputsNumber = 0;
    const invalidNumber = this.formControls.filter(input => {

      if (input.selectOneMenu && input.selectOneMenu.length === 1)
        input.value = input.selectOneMenu[0].value;

      if (input.isChanged || this.uncheckChanged) {
        model[input.name] = input.submitValue;
        changedInputsNumber++;
      }
      return input.validateFailed();
    }).length;

    console.log("changedInputsNumber: " + changedInputsNumber);

    if (this.addToSubmit) {
      const addToModel = this.addToSubmit();
      Object.keys(addToModel).forEach(key => {
        if (!model[key]) {
          model[key] = addToModel[key];
        }
      });
    }

    if (Object.keys(model).length === 1) {
      model = this.formControls.first.value;
    }

    if (invalidNumber !== 0) {
      console.log("form invalid, exit");
      console.log(model);
      return false;
    }

    if (changedInputsNumber === 0) {
      console.log("nothing changed");
      this.msg?.setMessage(this.messages.noChange);
      return;
    }

    const observable = this.handler?.processor(model);
    if (observable['status'] && !observable.isSuccess()) {
      this.notify(observable.message, State.error);
      return;
    }

    console.log("form valid, begin post...");
    console.log(model);
    this.isLoading = true;
    this.subscriptions.push(
      this.handler?.processor(model).subscribe(
        (response: any) => {
          this.isLoading = false;
          this.msg?.setMessage(this.messages.success);
          this.handler?.success?.call(response);
          this.handler?.callbackOnSuccess?.call(this);
          if (this.isResetOnSuccess) {
            this.formControls.forEach(input => input.reset());
          } else {
            this.formControls.forEach(input => input.updateToNewValue());
          }

          if (this.muteOnSuccess) {
            this.mute();
          }
        },
        (error: HttpErrorResponse) => {
          this.isLoading = false;
          Notifier.httpErrorResponse(error, this.msg);
        }
      )
    );
    return false;
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.init();
    }, 0);
  }
  init() {
    if (this.initCtrl) {
      this.ctrl = this.initCtrl() as FormAbstract;
    }
    this.msg = this._msg.first;
    this._originalMute = this.readMode;
    this.updateMute();
    this.handler?.reset?.call();
  }

  reset() {
    this.formControls.forEach(input => input.reset());
    this.readMode = this._originalMute;
    this.handler?.reset?.call();
    this.updateMute();
  }

  mute() {
    this.readMode = true;
    this.updateMute();
  }

  unMute() {
    this.readMode = false;
    this.updateMute();
  }

  private updateMute() {
    if (this.isMute) {
      setTimeout(() => {
        this.formControls.forEach(input => {
          input.formMode = InputMode.disabled;
        });
      }, 0);
    } else {
      setTimeout(() => {
        this.formControls.forEach(input => {
          input.formMode = input.originalMode;
        });
      }, 0);
    }
  }

}
