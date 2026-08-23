import { AuthenticatedDataRequestBody } from "./authenticated-data-request-body.model";

export class ChatRequestBody extends AuthenticatedDataRequestBody {
  title?: string = undefined;
  chatMessage?: string  = undefined;
  conversationId?: string  = undefined;
  date?: Date = new Date();

  constructor(userDataId?: number, title?: string, chatMessage?: string, conversationId?: string, date?: Date) {
    super();
    this.userDataId = userDataId;
    this.title = title;
    this.chatMessage = chatMessage;
    this.conversationId = conversationId;
    this.date = date;
  }
}
