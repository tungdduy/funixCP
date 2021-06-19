import {Message} from "./message";

export interface XeReponse {
  timeStamp: string,
  status: string,
  statusCode: bigint,
  reason: string,
  messages: Message[]
}
