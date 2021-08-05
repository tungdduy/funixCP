import {ApiMessages_vi} from "./api-messages_vi";
import {AppMessages_vi} from "./app-messages_vi";
import {Label_vi} from "./label_vi";
import {StringUtil} from "../../framework/util/string.util";

export const ApiMessages = ApiMessages_vi;
export const AppMessages = AppMessages_vi;
export const XeLabel = Label_vi;
export const XeLbl = (lblKey: string) => {
  if (!lblKey) return "";
  const lbl = XeLabel[lblKey];
  return lbl ? lbl : lblKey;
};
