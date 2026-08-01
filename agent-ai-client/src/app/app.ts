import { Component, inject, OnInit, signal } from '@angular/core';
import {COMMON_IMPORTS, CUSTOM_COMPONENTS, MENU_IMPORTS, PRIMENG_MESSAGE_COMPONENTS, PRIMENG_OVERLAY_COMPONENTS} from './shared/primeng-module-import';
import {COMMON_PROVIDER, DIALOG_PROVIDER, USER_AUTH_PROVIDER} from './services/service-provider-import';
import { SessionStorageService } from './services/session-storage.service';
import { UserAuthService } from './services/user-auth.service';
import { SessionStorageKeys } from './models/constans/session-storage-keys.const';
import { JwtUtils } from './shared/utils/jwt.utils';
import { MessageService } from 'primeng/api';
import { ErrorResponseModel } from './models/errors/error-response.model';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { UserDataModel } from './models/user-data.model';
import { StorageModel } from './models/storage.model';

@Component({
  selector: 'app-root',
  imports: [COMMON_IMPORTS, PRIMENG_OVERLAY_COMPONENTS, PRIMENG_MESSAGE_COMPONENTS, MENU_IMPORTS, CUSTOM_COMPONENTS],
  providers: [COMMON_PROVIDER, USER_AUTH_PROVIDER, DIALOG_PROVIDER],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  protected readonly title = signal('agent-ai-client');

  private sessionStorageService = inject(SessionStorageService);  
  private userAuthService = inject(UserAuthService);
  private messageService = inject(MessageService);
  private translateService = inject(TranslateService);
  private router = inject(Router);

  userData = signal<UserDataModel | null>(null);

  ngOnInit(): void {
    this.refreshTokenAndUserData(true);
    this.userAuthService.userDataChanges.subscribe((change) => {
      if (change) {
        this.refreshTokenAndUserData(false);
      }
    });
  }

  private refreshTokenAndUserData(isFirstLoad: boolean): void {
    const token = this.sessionStorageService.load(SessionStorageKeys.AUTH_TOKEN)?.value;
    const refreshToken = this.sessionStorageService.load(SessionStorageKeys.REFRESH_TOKEN)?.value;
    if (!refreshToken) {
      this.sessionStorageService.remove(SessionStorageKeys.AUTH_TOKEN);
      this.userData.set(null);
      this.router.navigate(['/login']);
      return;
    }
    if (!token || JwtUtils.isTokenExpired(token)) {
      this.userAuthService.refresh(refreshToken).subscribe({
        next: (tokenPair) => {
          this.sessionStorageService.save(new StorageModel(SessionStorageKeys.AUTH_TOKEN, tokenPair.accessToken));
          this.sessionStorageService.save(new StorageModel(SessionStorageKeys.REFRESH_TOKEN, tokenPair.refreshToken));
          this.refreshTokenAndUserData(isFirstLoad);
        }
      });
    } else {
        const decodedToken = JwtUtils.decodeToken(token);
        this.userAuthService.getUserData(decodedToken.sub).subscribe({
          next: (userData) => {
            this.userData.set(userData);
            if (isFirstLoad) {
              this.router.navigate(['/']);
            }
          },
          error: (error: ErrorResponseModel) => {
            this.messageService.add({
                      severity: 'error',
                      summary: this.translateService.instant('error.defaultHeader'),
                      detail: error.message }
            );
          }
        });
    }
  }
}
