import {Message} from "./message";

export interface XeResponse {
  timeStamp: string;
  status: string;
  statusCode: bigint;
  reason: string;
  messages: Message[];
}
