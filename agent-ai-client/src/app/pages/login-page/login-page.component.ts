import { Component, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { COMMON_IMPORTS, FORMS_IMPORTS, PASSWORD_IMPORTS, PRIMENG_BUTTONS_COMPONENTS, PRIMENG_LABEL_COMPONENTS } from '../../shared/primeng-module-import';
import { UserLoginModel } from '../../models/user-login.model';
import { UserAuthService } from '../../services/user-auth.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from '../../services/session-storage.service';
import { StorageModel } from '../../models/storage.model';
import { SessionStorageKeys } from '../../models/constans/session-storage-keys.const';
import { ErrorResponseModel } from '../../models/errors/error-response.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { TokenPairModel } from '../../models/auth/token-pair.model';

@Component({
    selector: 'login-page',
    templateUrl: 'login-page.component.html',
    styleUrls: ['login-page.component.scss'],
    standalone: true,
    imports: [...COMMON_IMPORTS, ...FORMS_IMPORTS, ...PASSWORD_IMPORTS, ...PRIMENG_LABEL_COMPONENTS, ...PRIMENG_BUTTONS_COMPONENTS]
})
export class LoginPageComponent implements OnInit {

    private userAuthService = inject(UserAuthService);
    private messageService = inject(MessageService);
    private translateService = inject(TranslateService);
    private sessionStorageService = inject(SessionStorageService);
    private router = inject(Router);
    
    userLoginModel: WritableSignal<UserLoginModel> = signal<UserLoginModel>(new UserLoginModel('', '', ''));
    isRegisterMode: WritableSignal<boolean> = signal<boolean>(false);
    repeatPassword: WritableSignal<string> = signal<string>('');


    ngOnInit(): void {
    }

    onLoginClick(): void {
        this.userAuthService.login(this.userLoginModel()).subscribe({
            next: (tokenPair: TokenPairModel) => {
                console.log('Login successful. Token:', tokenPair.accessToken);
                this.sessionStorageService.save(new StorageModel(SessionStorageKeys.AUTH_TOKEN, tokenPair.accessToken));
                this.sessionStorageService.save(new StorageModel(SessionStorageKeys.REFRESH_TOKEN, tokenPair.refreshToken));
                this.sessionStorageService
                this.userAuthService.activeUserDatChange('LOGIN_DATA_CHANGED');
            },
            complete: () => {
                this.router.navigate(['/']);
            }
        });
    }

    onRegisterClick(): void {
        this.userAuthService.register(this.userLoginModel()).subscribe({
            next: (userData) => {
                this.messageService.add({
                    severity: 'success',
                    summary: this.translateService.instant('success.defaultHeader'),
                    detail: this.translateService.instant('login.registerSuccess') }
                );
                this.userLoginModel.set(new UserLoginModel('', '', ''));
                this.isRegisterMode.set(false);
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

    onKeyUp(event: KeyboardEvent): void {
        if (event.key === 'Enter') {
            if (this.isRegisterMode()) {
                this.onRegisterClick();
            } else {
                this.onLoginClick();
            }
        }
    }
}