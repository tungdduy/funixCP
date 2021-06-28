import {Injectable} from '@angular/core';
import {NbComponentStatus, NbGlobalPhysicalPosition, NbToastrService} from "@nebular/theme";
import {XeResponse} from "../static/model/xe-response";
import {AppMessages} from "../static/app-messages";
import {ApiMessages} from "../static/api-messages";
import {HttpErrorResponse} from "@angular/common/http";
import {constant} from "../static/constant";

@Injectable({
  providedIn: 'root'
})
export class XeNotifierService {
  constructor(private toastrService: NbToastrService) {
  }

  private showToast(type: NbComponentStatus, title: string, body: string) {
    const config = {
      status: type,
      destroyByClick: false,
      duration: 3000,
      position: NbGlobalPhysicalPosition.TOP_RIGHT,

    };
    this.toastrService.show(body, title, config);
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

  errorResponse(response: XeResponse) {
    const message = this.responseToString(response);
    if (message.length > 0) {
      this.error(message);
    } else {
      this.error(AppMessages.DEFAULT_ERROR_MESSAGE);
    }
  }

  private responseToString(response: XeResponse): string {
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

  private messageToString = (code: string, param: any): string => {

    const message = ApiMessages[code];
    const messageType = typeof message;
    if (messageType === "function") {
      return constant.API + ApiMessages[code](param);
    } else if (messageType === "string") {
      return constant.API + ApiMessages[code];
    } else {
      return "";
    }
  }

}
