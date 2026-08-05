import { Component, inject, OnInit } from '@angular/core';
import { COMMON_IMPORTS, FORMS_IMPORTS, MENU_IMPORTS, PRIMENG_BUTTONS_COMPONENTS } from '../../primeng-module-import';
import { MenuItem } from 'primeng/api';
import { SessionStorageKeys } from '../../../models/constans/session-storage-keys.const';
import { SessionStorageService } from '../../../services/session-storage.service';
import { UserAuthService } from '../../../services/user-auth.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'agent-profile-menu',
    templateUrl: './profile-menu.component.html',
    styleUrl: './profile-menu.component.scss',
    standalone: true,
    imports: [COMMON_IMPORTS, FORMS_IMPORTS, MENU_IMPORTS, PRIMENG_BUTTONS_COMPONENTS]
})
export class ProfileMenuComponent implements OnInit {

    private userAuthService = inject(UserAuthService);
    private sessionStorageService = inject(SessionStorageService)
    private router = inject(Router);
    private translateService = inject(TranslateService);

    private _profileMenuItes: MenuItem[] = []

    constructor() { }

    get profileMenuItems(): MenuItem[] {
        return this._profileMenuItes;
    }

    set profileMenuItems(value: MenuItem[]) {
        this._profileMenuItes = value;
    }

    ngOnInit(): void { 
        this.initializeMenuItems();
    }

    private initializeMenuItems(): void {
        if (this.userAuthService.isUserAdmin()) {
            this.profileMenuItems = [
                {
                    label: this.translateService.instant('profile.myAccount'),
                    items: [
                        { label: this.translateService.instant('buttons.adminPanel'), icon: 'pi pi-user' },
                        { label: this.translateService.instant('buttons.logout'), icon: 'pi pi-power-off', command: () => (this.logout()) }
                    ]
                }
            ];
        } else {     
            this.profileMenuItems = [
                {
                    label: this.translateService.instant('profile.myAccount'),
                    items: [
                        { label: this.translateService.instant('buttons.profile'), icon: 'pi pi-user' },
                        { label: this.translateService.instant('buttons.logout'), icon: 'pi pi-power-off', command: () => (this.logout()) }
                    ]
                }
            ];
        }
    }

    private logout(): void {
        this.userAuthService.logout(this.sessionStorageService.load(SessionStorageKeys.REFRESH_TOKEN)?.value).subscribe({
            complete: () => {
                this.sessionStorageService.remove(SessionStorageKeys.AUTH_TOKEN);
                this.sessionStorageService.remove(SessionStorageKeys.REFRESH_TOKEN);
                this.userAuthService.activeUserDatChange('LOGOUT_DATA_CHANGED');
                this.router.navigate(['/login']);
            }
        });
    }
}