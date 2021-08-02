export enum State {
  success = "success",
  info = "info",
  error = "error",
  danger = "danger",
  warning = "warning"
}

export class Message {
  code: string;
  params?: any;
  state?: State;

  static info(msg: string): Message {
    return Message.generateMessage(msg, State.info);
  }

  static error(msg: string): Message {
    return Message.generateMessage(msg, State.danger);
  }

  static success(msg: string): Message {
    return Message.generateMessage(msg, State.success);
  }

  static warning(msg: string): Message {
    return Message.generateMessage(msg, State.warning);
  }

  private static generateMessage(code: string, state: State): Message {
    const message = new Message();
    message.code = code;
    message.state = state;
    return message;
  }
}
