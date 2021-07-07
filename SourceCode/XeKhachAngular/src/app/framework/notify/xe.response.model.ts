import {Message} from "../model/message.model";

export interface XeResponseModel {
  timeStamp: string;
  status: string;
  statusCode: bigint;
  reason: string;
  messages: Message[];
}
