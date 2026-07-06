import { Component, inject, OnInit } from '@angular/core';
import { COMMON_IMPORTS, FORMS_IMPORTS, PASSWORD_IMPORTS, PRIMENG_BUTTONS_COMPONENTS, PRIMENG_LABEL_COMPONENTS } from '../../shared/primeng-module-import';
import { UserLoginModel } from '../../models/user-login.model';
import { UserAuthService } from '../../services/user-auth.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { COMMON_PROVIDER, USER_AUTH_PROVIDER } from '../../services/service-provider-import';
import { SessionStorageService } from '../../services/session-storage.service';
import { StorageModel } from '../../models/storage.model';
import { SessionStorageKeys } from '../../models/constans/session-storage-keys.const';
import { ErrorResponseModel } from '../../models/errors/error-response.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'login-page',
    templateUrl: 'login-page.component.html',
    styleUrls: ['login-page.component.scss'],
    standalone: true,
    imports: [...COMMON_IMPORTS, ...FORMS_IMPORTS, ...PASSWORD_IMPORTS, ...PRIMENG_LABEL_COMPONENTS, ...PRIMENG_BUTTONS_COMPONENTS],
    providers: [...USER_AUTH_PROVIDER]
})
export class LoginPageComponent implements OnInit {

    private userAuthService = inject(UserAuthService);
    private messageService = inject(MessageService);
    private translateService = inject(TranslateService);
    private sessionStorageService = inject(SessionStorageService);
    
    private _userLoginModel = new UserLoginModel('', '', '');
    private _isRegisterMode = false;
    private _repeatPassword = '';


    constructor() { }

    get userLoginModel(): UserLoginModel {
        return this._userLoginModel;
    }

    set userLoginModel(value: UserLoginModel) {
        this._userLoginModel = value;
    }

    get isRegisterMode(): boolean {
        return this._isRegisterMode;
    }

    set isRegisterMode(value: boolean) {
        this._isRegisterMode = value;
    }

    get repeatPassword(): string {
        return this._repeatPassword;
    }

    set repeatPassword(value: string) {
        this._repeatPassword = value;
    }

    ngOnInit(): void {
    }

    onLoginClick(): void {
        this.userAuthService.login(this._userLoginModel).subscribe({
            next: (token: string) => {
                console.log('Login successful. Token:', token);
                this.sessionStorageService.save(new StorageModel(SessionStorageKeys.AUTH_TOKEN, token));
            }
        });
    }

    onRegisterClick(): void {
        this.userAuthService.register(this._userLoginModel).subscribe({
            next: (userData) => {
                this.messageService.add({
                    severity: 'success',
                    summary: this.translateService.instant('success.defaultHeader'),
                    detail: this.translateService.instant('login.registerSuccess') }
                );
                this.userLoginModel = new UserLoginModel('', '', '');
                this.isRegisterMode = false;
            },
            error: (error: ErrorResponseModel) => {
                console.error('Registration failed:', error);
                this.messageService.add({
                    severity: 'error',
                    summary: this.translateService.instant('error.defaultHeader'),
                    detail: error.message }
                );
            }
        });
    }
}