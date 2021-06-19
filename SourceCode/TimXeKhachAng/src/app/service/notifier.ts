import {Injectable, NgModule} from '@angular/core';
import {NotifierOptions, NotifierModule, NotifierService} from 'angular-notifier';
import {AppMessages} from "../static/app-messages";
import {ApiMessages} from "../static/api-messages";
import {XeReponse} from "../static/model/xe-response";

const customNotifierOption: NotifierOptions = {
  position: {
    horizontal: {
      position: "left",
      distance: 150
    },
    vertical: {
      position: "top",
      distance: 12,
      gap: 10
    }
  },
  theme: "material",
  behaviour: {
    autoHide: 5000,
    onClick: "hide",
    onMouseover: "pauseAutoHide",
    showDismissButton: true,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: "ease"
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50
    },
    shift: {
      speed: 300,
      easing: 'ease'
    },
    overlap: 150
  }
}

@NgModule({
  imports: [
    NotifierModule.withConfig(customNotifierOption)
  ],
  exports: [NotifierModule]
})
@Injectable({
  providedIn: 'root'
})
export class Notifier {
  private type = {
    DEFAULT: "DEFAULT",
    SUCCESS: "SUCCESS",
    ERROR: "ERROR",
    INFO: "INFO",
    WARNING: "WARNING"
  }

  constructor(private notifier: NotifierService) { }

  success(message: string) {
    this.notifier.notify(this.type.SUCCESS, message);
  }

  warning(message: string) {
    this.notifier.notify(this.type.WARNING, message);
  }

  error(message: string) {
    this.notifier.notify(this.type.ERROR, message);
  }

  errorReponse(response: XeReponse) {
    let message = this.reponseToString(response);
    if(message.length > 0) {
      this.error(message);
    } else {
      this.error(AppMessages.DEFAULT_ERROR_MESSAGE);
    }
  }

  private reponseToString(response: XeReponse): string {
    const msgFinder: string[] = [];
    response.messages!.forEach((message) => {
      let msgString = this.messageToString(message.code, message.params);
      if(msgString.length > 0) {
        msgFinder.push(this.messageToString(message.code, message.params));
      }
    })
    if(msgFinder.length == 0) {
      msgFinder.push(this.messageToString(response.reason, null))
    }
    return msgFinder.join("<br/>");
  }

  private messageToString = (code: string, param:any): string => {
    // @ts-ignore
    let messageType = ApiMessages[code];
    if (messageType === "function") {
      // @ts-ignore
      return ApiMessages[code](param);
    } else if (messageType === "string") {
      // @ts-ignore
      return ApiMessages[code];
    } else {
      return "";
    }
  };

}




