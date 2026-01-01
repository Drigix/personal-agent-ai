import {ChatMessageRole} from './enums/chat-message-role.enum';

export class ChatMessageModel {
  constructor(
    public id?: string,
    public conversationId?: string,
    public role?: string,
    public date?: Date,
    public content?: string
  ) {
  }

  public isUserMessage(): boolean {
    return this.role === ChatMessageRole.USER;
  }

  public isAgentMessage(): boolean {
    return this.role === ChatMessageRole.AGENT;
  }
}
