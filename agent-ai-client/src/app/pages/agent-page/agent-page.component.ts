import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  inject,
  OnInit,
  signal,
  ViewChild
} from '@angular/core';
import {
  COMMON_IMPORTS, CUSTOM_COMPONENTS, CUSTOM_DIRECTIVES,
  FORMS_IMPORTS,
  PRIMENG_BUTTONS_COMPONENTS,
  PRIMENG_LABEL_COMPONENTS
} from '../../shared/primeng-module-import';
import {ChatRequestBody} from '../../models/chat-request-body.model';
import {ChatService} from '../../services/chat.service';
import {CHAT_PROVIDER} from '../../services/service-provider-import';
import {ConversationModel} from '../../models/conversation.model';
import {ChatMessageModel} from '../../models/chat-message.model';
import {ChatMessageRole} from '../../models/enums/chat-message-role.enum';
import {TranslateService} from '@ngx-translate/core';
import {StringUtils} from '../../shared/utils/string.utils';
import {ConfirmationService, MessageService} from 'primeng/api';
import {AgentFileUploadComponent} from '../../shared/components/agent-file-upload/agent-file-upload.component';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'agent-page',
  templateUrl: 'agent-page.component.html',
  styleUrls: ['agent-page.component.scss'],
  standalone: true,
  imports: [...COMMON_IMPORTS, ...FORMS_IMPORTS, ...PRIMENG_LABEL_COMPONENTS, ...PRIMENG_BUTTONS_COMPONENTS, ...CUSTOM_DIRECTIVES, ...CUSTOM_COMPONENTS],
  providers: [...CHAT_PROVIDER]
})
export class AgentPageComponent implements OnInit {

  readonly StringUtils = StringUtils;

  @ViewChild('agentFileUploadComponent') agentFileUploadComponent!: AgentFileUploadComponent;
  @ViewChild('scrollAnchor') scrollAnchor!: ElementRef;
  @ViewChild('chatScrollContainer') chatScrollContainer!: ElementRef;

  isNewConversation = signal<boolean>(true);
  conversations = signal<ConversationModel[]>([]);
  selectedConversation = signal<ConversationModel | undefined>(undefined);
  chatHistory = signal<ChatMessageModel[]>([]);
  text = signal<string>('');
  isLoading = signal<boolean>(false);
  chatResponseText = signal<string>('');
  displayedText = signal<string | undefined>('');

  private chatService = inject(ChatService);
  private translateService = inject(TranslateService);
  private cd = inject(ChangeDetectorRef);
  private confirmationService = inject(ConfirmationService);
  private messageService = inject(MessageService);

  ngOnInit(): void {
    this.loadConversations();
  }

  onAddNewConversationClicked(): void {
    this.isNewConversation.set(true);
    this.selectedConversation.set(undefined);
  }

  onConversationSelected(conversation: ConversationModel) {
    this.isNewConversation.set(false);
    this.selectedConversation.set(conversation);
    if (!!conversation?.id) {
      this.loadChatHistory(conversation?.id!);
    }
  }

  onDeleteConversationClicked(conversationId: string): void {
    this.confirmationService.confirm({
      key: 'appConfirmDialog',
      message: this.translateService.instant('conversation.deleteDialogMessage'),
      header: this.translateService.instant('conversation.deleteDialogHeader'),
      icon: 'pi pi-info-circle',
      rejectButtonStyleClass: 'p-button-text',
      rejectButtonProps: {
        label: this.translateService.instant('buttons.no'),
        severity: 'secondary',
        text: true,
      },
      acceptButtonProps: {
        label: this.translateService.instant('buttons.yes'),
        text: true,
      },
      accept: () => {
        this.deleteConversation(conversationId);
      },
    });
  }

  onKeyClick(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.onSentClicked();
    }
  }

  onSentClicked(): void {
    if (StringUtils.isEmpty(this.text())) {
      return;
    }
    if (this.isNewConversation()) {
      const newConversation = new ConversationModel(undefined, this.prepareTitle(this.text()));
      this.conversations().unshift(newConversation);
      this.selectedConversation.set(newConversation);
      this.isNewConversation.set(false);
    }
    const newFiles = !!this.agentFileUploadComponent && this.agentFileUploadComponent.files.length > 0 ? this.agentFileUploadComponent.files : null;
    const newChatMessage = new ChatMessageModel(
      undefined, this.selectedConversation()?.id, ChatMessageRole.USER, new Date(), this.text()
    );
    this.chatHistory().push(newChatMessage);
    this.isLoading.set(true);
    this.displayedText.set('');
    this.scrollToBottom();
    const chatRequestBody = new ChatRequestBody(
      this.selectedConversation()?.title, this.text(), newChatMessage.conversationId, newChatMessage.date
    );
    this.text.set('');
    this.cd.detectChanges();
    this.chatService.generateChatRequest(chatRequestBody, newFiles).subscribe({
      next: (res: ChatMessageModel) => {
        if (!!this.selectedConversation() && !this.selectedConversation()?.id) {
          this.selectedConversation()!.id = res?.conversationId;
        }
        this.chatResponseText.set(res?.content!);
        this.typeText(res);
      }, error: err => {
        const index = this.chatHistory().findIndex(item => item === newChatMessage);
        this.conversations().splice(index, 1);
        this.isLoading.set(false);
        this.messageService.add({
          severity: 'error',
          summary: this.translateService.instant('error.defaultHeader'),
          detail: this.translateService.instant('error.requestError') }
        );
      },
      complete: () => {
        this.isLoading.set(false);
      }
    });
  }

  private loadConversations(): void {
    this.chatService.getConversations().subscribe({
      next: (res: ConversationModel[]) => {
        this.conversations.set(res ?? []);
      },
      error: err => {
        this.handleHttpError(err)
      }
    });
  }

  private loadChatHistory(conversationId: string) {
    this.chatService.getChatHistoryByConversationId(conversationId).subscribe({
      next: (res: ChatMessageModel[]) => {
        this.chatHistory.set(res ?? []);
        this.scrollToBottom();
      },
      error: err => {
        this.handleHttpError(err)
      }
    });
  }

  private deleteConversation(conversationId: string): void {
    const conversationToDelete = this.conversations().find(conversation => conversation.id === conversationId);
    this.chatService.deleteConversation(conversationId).subscribe({
      next: () => {
        if (conversationToDelete) {
          this.isNewConversation.set(true);
          const index = this.conversations().findIndex(conversation => conversation.id === conversationId);
          this.conversations().splice(index, 1);
        }
      },
      error: err => {
        this.handleHttpError(err)
      }
    });
  }

  private prepareTitle(content: string): string {
    return content.length > 20 ? `${content.substring(0, 20)}...` : content;
  }

  private typeText(chatMessage: ChatMessageModel) {
    let index = 0;
    const text = chatMessage.content!;
    const interval = setInterval(() => {
      this.displayedText.update(val => val + text[index]);
      this.scrollToBottom();
      index++;

      if (index >= text.length) {
        clearInterval(interval);
        this.chatHistory().push(chatMessage);
        this.displayedText.set('');
        this.isLoading.set(false);
        this.cd.detectChanges();
      }
    }, 20);
  }

  private scrollToBottom(): void {
    try {
      setTimeout(() => {
        this.scrollAnchor?.nativeElement?.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
      })
      this.cd.detectChanges();
    } catch (err) {
      console.error('Scroll error:', err);
    }
  }

  private handleHttpError(error: HttpErrorResponse) {
    this.messageService.add({
      severity: 'error',
      detail: error.message
    });
  }
}
