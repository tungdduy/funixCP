export enum State {
  success = "success",
  info = "info",
  error = "error",
  danger = "danger"
}

export interface Message {
  code: string;
  params?: any;
  state?: State;
}
