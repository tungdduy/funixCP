import {Injectable} from '@angular/core';
import {NbComponentStatus, NbGlobalPhysicalPosition, NbToastrService} from "@nebular/theme";
import {XeResponseModel} from "./xe.response.model";
import {AppMessages} from "../../business/i18n/app-messages";
import {ApiMessages} from "../../business/i18n/api-messages";
import {HttpErrorResponse} from "@angular/common/http";
import {config} from "../config";
import {ObjectUtil} from "../util/object.util";

@Injectable({
  providedIn: 'root'
})
export class XeNotifierService {
  constructor(private toastService: NbToastrService) {
  }

  private showToast(type: NbComponentStatus, title: string, body: string) {
    const toastConfig = {
      status: type,
      destroyByClick: false,
      duration: 3000,
      position: NbGlobalPhysicalPosition.TOP_RIGHT,

    };
    this.toastService.show(body, title, toastConfig);
  }

  public success(message: string) {
    this.showToast('success', message, new Date().toLocaleString());
  }

  public error(message: string) {
    this.showToast('danger', message, new Date().toLocaleString());
  }


  httpErrorResponse(error: HttpErrorResponse) {
    this.errorResponse(error.error);
  }

  errorResponse(response: XeResponseModel) {
    const message = this.responseToString(response);
    if (message.length > 0) {
      this.error(message);
    } else {
      this.error(AppMessages.DEFAULT_ERROR_MESSAGE);
    }
  }

  private responseToString(response: XeResponseModel): string {
    const msgFinder: string[] = [];
    if (response.reason == null) {
      return "";
    }
    response.messages?.forEach((message) => {
      if (message === null) return;

      const msgString = this.messageToString(message.code, message.params);
      if (msgString.length > 0) {
        msgFinder.push(msgString);
      }
    });
    if (msgFinder.length === 0) {
      msgFinder.push(response.reason);
    }
    return msgFinder.join(`\n`);
  }

  static API = "API: ";
  private messageToString = (code: string, param: any): string => {

    const message = ApiMessages[code];
    if (ObjectUtil.isFunction(message)) {
      return XeNotifierService.API + ApiMessages[code](param);
    } else if (ObjectUtil.isString(message)) {
      return XeNotifierService.API + ApiMessages[code];
    } else {
      return "";
    }
  }

}
