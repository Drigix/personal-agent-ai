export class ChatRequestBody {
  title?: string = undefined;
  chatMessage?: string  = undefined;
  conversationId?: string  = undefined;
  date?: Date = new Date();

  constructor(title?: string, chatMessage?: string, conversationId?: string, date?: Date) {
    this.title = title;
    this.chatMessage = chatMessage;
    this.conversationId = conversationId;
    this.date = date;
  }
}
