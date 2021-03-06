import {Injectable} from '@angular/core';
import {NbComponentStatus, NbGlobalPhysicalPosition, NbToastrService} from "@nebular/theme";
import {XeResponseModel} from "./xe.response.model";
import {HttpErrorResponse} from "@angular/common/http";
import {ObjectUtil} from "../util/object.util";
import {ApiMessages, AppMessages} from "../../business/i18n";
import {StringUtil} from "../util/string.util";
import {XeLabelComponent} from "../components/xe-label/xe-label.component";
import {State} from "../model/message.model";

@Injectable({
  providedIn: 'root'
})
export class Notifier {

  static toastService: NbToastrService;
  static API = "API: ";

  constructor(private toastService: NbToastrService) {
    Notifier.toastService = toastService;
  }

  static success(message: string) {
    this.showToast('success', message, new Date().toLocaleString());
  }

  static error(message: string) {
    this.showToast('danger', message, new Date().toLocaleString());
  }


  static httpErrorResponse(error: HttpErrorResponse, labelComponent?: XeLabelComponent) {
    if (error?.error) {
      if (labelComponent) {
        labelComponent.setMessage({code: Notifier.responseToString(error.error), state: State.danger});
      } else {
        Notifier.errorResponse(error.error);
      }
    } else {
      Notifier.error(error.message);
    }

  }

  static errorResponse(response: XeResponseModel) {
    const message = Notifier.responseToString(response);
    if (message.length > 0) {
      Notifier.error(message);
    } else {
      Notifier.error(AppMessages.DEFAULT_ERROR_MESSAGE);
    }
  }

  static responseToString(response: XeResponseModel): string {
    const msgFinder: string[] = [];
    if (response.reason == null) {
      return "";
    }
    response.messages?.forEach((message) => {
      if (message === null) return;

      const msgString = Notifier.messageToString(message.code, message.params);
      if (msgString.length > 0) {
        msgFinder.push(msgString);
      }
    });
    if (msgFinder.length === 0) {
      msgFinder.push(Notifier.messageToString(response.reason));
    }
    return msgFinder.join(`\n`);
  }

  static warning(message: string) {
    this.showToast('warning', message, new Date().toLocaleString());
  }

  static notify(msg: any) {
    this.showToast(msg.state, msg.code, new Date().toLocaleString());
  }

  private static showToast(type: NbComponentStatus, title: string, body: string) {
    const toastConfig = {
      status: type,
      destroyByClick: false,
      duration: 5000,
      position: NbGlobalPhysicalPosition.TOP_RIGHT,

    };
    Notifier.toastService.show(body, title, toastConfig);
  }

  private static messageToString = (code: string, param?: any): string => {

    const message = ApiMessages[StringUtil.toUPPER_UNDERLINE(code)];
    if (ObjectUtil.isFunction(message)) {
      return Notifier.API + message(param);
    } else if (ObjectUtil.isString(message)) {
      return Notifier.API + message;
    } else {
      return code;
    }
  }
}
