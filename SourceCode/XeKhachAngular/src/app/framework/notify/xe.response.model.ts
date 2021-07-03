import {Message} from "./message.model";

export interface XeResponseModel {
  timeStamp: string;
  status: string;
  statusCode: bigint;
  reason: string;
  messages: Message[];
}
