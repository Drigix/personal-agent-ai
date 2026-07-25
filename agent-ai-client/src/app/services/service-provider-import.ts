import {ChatService} from './chat.service';
import {TranslateService} from '@ngx-translate/core';
import {ConfirmationService, MessageService} from 'primeng/api';
import { UserAuthService } from './user-auth.service';

export const COMMON_PROVIDER = [TranslateService];

export const CHAT_PROVIDER = [ChatService, TranslateService];

export const USER_AUTH_PROVIDER = [UserAuthService, TranslateService];

export const DIALOG_PROVIDER = [ConfirmationService];