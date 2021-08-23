import {ApiMessages_vi} from "./api-messages_vi";
import {AppMessages_vi} from "./app-messages_vi";
import {Label_vi} from "./label_vi";

export const ApiMessages = ApiMessages_vi;
export const AppMessages = AppMessages_vi;
export const XeLabel = Label_vi;
export const unmapped = [];
export const XeLbl = (lblKey: string) => {
  if (!lblKey) return "";
  let lbl = XeLabel[lblKey];
  if (lbl) return lbl;
  const ext = lblKey.split(".").pop();
  lbl = XeLabel[lblKey];
  if ((lbl === undefined) && !unmapped.includes(lblKey) && !lblKey.includes(" ")) unmapped.push(lblKey);
  return lbl !== undefined ? lbl : lblKey;
};
