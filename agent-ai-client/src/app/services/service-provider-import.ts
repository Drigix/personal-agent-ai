import {ChatService} from './chat.service';
import {TranslateService} from '@ngx-translate/core';
import {ConfirmationService, MessageService} from 'primeng/api';

export const CHAT_PROVIDER = [ChatService, TranslateService];

export const DIALOG_PROVIDER = [MessageService, ConfirmationService];

export const MESSAGES_PROVIDER = [MessageService];
