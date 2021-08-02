import {AfterViewInit, Component, ContentChildren, Input, OnDestroy, QueryList} from '@angular/core';
import {Subscription} from "rxjs";
import {XeInputComponent} from "../xe-input/xe-input.component";
import {FormAbstract} from "../../../business/abstract/form.abstract";
import {Notifier} from "../../notify/notify.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormHandler} from "../../../business/abstract/formHandler";
import {XeLabel} from "../../../business/i18n";
import {State} from "../../model/message.model";
import {XeLabelComponent} from "../xe-label/xe-label.component";

@Component({
  selector: 'xe-form',
  templateUrl: './xe-form.component.html',
  styleUrls: ['./xe-form.component.scss']
})
export class XeFormComponent implements OnDestroy, AfterViewInit {

  @Input() addToSubmit: () => {};
  @Input() onSuccess: 'update' | 'reset';
  private get isResetOnSuccess() {
    return this.onSuccess === 'reset';
  }
  @Input() readonly;
  @Input() class;
  @Input() name;
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

  notify(message: string, state: State) {
    const msg = {code: message, state};
    if (this.msg) {
      this.msg.setMessage(msg);
    } else {
      Notifier.notify(msg);
    }
  }


  protected subscriptions: Subscription[] = [];
  private _originalMute;

  get show() {
    if (this.hide === undefined) {
      return true;
    }
    if (this.hide === '') {
      return false;
    }
    return this.hide;
  }

  set show(value) {
    this.hide = value;
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
    return this.readonly === '' || this.readonly === true;
  }

  public onSubmit() {
    if (!this.handler) {
      console.log("no FormHandler found!");
      return;
    }
    let model = {};
    console.log("start submit form: " + this.name);
    let changedInputsNumber = 0;
    const invalidNumber = this.formControls.filter(control => {
      model[control.name] = control.value;
      if (control.isChanged) {
        changedInputsNumber++;
      }
      return control.validateFailed();
    }).length;

    console.log("changedInputsNumber: " + changedInputsNumber);

    if (this.addToSubmit) {
      const addToModel = this.addToSubmit();
      Object.keys(addToModel).forEach(key => {
        model[key] = addToModel[key];
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
    console.log("form valid, begin post...");

    if (changedInputsNumber === 0) {
      console.log("nothing changed");
      this.msg.setMessage(this.messages.noChange);
      return;
    }
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
            this.formControls.forEach(input => input.update());
          }

          this.mute();
        },
        (error: HttpErrorResponse) => {
          this.isLoading = false;
          Notifier.httpErrorResponse(error, this.msg);
        }
      )
    );
    return false;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngAfterViewInit(): void {
    this.msg = this._msg.first;
    this._originalMute = this.readonly;
    this.updateMute();
    this.handler?.reset?.call();
  }

  reset() {
    this.formControls.forEach(input => input.reset());
    this.readonly = this._originalMute;
    this.handler?.reset?.call();
    this.updateMute();
  }

  mute() {
    this.readonly = true;
    this.updateMute();
  }

  unMute() {
    this.readonly = false;
    this.updateMute();
  }

  private updateMute() {
    if (this.isMute) {
      setTimeout(() => {
        this.formControls.forEach(input => {
          input.disabled = false;
        });
      }, 0);
    } else {
      setTimeout(() => {
        this.formControls.forEach(input => {
          input.disabled = true;
        });
      }, 0);
    }
  }
}
