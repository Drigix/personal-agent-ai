import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ChatRequestBody} from '../models/chat-request-body.model';
import {map, Observable} from 'rxjs';
import {ConversationModel} from '../models/conversation.model';
import {ChatMessageModel} from '../models/chat-message.model';

@Injectable()
export class ChatService {

  private RESOURCE_URL = 'http://localhost:8080/api/' + 'chatSerivce/';
  private httpClient = inject(HttpClient);

  generateChatRequest(body: ChatRequestBody): Observable<ChatMessageModel> {
    const url = this.RESOURCE_URL + "generateChatRequest";
    return this.httpClient.post<ChatMessageModel>(
      url,
      body
    ).pipe(
      map(msg =>
        new ChatMessageModel(
          msg.id,
          msg.conversationId,
          msg.role,
          msg.date ?  new Date(msg.date) : undefined,
          msg.content
        )
      ));
  }

  getConversations(): Observable<ConversationModel[]> {
    const url = this.RESOURCE_URL + `getConversations`;
    return this.httpClient.get<ConversationModel[]>(url).pipe(
      map(conversations => conversations.map(conv =>
        new ConversationModel(
          conv.id,
          conv.title,
        )
      ))
    );
  }

  getChatHistoryByConversationId(conversationId: string): Observable<ChatMessageModel[]> {
    const url = this.RESOURCE_URL + `getChatHistoryByConversationId/${conversationId}`;
    return this.httpClient.get<ChatMessageModel[]>(url).pipe(
      map(messages => messages.map(msg =>
        new ChatMessageModel(
          msg.id,
          msg.conversationId,
          msg.role,
          msg.date ?  new Date(msg.date) : undefined,
          msg.content
        )
      ))
    );
  }

  deleteConversation(conversationId: string): Observable<any> {
    const url = this.RESOURCE_URL + `deleteConversation/${conversationId}`;
    return this.httpClient.delete<any>(url);
  }
}
